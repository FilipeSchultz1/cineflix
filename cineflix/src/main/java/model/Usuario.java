package model;

public class Usuario {

    private final String nome;

    private final int idade;

    private final PerfilCinefilo perfilCinefilo;

    private boolean notificacoesAtivadas;

    public Usuario(
            String nome,
            int idade,
            PerfilCinefilo perfilCinefilo,
            boolean notificacoesAtivadas
    ) {

        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = perfilCinefilo;
        this.notificacoesAtivadas = notificacoesAtivadas;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public boolean isNotificacoesAtivadas() {
        return notificacoesAtivadas;
    }

    public void setNotificacoesAtivadas(boolean notificacoesAtivadas) {
        this.notificacoesAtivadas = notificacoesAtivadas;
    }
}