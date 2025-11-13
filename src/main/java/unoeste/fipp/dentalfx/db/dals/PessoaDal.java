package unoeste.fipp.dentalfx.db.dals;

import unoeste.fipp.dentalfx.db.entidades.*;
import unoeste.fipp.dentalfx.db.util.IDAL;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PessoaDal implements IDAL<Pessoa> {
    @Override
    public boolean gravar(Pessoa entidade) {
        String sql = "";
        if(entidade instanceof Paciente){
            sql="""
                    INSERT INTO paciente(
                    	pac_cpf, pac_nome, pac_cep, pac_rua, pac_numero, pac_bairro, pac_cidade, pac_uf, pac_fone, pac_email, pac_histo)
                    	VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #A, #B)""";
            sql=sql.replace("#1",((Paciente) entidade).getCpf());
            sql=sql.replace("#2",((Paciente) entidade).getNome());
            sql=sql.replace("#3",((Paciente) entidade).getCep());
                sql=sql.replace("#4",((Paciente) entidade).getRua());
                sql=sql.replace("#5",((Paciente) entidade).getNumero());
                sql=sql.replace("#6",((Paciente) entidade).getBairro());
                sql=sql.replace("#7",((Paciente) entidade).getCidade());
                sql=sql.replace("#8",((Paciente) entidade).getUf());
                sql=sql.replace("#9",((Paciente) entidade).getFone());
                sql=sql.replace("#A",((Paciente) entidade).getEmail());
                sql=sql.replace("#B",((Paciente) entidade).getHistorico());
        } else if (entidade instanceof Dentista) {
            sql =
                    """
                            INSERT INTO dentista(
                            	den_nome, den_cro, den_fone, den_email)
                            	VALUES ('#2', #5, '#3', '#4');
                            """;
            sql = sql.replace("#2", "" + ((Dentista) entidade).getNome());
            sql = sql.replace("#3", "" + ((Dentista) entidade).getFone());
            sql = sql.replace("#4", "" + ((Dentista) entidade).getEmail());
            sql = sql.replace("#5", "" + ((Dentista) entidade).getCro());
        }
        else {
            sql =
                    """
                            INSERT INTO usuario(
                            	uso_nome, uso_nivel, uso_senha)
                            	VALUES ('#2', #3, '#4');
                            """;
            sql = sql.replace("#2", "" + ((Usuario) entidade).getNome());
            sql = sql.replace("#3", "" + ((Usuario) entidade).getNivel());
            sql = sql.replace("#4", "" + ((Usuario) entidade).getSenha());
        }
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Pessoa entidade) {
        String sql = "";
        if (entidade instanceof Paciente) {
            sql = """
                    UPDATE paciente
                    	SET pac_cpf=#1, pac_nome=#2, pac_cep=#4, pac_rua=#5, pac_numero=#6, pac_bairro=#7, pac_cidade=#8, pac_uf=#9, pac_fone=#A, pac_email=#B, pac_histo=#C
                    	WHERE pac_id=#3""";
            sql = sql.replace("#1", ((Paciente) entidade).getCpf());
            sql = sql.replace("#3", "" + entidade.getId());
            sql=sql.replace("#2",((Paciente) entidade).getNome());
            sql=sql.replace("#4",((Paciente) entidade).getCep());
            sql=sql.replace("#5",((Paciente) entidade).getRua());
            sql=sql.replace("#6",((Paciente) entidade).getNumero());
            sql=sql.replace("#7",((Paciente) entidade).getBairro());
            sql=sql.replace("#8",((Paciente) entidade).getCidade());
            sql=sql.replace("#9",((Paciente) entidade).getUf());
            sql=sql.replace("#A",((Paciente) entidade).getFone());
            sql=sql.replace("#B",((Paciente) entidade).getEmail());
            sql=sql.replace("#C",((Paciente) entidade).getHistorico());
        } else if (entidade instanceof Dentista) {
            sql =
                    """
                            UPDATE dentista SET
                            	den_nome = '#2', den_cro=#5, den_fone='#3', den_email = '#4
                            	WHERE den_id = #1
                                        """;
            sql = sql.replace("#1",""+((Dentista)entidade).getId());
            sql = sql.replace("#2",""+((Dentista)entidade).getNome());
            sql = sql.replace("#3",""+((Dentista)entidade).getFone());
            sql = sql.replace("#4",""+((Dentista)entidade).getEmail());
            sql = sql.replace("#5",""+((Dentista)entidade).getCro());

        }
        else {

            sql =
                    """
                           UPDATE usuario SET
                            	uso_nome='#2', uso_nivel = #3, uso_senha = '#4'
                            	WHERE uso_id = #1
                            """;

            sql = sql.replace("#1", "" + ((Usuario) entidade).getId());
            sql = sql.replace("#2", "" + ((Usuario) entidade).getNome());
            sql = sql.replace("#3", "" + ((Usuario) entidade).getNivel());
            sql = sql.replace("#4", "" + ((Usuario) entidade).getSenha());
            System.out.println(sql);
        }
        SingletonDB.getConexao().manipular(sql);
        System.out.println(SingletonDB.getConexao().getMensagemErro());
        return true;
    }


    @Override
    public boolean apagar(Pessoa entidade) {
        if(entidade instanceof Paciente)
            return SingletonDB.getConexao().manipular("DELETE FROM paciente WHERE pac_id="+entidade.getId());
        else if(entidade instanceof Dentista){
            return SingletonDB.getConexao().manipular("DELETE FROM dentista WHERE den_id="+entidade.getId());
        }
        else {
            return SingletonDB.getConexao().manipular("DELETE FROM usuario WHERE uso_id="+entidade.getId());
        }
    }

    @Override
    public Pessoa get(int id) {

        return null;
    }

    public Pessoa get(int id,Pessoa pessoa) {
        Pessoa retorno = null;
        if(pessoa instanceof Paciente){
            String sql="SELECT * FROM paciente WHERE pac_id="+id;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            try {
                if(resultSet.next()){
                    retorno =new Paciente(resultSet.getInt("pac_id"),resultSet.getString("pac_nome"),resultSet.getString("pac_cpf"),resultSet.getString("pac_cep"),resultSet.getString("pac_cidade"),resultSet.getString("pac_bairro")
                            ,resultSet.getString("pac_numero"),resultSet.getString("pac_rua"),resultSet.getString("pac_uf"),resultSet.getString("pac_email"),resultSet.getString("pac_fone"),resultSet.getString("pac_histo"));

                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        } else if (pessoa instanceof Dentista) {
            String sql="SELECT * FROM dentista WHERE den_id="+id;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            try {
                if(resultSet.next()){
                    retorno =new Dentista(resultSet.getInt("den_id"),resultSet.getString("den_nome"),resultSet.getInt("den_cro"),resultSet.getString("den_fone"),resultSet.getString("den_email"));
                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        }
        else {
            String sql="SELECT * FROM usuario WHERE usu_id="+id;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            try {
                if(resultSet.next()){
                    retorno =new Usuario(resultSet.getInt("uso_id"),resultSet.getString("uso_nome"),resultSet.getInt("uso_nivel"),resultSet.getString("uso_senha"));
                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        }

        return retorno;
    }

    @Override
    public List<Pessoa> get(String filtro) {
        return List.of();
    }


    public List<Pessoa> get(String filtro,Pessoa pessoa) {
        List<Pessoa> pessoaList = new ArrayList<>();;
        if(pessoa instanceof Paciente){
            String sql="SELECT * FROM paciente";
            if(!filtro.isEmpty())
                sql+=" WHERE "+filtro;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            System.out.println(SingletonDB.getConexao().getMensagemErro());
            try {
                while(resultSet.next()){
                    Paciente paciente =new Paciente(resultSet.getInt("pac_id"),resultSet.getString("pac_nome"),resultSet.getString("pac_cpf"),resultSet.getString("pac_cep"),resultSet.getString("pac_cidade"),resultSet.getString("pac_bairro")
                            ,resultSet.getString("pac_numero"),resultSet.getString("pac_rua"),resultSet.getString("pac_uf"),resultSet.getString("pac_email"),resultSet.getString("pac_fone"),resultSet.getString("pac_histo"));
                    pessoaList.add(paciente);
                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        } else if (pessoa instanceof Dentista) {
            String sql="SELECT * FROM dentista";
            if(!filtro.isEmpty())
                sql+=" WHERE "+filtro;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            System.out.println(SingletonDB.getConexao().getMensagemErro());
            try {
                while(resultSet.next()){
                    Dentista dentista =  new Dentista(resultSet.getInt("den_id"),resultSet.getString("den_nome"),resultSet.getInt("den_cro"),resultSet.getString("den_fone"),resultSet.getString("den_email"));
                    pessoaList.add(dentista);
                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        }
        else{
            String sql="SELECT * FROM usuario";
            if(!filtro.isEmpty())
                sql+=" WHERE "+filtro;
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            System.out.println(SingletonDB.getConexao().getMensagemErro());
            try {
                while(resultSet.next()){
                    Usuario usuario =  new Usuario(resultSet.getInt("uso_id"),resultSet.getString("uso_nome"),resultSet.getInt("uso_nivel"),resultSet.getString("uso_senha"));
                    pessoaList.add(usuario);
                }
            } catch (Exception e) {  throw new RuntimeException(e); }
        }
        return pessoaList;
    }
}
