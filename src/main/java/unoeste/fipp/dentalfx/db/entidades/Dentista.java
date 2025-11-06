package unoeste.fipp.dentalfx.db.entidades;

public class Dentista extends Pessoa{
    private int cro;
    private String fone;
    private String email;

    public Dentista(int id, String nome, int cro, String fone, String email) {
        super(id, nome);
        this.cro = cro;
        this.fone = fone;
        this.email = email;
    }

    public Dentista(String nome, int cro, String fone, String email) {
        super(nome);
        this.cro = cro;
        this.fone = fone;
        this.email = email;
    }

    public Dentista() {
    }

    public int getCro() {
        return cro;
    }

    public void setCro(int cro) {
        this.cro = cro;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
