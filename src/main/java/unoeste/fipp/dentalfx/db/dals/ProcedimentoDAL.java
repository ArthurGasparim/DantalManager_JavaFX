package unoeste.fipp.dentalfx.db.dals;

import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Procedimento;
import unoeste.fipp.dentalfx.db.util.IDAL;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProcedimentoDAL implements IDAL<Procedimento> {
    @Override
    public boolean gravar(Procedimento entidade) {
        String sql="""
          INSERT INTO procedimento (pro_desc, pro_valor, pro_tempo) 
          VALUES ('#1',#2,#3)""";
        sql=sql.replace("#1",entidade.getDescricao());
        sql=sql.replace("#2",String.format(Locale.US,"%.2f",entidade.getValor()));
        sql=sql.replace("#3",String.format(Locale.US,"%.2f",entidade.getTempo()));
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Procedimento entidade) {
        String sql="""
          UPDATE procedimento SET pro_desc='#1', pro_valor=#2, pro_tempo=#4 
          WHERE pro_id=#3""";
        sql=sql.replace("#1",entidade.getDescricao());
        sql=sql.replace("#2",String.format(Locale.US,"%.2f",entidade.getValor()));
        sql=sql.replace("#3",""+entidade.getId());
        sql=sql.replace("#4",String.format(Locale.US,"%.2f",entidade.getTempo()));
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Procedimento entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM procedimento WHERE pro_id="+entidade.getId());
    }

    @Override
    public Procedimento get(int id) {
        Procedimento procedimento=null;
        String sql="SELECT * FROM procedimento WHERE pro_id="+id;
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try {
            if(resultSet.next()){
                procedimento =new Procedimento(resultSet.getInt("pro_id"),resultSet.getDouble("pro_tempo"),
                        resultSet.getDouble("pro_valor"),resultSet.getString("pro_desc")
                       );
            }
        } catch (Exception e) {  throw new RuntimeException(e); }
        return procedimento;
    }

    @Override
    public List<Procedimento> get(String filtro) {
        List<Procedimento> procedimentoList = new ArrayList<>();
        String sql="SELECT * FROM procedimento ";
        if(!filtro.isEmpty()){
            sql+="WHERE "+filtro;
        }

        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        //System.out.println(SingletonDB.getConexao().getMensagemErro());
        //System.out.println(sql);
        try {
            while(resultSet.next()){
                procedimentoList.add(new Procedimento(resultSet.getInt("pro_id"),resultSet.getDouble("pro_tempo"),
                        resultSet.getDouble("pro_valor"),resultSet.getString("pro_desc")
                ));
            }
        } catch (Exception e) {  throw new RuntimeException(e); }
        return procedimentoList;
    }
}
