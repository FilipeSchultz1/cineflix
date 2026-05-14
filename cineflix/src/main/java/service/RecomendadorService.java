package service;

import model.Filme;
import model.Recomendacao;
import model.Usuario;
import util.GeradorAleatorio;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecomendadorService {

    private final CatalogoFilmesAPI catalogoFilmesAPI;

    private final HistoricoUsuarioRepository historicoRepository;

    private final NotificadorPush notificadorPush;

    private final GeradorAleatorio geradorAleatorio;

    private final CalculadoraScore calculadoraScore;

    private final FiltroFilmes filtroFilmes;

    public RecomendadorService(
            CatalogoFilmesAPI catalogoFilmesAPI,
            HistoricoUsuarioRepository historicoRepository,
            NotificadorPush notificadorPush,
            GeradorAleatorio geradorAleatorio,
            CalculadoraScore calculadoraScore,
            FiltroFilmes filtroFilmes
    ) {

        this.catalogoFilmesAPI = catalogoFilmesAPI;
        this.historicoRepository = historicoRepository;
        this.notificadorPush = notificadorPush;
        this.geradorAleatorio = geradorAleatorio;
        this.calculadoraScore = calculadoraScore;
        this.filtroFilmes = filtroFilmes;
    }

    /**
     * Gera recomendações ranqueadas para o usuário.
     */
    public List<Recomendacao> recomendar(
            Usuario usuario,
            int topN
    ) {

        try {

            List<Filme> catalogo =
                    catalogoFilmesAPI.buscarTodos();

            List<Filme> filmesFiltrados =
                    filtroFilmes.filtrar(
                            catalogo,
                            usuario.getPerfilCinefilo()
                    );

            List<Recomendacao> recomendacoes =
                    filmesFiltrados.stream()
                            .map(filme -> criarRecomendacao(
                                    filme,
                                    usuario
                            ))
                            .sorted(
                                    Comparator
                                            .comparingDouble(
                                                    Recomendacao::getScore
                                            )
                                            .reversed()
                                            .thenComparing(
                                                    r -> r.getFilme().getPopularidade(),
                                                    Comparator.reverseOrder()
                                            )
                            )
                            .limit(topN)
                            .collect(Collectors.toList());

            historicoRepository.registrarRecomendacao(
                    usuario,
                    recomendacoes
            );

            enviarNotificacao(usuario);

            return recomendacoes;

        } catch (Exception e) {

            return Collections.emptyList();
        }
    }

    /**
     * Retorna uma recomendação aleatória.
     */
    public Optional<Recomendacao> recomendarAleatorio(
            Usuario usuario
    ) {

        List<Recomendacao> recomendacoes =
                recomendar(usuario, Integer.MAX_VALUE);

        if (recomendacoes.isEmpty()) {
            return Optional.empty();
        }

        int indice =
                geradorAleatorio.sortearInteiro(
                        0,
                        recomendacoes.size() - 1
                );

        return Optional.of(
                recomendacoes.get(indice)
        );
    }

    private Recomendacao criarRecomendacao(
            Filme filme,
            Usuario usuario
    ) {

        double score =
                calculadoraScore.calcularScore(
                        filme,
                        usuario.getPerfilCinefilo()
                );

        String justificativa =
                "Recomendado por compatibilidade de gêneros e perfil.";

        return new Recomendacao(
                filme,
                score,
                justificativa
        );
    }

    private void enviarNotificacao(
            Usuario usuario
    ) {

        if (usuario.isNotificacoesAtivadas()) {

            notificadorPush.enviar(
                    usuario,
                    "Sua recomendação do dia chegou!"
            );
        }
    }

    public Recomendacao RecomendadorService(Usuario usuario) {
        return null;
    }
}
