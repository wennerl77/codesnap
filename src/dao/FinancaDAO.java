package dao;

import ConnectionDB.FactoryConnectionDB;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Financa;

public class FinancaDAO {

    private static final String schema = "wenner_lucas";

    public static void createFinanca(Financa financa) throws SQLException {
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "INSERT INTO " + schema + ".financas(nome, classificacao, valor, data_entrada, data_cadastro, tipo_transacao) VALUES (?,?,?,?,?,?);";
        PreparedStatement query = c.prepareStatement(SQL);
        query.setString(1, financa.getNome());
        query.setString(2, financa.getClassificacao());
        query.setFloat(3, financa.getValor());
        query.setDate(4, financa.getDataFinanca());
        query.setDate(5, financa.getDataCadastro());
        query.setString(6, financa.getTipoTransacao());
        query.executeUpdate();
        c.close();
    }

    public static List<Financa> getFinancas(Date date) throws SQLException {
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        int mes = date.toLocalDate().getMonthValue();
        String SQL = "SELECT * FROM " + schema + ".financas WHERE EXTRACT(MONTH FROM data_entrada) = (?);";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
        preparedStatement.setInt(1, mes);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String classificacao = rs.getString("classificacao");
            float valor = rs.getFloat("valor");
            Date dataEntrada = rs.getDate("data_entrada");
            Date dataCadastro = rs.getDate("data_cadastro");
            String tipoTransacao = rs.getString("tipo_transacao");
            
            Financa financa = new Financa(id, nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
            
            list.add(financa);
        }
        
        return list;
    }
    
    public static List<Financa> getFinancas(Date date1, Date date2) throws SQLException {
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "SELECT * FROM " + schema + ".financas WHERE data_entrada >= (?) AND data_entrada <= (?);";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
        preparedStatement.setDate(1, date1);
        preparedStatement.setDate(2, date2);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String classificacao = rs.getString("classificacao");
            float valor = rs.getFloat("valor");
            Date dataEntrada = rs.getDate("data_entrada");
            Date dataCadastro = rs.getDate("data_cadastro");
            String tipoTransacao = rs.getString("tipo_transacao");
            
            Financa financa = new Financa(id, nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
            
            list.add(financa);
        }
        
        return list;
    }
}
