package unoeste.fipp.dentalfx.db.entidades;

public class Usuario extends Pessoa {
    public int nivel;
    public String senha;

    public Usuario(int id, String nome, int nivel, String senha) {
        super(id, nome);
        this.nivel = nivel;
        this.senha = senha;
    }

    public Usuario(String nome, int nivel, String senha) {
        super(nome);
        this.nivel = nivel;
        this.senha = senha;
    }

    public Usuario() {
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
