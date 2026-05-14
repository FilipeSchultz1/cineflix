package service;

import model.Filme;
import model.PerfilCinefilo;
import model.enums.Genero;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroFilmes {

    public List<Filme> filtrar(
            List<Filme> catalogo,
            PerfilCinefilo perfil
    ) {

        if (catalogo == null || catalogo.isEmpty()) {
            return Collections.emptyList();
        }

        return catalogo.stream()
                .filter(filme -> !jaFoiAssistido(filme, perfil))
                .filter(filme -> classificacaoCompativel(filme, perfil))
                .filter(filme -> idiomaAceito(filme, perfil))
                .filter(filme -> generoPermitido(filme, perfil))
                .collect(Collectors.toList());
    }

    private boolean jaFoiAssistido(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        return perfil.getFilmesAssistidos()
                .contains(filme.getTitulo());
    }

    private boolean classificacaoCompativel(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        return filme.getClassificacaoEtaria()
                .getIdadeMinima()
                <=
                perfil.getClassificacaoMaxima()
                        .getIdadeMinima();
    }

    private boolean idiomaAceito(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        return perfil.getIdiomasAceitos()
                .contains(filme.getIdioma());
    }

    private boolean generoPermitido(
            Filme filme,
            PerfilCinefilo perfil
    ) {

        for (Genero genero : filme.getGeneros()) {

            if (perfil.getPesoGenero(genero) == 0.0) {
                return false;
            }
        }

        return true;
    }
}