package unoeste.fipp.dentalfx;

import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.dals.ProcedimentoDAL;
import unoeste.fipp.dentalfx.db.entidades.*;
import unoeste.fipp.dentalfx.db.util.SingletonDB;
import unoeste.fipp.dentalfx.utils.FichaPaciente;

import java.time.LocalDate;
import java.util.List;

public class MainTeste {
    public static void main(String[] args) {
        if(FichaPaciente.gerarPDF("Teste.pdf")){
            FichaPaciente.abrirPDF("Teste.pdf");
        }
        SingletonDB.conectar();
        //MaterialDAL dal=new MaterialDAL();
        //ProcedimentoDAL dal = new ProcedimentoDAL();
//        Material novo=new Material("ampola anestésica",18.25);
//        dal.gravar(novo);

        //Procedimento procedimento = new Procedimento(1.5,30,"Retirada de Cárie");
        // Procedimento procedimento = new Procedimento(0.5,25,"Consulta de Rotina");
        // dal.gravar(procedimento);
        //Procedimento nomoProcedimento = dal.get(1);
        //nomoProcedimento.setDescricao("Mudeiiii");
        //dal.alterar(nomoProcedimento);
        //dal.apagar(nomoProcedimento);
//        materialAlterado.setDescricao("Gase");
//        dal.alterar(materialAlterado);

        /*if(!dal.apagar(materialAlterado))
            System.out.println("Erro: "+SingletonDB.getConexao().getMensagemErro());
        */
       // List<Procedimento> procedimentoList = dal.get("");
        //procedimentoList.forEach(m-> System.out.println(m.getDescricao()));
       /* PessoaDal dal = new PessoaDal();
        List<Pessoa> pessoaList = dal.get("",new Paciente());
        pessoaList.forEach(m->{
            System.out.println(m.getNome());
        });*/


        //Testando agenda
        //Eu quero a agenda do dentista Zé na data de hoje
        Dentista dentista = new Dentista(2,"Zé",1234,"18996227785","email@email.com");
        PessoaDal pessoaDal = new PessoaDal();
        pessoaDal.apagar(dentista);
        List<Pessoa> pessoaList = pessoaDal.get("",new Dentista());
        Paciente paciente1 = new Paciente(1,"Rodolfo","","","","","","","","","","");
        Paciente paciente2 = new Paciente(2,"Rita","","","","","","","","","","");
        Agenda agenda = new Agenda(dentista, LocalDate.now());
        agenda.addHorário(paciente1,9);
        agenda.addHorário(paciente2,1);

        pessoaList.forEach(m->{
            System.out.println(m.getNome());
        });
        dentista = (Dentista) pessoaDal.get(1,new Dentista());
        System.out.println(dentista.getNome());
        //Momento em que o dentista registra oo atendimento
        Atendimento atendimento = new Atendimento("Blá blá blá...");
        Material material = new Material(1,"ampola anestesica",25);
        atendimento.addMaterial(2,material);
        Horario horario= agenda.getHorario(1);
        horario.setAtendimento(atendimento);
    }
}
