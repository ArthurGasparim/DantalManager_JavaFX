package unoeste.fipp.dentalfx.db.entidades;

public class Horario {
    private int sequencia;
    private Paciente paciente;
    private Atendimento atendimento;
    private int conId;

    public Horario(int sequencia, Paciente paciente) {
        this.sequencia = sequencia;
        this.paciente = paciente;
        atendimento = null;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public Horario() {
        this(0,null);
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
}
