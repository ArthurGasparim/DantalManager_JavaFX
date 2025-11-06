package unoeste.fipp.dentalfx.db.entidades;

public class Procedimento {
    private int id;
    private double tempo,valor;
    private String descricao;

    public Procedimento(int id, double tempo, double valor, String descricao) {
        this.id = id;
        this.tempo = tempo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Procedimento() {
        this(0,0,0,"");
    }

    public Procedimento(double tempo, double valor, String descricao) {
        this(0,tempo,valor,descricao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
