package service;

import model.Filme;
import model.PerfilCinefilo;
import model.enums.Genero;

public class CalculadoraScore {

    public static final double PESO_GENERO = 0.50;
    public static final double PESO_DURACAO = 0.20;
    public static final double PESO_POPULARIDADE = 0.15;
    public static final double PESO_AFINIDADE = 0.15;

    public double calcularScore(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        double scoreGenero = calcularScoreGenero(filme, perfil);

        double scoreDuracao = calcularScoreDuracao(filme, perfil);

        double scorePopularidade = filme.getPopularidade();

        double scoreAfinidade = calcularScoreAfinidade(perfil);

        double scoreFinal =
                (scoreGenero * PESO_GENERO)
                        +
                        (scoreDuracao * PESO_DURACAO)
                        +
                        (scorePopularidade * PESO_POPULARIDADE)
                        +
                        (scoreAfinidade * PESO_AFINIDADE);

        return limitarScore(scoreFinal);
    }

    public double calcularScoreGenero(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        double soma = 0.0;

        for (Genero genero : filme.getGeneros()) {
            soma += perfil.getPesoGenero(genero);
        }

        double media = soma / filme.getGeneros().size();

        return media * 100;
    }

    public double calcularScoreDuracao(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        int duracao = filme.getDuracaoMinutos();

        if (
                duracao >= perfil.getDuracaoMinima()
                        &&
                        duracao <= perfil.getDuracaoMaxima()
        ) {
            return 100;
        }

        int distancia;

        if (duracao < perfil.getDuracaoMinima()) {
            distancia = perfil.getDuracaoMinima() - duracao;
        } else {
            distancia = duracao - perfil.getDuracaoMaxima();
        }

        double score = 100 - (distancia * 1.25);

        return Math.max(score, 0);
    }

    private double calcularScoreAfinidade(
            PerfilCinefilo perfil
    ) {

        if (perfil.getNotasFilmes().isEmpty()) {
            return 50;
        }

        double media =
                perfil.getNotasFilmes()
                        .values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0);

        return (media / 5.0) * 100;
    }

    private double limitarScore(double score) {

        if (score < 0) {
            return 0;
        }

        return Math.min(score, 100);
    }
}
