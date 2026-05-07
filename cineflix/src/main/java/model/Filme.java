package model;

import java.util.List;
import java.util.Objects;

import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

public final class Filme {

    private final String id;
    private final String titulo;
    private final int ano;
    private final int duracaoMinutos;
    private final List<Genero> generos;
    private final ClassificacaoEtaria classificacaoEtaria;
    private final Idioma idioma;
    private final int popularidade;

    public Filme(
            String id,
            String titulo,
            int ano,
            int duracaoMinutos,
            List<Genero> generos,
            ClassificacaoEtaria classificacaoEtaria,
            Idioma idioma,
            int popularidade
    ) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.duracaoMinutos = duracaoMinutos;
        this.generos = List.copyOf(generos);
        this.classificacaoEtaria = classificacaoEtaria;
        this.idioma = idioma;
        this.popularidade = popularidade;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public int getPopularidade() {
        return popularidade;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Filme filme)) {
            return false;
        }

        return Objects.equals(id, filme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}