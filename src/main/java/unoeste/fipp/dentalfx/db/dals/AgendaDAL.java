package unoeste.fipp.dentalfx.db.dals;

import unoeste.fipp.dentalfx.db.entidades.*;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAL {
    public Agenda get(Dentista dentista, LocalDate date){
        Agenda agenda = new Agenda(dentista,date);
        String sql = "SELECT * FROM consulta where den_id = #1 AND con_data = '#2'";
        sql = sql.replace("#1",dentista.getId()+"");
        sql = sql.replace("#2",date.toString());
        System.out.println(sql);
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
                    horario.setAtendimento(atendimento);
                }
                agenda.addHorario(horario,horario.getSequencia());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agenda;
    }

    public boolean gravar(Agenda agenda) throws Exception{
        //apagar todas as linhas da tabela cons_mat relacionados a data e ao dentista (Informações da agenda recebida por parametro)
        //apagar todas as linhas da tabela cons_proc relacionados a data e ao dentista (Informações da agenda recebida por parametro)
        System.out.println(agenda.getData().toString());
        String sql = "Select * from consulta where den_id = " + agenda.getDentista().getId()+ " and con_data = '"+agenda.getData().toString()+"'";
        System.out.println(sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()){
                int con_id = rs.getInt("con_id");
                int horario = rs.getInt("con_horario");
                String sql1 = "Delete * from cons_proc WHERE con_id="+con_id;
                String sql2 = "Delete * from cons_proc WHERE con_id="+con_id;
                SingletonDB.getConexao().manipular(sql1);
                SingletonDB.getConexao().manipular(sql2);
                Horario horarioAg = agenda.getHorario(horario);
                for(Atendimento.MatItem m : horarioAg.getAtendimento().getMaterialList()){
                    sql = """
                            Insert into cons_mat (mat_id, con_id, cm_quant) 
                            VALUES  (#1, #2, #3)
  
                            """;
                    sql = sql.replace("#1",""+ m.material().getId());
                    sql = sql.replace("#2", ""+ con_id);
                    sql = sql.replace("#3",""+ m.quant());
                    SingletonDB.getConexao().manipular(sql);
                }
                for(Atendimento.ProcItem p: horarioAg.getAtendimento().getProcedimentoList()){
                    sql = """
                            Insert into cons_proc (con_id, proc_id, cp_quant) 
                            VALUES  (#1, #2, #3)
  
                            """;
                    sql = sql.replace("#1",""+  con_id);
                    sql = sql.replace("#2", ""+p.procedimento().getId());
                    sql = sql.replace("#3",""+ p.quant());
                    SingletonDB.getConexao().manipular(sql);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //teste o delete cascade e use opcional,emte para as duas ações acima
        //apagar todas as consultas relacionadas a data e ao dentista
        //gravar denovo
        //gravar tudo novamente
        return true;
    }
}
