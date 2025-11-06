package unoeste.fipp.dentalfx.db.dals;

import unoeste.fipp.dentalfx.db.entidades.*;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AgendaDAL {
    public Agenda get(Dentista dentista, LocalDate date){
        Agenda agenda = new Agenda(dentista,date);
        String sql = "SELECT * FROM consulta where den_id = #1 AND con_data = '#2'";
        sql.replace("#1",dentista.getId()+"");
        sql.replace("#2",date.toString());
        PessoaDal dalPac = new PessoaDal();
        MaterialDAL dalMat = new MaterialDAL();
        ProcedimentoDAL procedimentoDAL = new ProcedimentoDAL();
        try {
            ResultSet rs = SingletonDB.getConexao().consultar(sql);
            while (rs.next()){
                Horario horario = new Horario(rs.getInt("con_horario"),(Paciente) dalPac.get(rs.getInt("pac_id"),new Paciente()));
                if(rs.getBoolean("con_efetivado")){
                    Atendimento atendimento =  new Atendimento(rs.getString("con_relato"));
                    int idConsulta = rs.getInt("con_id");
                    ResultSet rsmat = SingletonDB.getConexao().consultar("SELECT * FROM cons_mat WHERE con_id = "+idConsulta);
                    while (rsmat.next()){
                        atendimento.addMaterial(rsmat.getInt("cm_quant"),dalMat.get(rsmat.getInt("mat_id")));
                    }
                    rsmat = SingletonDB.getConexao().consultar("SELECT * FROM cons_proc WHERE con_id = "+idConsulta);
                    while (rsmat.next()){
                        atendimento.addProcedimento(rsmat.getInt("cp_quanto"),procedimentoDAL.get(rsmat.getInt("pro_id")));
                    }

                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agenda;
    }

    public boolean gravar(Agenda agenda){
        //apagar todas as linhas da tabela cons_mat relacionados a data e ao dentista (Informações da agenda recebida por parametro)
        //apagar todas as linhas da tabela cons_proc relacionados a data e ao dentista (Informações da agenda recebida por parametro)
        //teste o delete cascade e use opcional,emte para as duas ações acima
        //apagar todas as consultas relacionadas a data e ao dentista
        //gravar denovo
        //gravar tudo novamente
        return true;
    }
}
