import exception.DuracaoInvalidaException;
import exception.PesoInvalidoException;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class PerfilCinefiloTest {

    private Map<Genero, Double> pesos;

    @BeforeEach
    void setUp() {

        pesos = Map.of(
                Genero.DRAMA, 0.8,
                Genero.COMEDIA, 0.5,
                Genero.TERROR, 0.0
        );
    }

    @Test
    @DisplayName("deve criar perfil quando dados sao validos")
    void deve_CriarPerfil_Quando_DadosValidos() {

        // Arrange + Act
        PerfilCinefilo perfil = new PerfilCinefilo(
                pesos,
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                Set.of(
                        Idioma.PORTUGUES,
                        Idioma.INGLES
                )
        );

        // Assert
        assertNotNull(perfil);

        assertEquals(
                0.8,
                perfil.getPesoGenero(Genero.DRAMA)
        );
    }

    @Test
    @DisplayName("deve lancar excecao quando peso for invalido")
    void deve_LancarExcecao_Quando_PesoForaDoIntervalo() {

        // Arrange
        Map<Genero, Double> pesosInvalidos = Map.of(
                Genero.ACAO, 1.5
        );

        // Act + Assert
        assertThrows(
                PesoInvalidoException.class,
                () -> new PerfilCinefilo(
                        pesosInvalidos,
                        90,
                        150,
                        ClassificacaoEtaria.DEZESSEIS,
                        Set.of(Idioma.INGLES)
                )
        );
    }

    @Test
    @DisplayName("deve lancar excecao quando duracao minima maior que maxima")
    void deve_LancarExcecao_Quando_DuracaoInvalida() {

        // Assert
        assertThrows(
                DuracaoInvalidaException.class,
                () -> new PerfilCinefilo(
                        pesos,
                        200,
                        100,
                        ClassificacaoEtaria.DEZESSEIS,
                        Set.of(Idioma.INGLES)
                )
        );
    }

    @Test
    @DisplayName("deve adicionar filme ao historico")
    void deve_AdicionarFilmeAoHistorico_Quando_FilmeAssistido() {

        // Arrange
        PerfilCinefilo perfil = new PerfilCinefilo(
                pesos,
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                Set.of(Idioma.INGLES)
        );

        // Act
        perfil.adicionarFilmeAssistido(
                "Interestelar"
        );

        // Assert
        assertTrue(
                perfil.getFilmesAssistidos()
                        .contains("Interestelar")
        );
    }

    @Test
    @DisplayName("deve adicionar nota valida")
    void deve_AdicionarNota_Quando_NotaValida() {

        // Arrange
        PerfilCinefilo perfil = new PerfilCinefilo(
                pesos,
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                Set.of(Idioma.INGLES)
        );

        // Act
        perfil.adicionarNota("F01", 5);

        // Assert
        assertEquals(
                5,
                perfil.getNotaPara("F01")
        );
    }

    @Test
    @DisplayName("deve lancar excecao quando nota invalida")
    void deve_LancarExcecao_Quando_NotaForaDoIntervalo() {

        // Arrange
        PerfilCinefilo perfil = new PerfilCinefilo(
                pesos,
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                Set.of(Idioma.INGLES)
        );

        // Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> perfil.adicionarNota(
                        "F01",
                        10
                )
        );
    }

    @Test
    @DisplayName("deve retornar null quando filme nunca foi avaliado")
    void deve_RetornarNull_Quando_FilmeNuncaAvaliado() {

        // Arrange
        PerfilCinefilo perfil = new PerfilCinefilo(
                pesos,
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                Set.of(Idioma.INGLES)
        );

        // Assert
        assertNull(
                perfil.getNotaPara("FILME_INEXISTENTE")
        );
    }
}
