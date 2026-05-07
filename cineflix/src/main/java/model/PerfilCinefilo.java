package model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exception.DuracaoInvalidaException;
import exception.PesoInvalidoException;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

public class PerfilCinefilo {

    private final Map<Genero, Double> pesosGenero;

    private final int duracaoMinima;
    private final int duracaoMaxima;

    private final ClassificacaoEtaria classificacaoMaxima;

    private final Set<Idioma> idiomasAceitos;

    private final Set<String> filmesAssistidos;

    private final Map<String, Integer> notasFilmes;

    public PerfilCinefilo(
            Map<Genero, Double> pesosGenero,
            int duracaoMinima,
            int duracaoMaxima,
            ClassificacaoEtaria classificacaoMaxima,
            Set<Idioma> idiomasAceitos
    ) {

        validarPesos(pesosGenero);
        validarDuracao(duracaoMinima, duracaoMaxima);

        this.pesosGenero = new EnumMap<>(pesosGenero);

        this.duracaoMinima = duracaoMinima;
        this.duracaoMaxima = duracaoMaxima;

        this.classificacaoMaxima = classificacaoMaxima;

        this.idiomasAceitos = EnumSet.copyOf(idiomasAceitos);

        this.filmesAssistidos = new HashSet<>();

        this.notasFilmes = new HashMap<>();
    }

    private void validarPesos(Map<Genero, Double> pesosGenero) {

        for (Double peso : pesosGenero.values()) {

            if (peso < 0.0 || peso > 1.0) {

                throw new PesoInvalidoException(
                        "Peso deve estar entre 0.0 e 1.0"
                );
            }
        }
    }

    private void validarDuracao(int min, int max) {

        if (min > max) {

            throw new DuracaoInvalidaException(
                    "Duracao minima nao pode ser maior que maxima"
            );
        }
    }

    public void adicionarFilmeAssistido(String tituloFilme) {
        filmesAssistidos.add(tituloFilme);
    }

    public void adicionarNota(String idFilme, int nota) {

        if (nota < 1 || nota > 5) {

            throw new IllegalArgumentException(
                    "Nota deve estar entre 1 e 5"
            );
        }

        notasFilmes.put(idFilme, nota);
    }

    public Double getPesoGenero(Genero genero) {
        return pesosGenero.getOrDefault(genero, 0.0);
    }

    public int getDuracaoMinima() {
        return duracaoMinima;
    }

    public int getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public Set<Idioma> getIdiomasAceitos() {
        return Collections.unmodifiableSet(idiomasAceitos);
    }

    public Set<String> getFilmesAssistidos() {
        return Collections.unmodifiableSet(filmesAssistidos);
    }

    public Map<String, Integer> getNotasFilmes() {
        return Collections.unmodifiableMap(notasFilmes);
    }
}
