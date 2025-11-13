package unoeste.fipp.dentalfx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private Pessoa dentista;
    private LocalDate data;
    private List<Horario> horarioList;

    public Agenda(Pessoa dentista, LocalDate data) {
        this.dentista = dentista;
        this.data = data;
        horarioList = new ArrayList<>();
        //criando os 10 horários de atendimento diário
        for(int i=0; i < 10; i++){
            horarioList.add(new Horario());
        }
    }

    public void addHorario(Horario horario, int sequencia){
        horarioList.set(sequencia, horario);
    }


    public Pessoa getDentista() {
        return dentista;
    }

    public void setDentista(Pessoa dentista) {
        this.dentista = dentista;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public Horario getHorario(int i) {
        return horarioList.get(i-1);
    }
}
