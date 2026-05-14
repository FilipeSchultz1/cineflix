import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class FilmeTest {

    @Test
    @DisplayName("deve criar filme com todos os atributos preenchidos")
    void deve_CriarFilme_Quando_AtributosValidos() {

        // Arrange + Act
        Filme filme = new Filme(
                "F01",
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
        );

        // Assert
        assertAll(
                () -> assertEquals("F01", filme.getId()),
                () -> assertEquals("Interestelar", filme.getTitulo()),
                () -> assertEquals(2014, filme.getAno()),
                () -> assertEquals(169, filme.getDuracaoMinutos()),
                () -> assertEquals(95, filme.getPopularidade()),
                () -> assertEquals(
                        Idioma.INGLES,
                        filme.getIdioma()
                )
        );
    }

    @Test
    @DisplayName("deve considerar filmes iguais quando possuem mesmo id")
    void deve_ConsiderarFilmesIguais_Quando_MesmoId() {

        // Arrange
        Filme filme1 = new Filme(
                "F01",
                "Filme A",
                2020,
                120,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.PORTUGUES,
                80
        );

        Filme filme2 = new Filme(
                "F01",
                "Filme B",
                2023,
                150,
                List.of(Genero.COMEDIA),
                ClassificacaoEtaria.DEZESSEIS,
                Idioma.INGLES,
                99
        );

        // Act + Assert
        assertEquals(filme1, filme2);

        assertEquals(
                filme1.hashCode(),
                filme2.hashCode()
        );
    }

    @Test
    @DisplayName("deve considerar filmes diferentes quando ids sao diferentes")
    void deve_ConsiderarFilmesDiferentes_Quando_IdsDiferentes() {

        // Arrange
        Filme filme1 = new Filme(
                "F01",
                "Filme A",
                2020,
                120,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.PORTUGUES,
                80
        );

        Filme filme2 = new Filme(
                "F02",
                "Filme A",
                2020,
                120,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.PORTUGUES,
                80
        );

        // Assert
        assertNotEquals(filme1, filme2);
    }
}
