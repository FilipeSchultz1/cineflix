package main.java;

import model.Filme;
import model.PerfilCinefilo;
import model.Recomendacao;
import model.Usuario;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import service.CalculadoraScore;
import service.CatalogoFilmesAPI;
import service.FiltroFilmes;
import service.HistoricoUsuarioRepository;
import service.NotificadorPush;
import service.RecomendadorService;
import util.GeradorAleatorio;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("          BEM-VINDO AO CINEFLIX");
        System.out.println("=======================================\n");

        // =========================================
        // DADOS DO USUÁRIO
        // =========================================

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        // =========================================
        // PREFERÊNCIAS DE GÊNERO
        // =========================================

        Map<Genero, Double> pesosGenero = new EnumMap<>(Genero.class);

        System.out.println("\nDigite um peso de 0.0 a 1.0 para cada gênero:");

        for (Genero genero : Genero.values()) {

            System.out.print(genero + ": ");

            double peso = Double.parseDouble(scanner.nextLine());

            pesosGenero.put(genero, peso);
        }

        // =========================================
        // DURAÇÃO
        // =========================================

        System.out.print("\nDuração mínima preferida: ");
        int duracaoMinima = Integer.parseInt(scanner.nextLine());

        System.out.print("Duração máxima preferida: ");
        int duracaoMaxima = Integer.parseInt(scanner.nextLine());

        // =========================================
        // CLASSIFICAÇÃO ETÁRIA
        // =========================================

        System.out.println("\nClassificação máxima aceitável:");

        System.out.println("1 - LIVRE");
        System.out.println("2 - 10");
        System.out.println("3 - 12");
        System.out.println("4 - 14");
        System.out.println("5 - 16");
        System.out.println("6 - 18");

        int opcaoClassificacao =
                Integer.parseInt(scanner.nextLine());

        ClassificacaoEtaria classificacao =
                switch (opcaoClassificacao) {

                    case 1 -> ClassificacaoEtaria.LIVRE;
                    case 2 -> ClassificacaoEtaria.DEZ;
                    case 3 -> ClassificacaoEtaria.DOZE;
                    case 4 -> ClassificacaoEtaria.QUATORZE;
                    case 5 -> ClassificacaoEtaria.DEZESSEIS;
                    default -> ClassificacaoEtaria.DEZOITO;
                };

        // =========================================
        // IDIOMAS
        // =========================================

        Set<Idioma> idiomas = new HashSet<>();

        System.out.println("\nIdiomas aceitos:");
        System.out.println("1 - Português");
        System.out.println("2 - Inglês");

        System.out.print("Digite os números separados por espaço: ");

        String[] escolhasIdioma =
                scanner.nextLine().split(" ");

        for (String escolha : escolhasIdioma) {

            if (escolha.equals("1")) {
                idiomas.add(Idioma.PORTUGUES);
            }

            if (escolha.equals("2")) {
                idiomas.add(Idioma.INGLES);
            }
        }

        // =========================================
        // PERFIL
        // =========================================

        PerfilCinefilo perfil = new PerfilCinefilo(
                pesosGenero,
                duracaoMinima,
                duracaoMaxima,
                classificacao,
                idiomas
        );

        // =========================================
        // FILMES JÁ ASSISTIDOS
        // =========================================

        System.out.println(
                "\nDigite filmes já assistidos " +
                        "(digite FIM para encerrar):"
        );

        while (true) {

            String filme = scanner.nextLine();

            if (filme.equalsIgnoreCase("FIM")) {
                break;
            }

            perfil.adicionarFilmeAssistido(filme);
        }

        // =========================================
        // NOTAS
        // =========================================

        System.out.println(
                "\nDeseja adicionar notas para filmes? (s/n)"
        );

        String respostaNotas = scanner.nextLine();

        if (respostaNotas.equalsIgnoreCase("s")) {

            while (true) {

                System.out.print("\nID do filme (ou FIM): ");

                String idFilme = scanner.nextLine();

                if (idFilme.equalsIgnoreCase("FIM")) {
                    break;
                }

                System.out.print("Nota (1 a 5): ");

                int nota =
                        Integer.parseInt(scanner.nextLine());

                perfil.adicionarNota(idFilme, nota);
            }
        }

        // =========================================
        // USUÁRIO
        // =========================================

        Usuario usuario = new Usuario(
                nome,
                idade,
                perfil,
                true
        );

        // =========================================
        // CATÁLOGO MOCK
        // =========================================

        CatalogoFilmesAPI catalogo = () -> List.of(

                new Filme(
                        "F01",
                        "Duna: Parte Dois",
                        2024,
                        166,
                        List.of(
                                Genero.FICCAO_CIENTIFICA,
                                Genero.DRAMA
                        ),
                        ClassificacaoEtaria.QUATORZE,
                        Idioma.INGLES,
                        92
                ),

                new Filme(
                        "F02",
                        "Ela (Her)",
                        2013,
                        126,
                        List.of(
                                Genero.FICCAO_CIENTIFICA,
                                Genero.DRAMA,
                                Genero.ROMANCE
                        ),
                        ClassificacaoEtaria.DEZESSEIS,
                        Idioma.INGLES,
                        78
                ),

                new Filme(
                        "F03",
                        "O Iluminado",
                        1980,
                        146,
                        List.of(Genero.TERROR),
                        ClassificacaoEtaria.DEZOITO,
                        Idioma.INGLES,
                        88
                ),

                new Filme(
                        "F04",
                        "Interestelar",
                        2014,
                        169,
                        List.of(
                                Genero.FICCAO_CIENTIFICA,
                                Genero.DRAMA
                        ),
                        ClassificacaoEtaria.DOZE,
                        Idioma.INGLES,
                        95
                ),

                new Filme(
                        "F05",
                        "Click",
                        2006,
                        107,
                        List.of(
                                Genero.COMEDIA,
                                Genero.DRAMA
                        ),
                        ClassificacaoEtaria.DOZE,
                        Idioma.INGLES,
                        65
                ),

                new Filme(
                        "F06",
                        "A Chegada",
                        2016,
                        116,
                        List.of(
                                Genero.FICCAO_CIENTIFICA,
                                Genero.DRAMA
                        ),
                        ClassificacaoEtaria.DOZE,
                        Idioma.INGLES,
                        84
                )
        );

        // =========================================
        // MOCKS
        // =========================================

        HistoricoUsuarioRepository historico =
                (u, recomendacoes) -> {

                    System.out.println(
                            "\n[Historico salvo com sucesso]"
                    );
                };

        NotificadorPush notificador =
                (u, mensagem) -> {

                    System.out.println(
                            "\n[Push enviado]"
                    );

                    System.out.println(mensagem);
                };

        GeradorAleatorio gerador =
                (min, max) -> min;

        // =========================================
        // SERVIÇO
        // =========================================

        RecomendadorService service =
                new RecomendadorService(
                        catalogo,
                        historico,
                        notificador,
                        gerador,
                        new CalculadoraScore(),
                        new FiltroFilmes()
                );

        // =========================================
        // RECOMENDAÇÕES
        // =========================================

        System.out.println(
                "\n======================================="
        );

        System.out.println(
                "      RECOMENDAÇÕES PARA " +
                        usuario.getNome().toUpperCase()
        );

        System.out.println(
                "======================================="
        );

        List<Recomendacao> recomendacoes =
                service.recomendar(usuario, 5);

        if (recomendacoes.isEmpty()) {

            System.out.println(
                    "\nNenhum filme compatível encontrado."
            );

        } else {

            int posicao = 1;

            for (Recomendacao recomendacao : recomendacoes) {

                System.out.println(
                        "\n" + posicao + ". " +
                                recomendacao.getFilme().getTitulo()
                );

                System.out.println(
                        "Score: " +
                                recomendacao.getScore()
                );

                System.out.println(
                        "Justificativa: " +
                                recomendacao.getJustificativa()
                );

                posicao++;
            }
        }

        // =========================================
        // SURPREENDA-ME
        // =========================================

        System.out.println(
                "\nDeseja usar o modo Surpreenda-me? (s/n)"
        );

        String surpresa =
                scanner.nextLine();

        if (surpresa.equalsIgnoreCase("s")) {

            Recomendacao recomendacaoAleatoria =
                    service.RecomendadorService(usuario);

            System.out.println(
                    "\n======================================="
            );

            System.out.println(
                    "         FILME SURPRESA"
            );

            System.out.println(
                    "======================================="
            );

            System.out.println(
                    "\nFilme: " +
                            recomendacaoAleatoria.getFilme().getTitulo()
            );

            System.out.println(
                    "Score: " +
                            recomendacaoAleatoria.getScore()
            );
        }

        scanner.close();
    }
}
