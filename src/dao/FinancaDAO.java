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

    // SCHEMA do banco de dados
    private static final String SCHEMA = "wenner_lucas";

    /**
     * 
     * @param financa financa a ser criada
     * <p>
     * Adiciona uma financa ao banco de dados na tabela Financa
     * </p>
     * @throws SQLException 
     */
    public static void createFinanca(Financa financa) throws SQLException {
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "INSERT INTO " + SCHEMA + ".financas(nome, classificacao, valor, data_entrada, data_cadastro, tipo_transacao) VALUES (?,?,?,?,?,?);";
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

    /**
     * 
     * @param date data
     * <p>
     * Cria uma lista de financas a partir do mes da data
     * </p>
     * @return Uma lista de financas
     * @throws SQLException 
     */
    public static List<Financa> getFinancas(Date date) throws SQLException {
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        int mes = date.toLocalDate().getMonthValue();
        int ano = date.toLocalDate().getYear();
        String SQL = "SELECT * FROM " + SCHEMA + ".financas WHERE EXTRACT(MONTH FROM data_entrada) = (?) AND EXTRACT(YEAR FROM data_entrada) = (?);";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
        preparedStatement.setInt(1, mes);
        preparedStatement.setInt(2, ano);
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
    
    /**
     * 
     * @param date1 primeira data
     * @param date2 segunda data
     * <p>
     * Cria uma lista de financas com todas as financas no intervalo entre data1 e data2
     * </p>
     * @return Uma lista de financas
     * @throws SQLException 
     */
    public static List<Financa> getFinancas(Date date1, Date date2) throws SQLException {
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "SELECT * FROM " + SCHEMA + ".financas WHERE data_entrada >= (?) AND data_entrada <= (?);";
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
    
    /**
     * 
     * <p>
     * Cria uma lista de financas com todas as financas da tabela
     * </p>
     * @return Uma lista de financas
     * @throws SQLException 
     */
    public static List<Financa> getAllFinancas() throws SQLException {
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "SELECT * FROM " + SCHEMA + ".financas;";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
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
    
    /**
     * 
     * @param financa financa a ser deletada
     * Deleta a financa passada como parametro
     * @throws SQLException 
     */
    public static void deleteFinanca(Financa financa) throws SQLException{
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "DELETE FROM " + SCHEMA + ".financas WHERE "
                + "nome = ? AND "
                + "classificacao = ? AND "
                + "valor = ? AND "
                + "data_entrada = ? AND "
                + "data_cadastro = ? AND "
                + "tipo_transacao = ?;";
        
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
}
