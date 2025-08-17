package com.alura.literalura;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.DadosRespostaDTO;
import com.alura.literalura.dto.LivroDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.IConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Scanner leitura = new Scanner(System.in);
	private final LivroRepository livroRepository;
	private final AutorRepository autorRepository;

	@Autowired
	public LiteraluraApplication(LivroRepository livroRepository, AutorRepository autorRepository) {
		this.livroRepository = livroRepository;
		this.autorRepository = autorRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var opcao = -1;
		while (opcao != 0) {
			exibirMenu();

			try {
				opcao = Integer.parseInt(leitura.nextLine());
				switch (opcao) {
					case 1:
						buscarLivroPeloTitulo();
						break;
					case 2:
						listarLivrosRegistrados();
						break;
					case 3:
						listarAutoresRegistrados();
						break;
					case 4:
						listarAutoresVivosEmDeterminadoAno();
						break;
					case 5:
						listarLivrosEmDeterminadoIdioma();
						break;
					case 0:
						System.out.println("Saindo do LiterAlura...");
						break;
					default:
						System.out.println("Opção inválida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida, por favor, digite um número.");
			}
		}
	}

	private void buscarLivroPeloTitulo() {
		ConsumoApi consumoApi = new ConsumoApi();
		IConverteDados conversor = new ConverteDados();
		final String ENDERECO = "https://gutendex.com/books/?search=";

		System.out.println("Digite o nome do livro para busca:");
		var nomeLivro = leitura.nextLine();

		System.out.println("Buscando...");
		var busca = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);
		var json = consumoApi.obterDados(ENDERECO + busca);

		DadosRespostaDTO dadosResposta = conversor.obterDados(json, DadosRespostaDTO.class);

		if (dadosResposta != null && !dadosResposta.resultados().isEmpty()) {
			LivroDTO livroDTO = dadosResposta.resultados().get(0);
			AutorDTO autorDTO = livroDTO.autores().get(0);

			Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(autorDTO.nome());

			Autor autor;
			if (autorExistente.isPresent()) {
				autor = autorExistente.get();
			} else {
				autor = new Autor(autorDTO.nome(), autorDTO.anoNascimento(), autorDTO.anoFalecimento());
			}

			Livro livro = new Livro(livroDTO, autor);

			autor.adicionarLivro(livro);
			autorRepository.save(autor);

			System.out.println("Livro salvo com sucesso!");
			System.out.println(livro);

		} else {
			System.out.println("Nenhum livro encontrado com esse título.");
		}
	}

	private void listarLivrosRegistrados() {
		List<Livro> livros = livroRepository.findAll();

		if (livros.isEmpty()) {
			System.out.println("\nNenhum livro cadastrado.");
		} else {
			System.out.println("\n----- LIVROS REGISTRADOS -----");
			livros.stream()
					.sorted((l1, l2) -> l1.getTitulo().compareTo(l2.getTitulo()))
					.forEach(System.out::println);
		}
	}

	private void listarAutoresRegistrados() {
		List<Autor> autores = autorRepository.findAll();

		if (autores.isEmpty()) {
			System.out.println("\nNenhum autor cadastrado.");
		} else {
			System.out.println("\n----- AUTORES REGISTRADOS -----");
			autores.stream()
					.sorted((a1, a2) -> a1.getNome().compareTo(a2.getNome()))
					.forEach(autor -> {
						System.out.println("\nAutor: " + autor.getNome());
						System.out.println("  Nascimento: " + autor.getAnoNascimento());
						System.out.println("  Falecimento: " + autor.getAnoFalecimento());
						System.out.println("  Livros: [" + autor.getLivros().stream()
								.map(Livro::getTitulo)
								.collect(Collectors.joining(", ")) + "]");
					});
		}
	}

	private void listarAutoresVivosEmDeterminadoAno() {
		System.out.println("Digite o ano para pesquisar os autores vivos:");
		try {
			var ano = Integer.parseInt(leitura.nextLine());
			List<Autor> autores = autorRepository.findAutoresVivosEmAno(ano);

			if (autores.isEmpty()) {
				System.out.println("\nNenhum autor vivo encontrado para o ano de " + ano + ".");
			} else {
				System.out.println("\n----- AUTORES VIVOS EM " + ano + " -----");
				autores.forEach(System.out::println);
			}
		} catch (NumberFormatException e) {
			System.out.println("Ano inválido. Por favor, digite um número.");
		}
	}

	private void listarLivrosEmDeterminadoIdioma() {
		System.out.println("Digite o idioma para a busca (pt, en, es, fr):");
		var idioma = leitura.nextLine();

		List<Livro> livros = livroRepository.findByIdioma(idioma);

		if (livros.isEmpty()) {
			System.out.println("\nNenhum livro encontrado para o idioma '" + idioma + "'.");
		} else {
			System.out.println("\n----- LIVROS NO IDIOMA '" + idioma + "' -----");
			livros.forEach(System.out::println);
		}
	}

	private void exibirMenu() {
		var menu = """
			\n*** LiterAlura - Escolha uma opção ***
			1 - Buscar livro pelo título
			2 - Listar livros registrados
			3 - Listar autores registrados
			4 - Listar autores vivos em determinado ano
			5 - Listar livros em determinado idioma
			0 - Sair
			""";
		System.out.println(menu);
	}
}