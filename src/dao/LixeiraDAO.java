package dao;

import ConnectionDB.FactoryConnectionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Financa;

public class LixeiraDAO {
    
    // SCHEMA do banco de dados
    private static final String SCHEMA = "wenner_lucas";
    
    /**
     * 
     * @param financa financa a ser criada
     * <p>
     * Adiciona uma financa ao banco de dados na tabela Lixeira
     * </p>
     * @throws SQLException 
     */
    public static void createLixeira(Financa financa) throws SQLException{
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "INSERT INTO " + SCHEMA + ".lixeira(nome, classificacao, valor, data_entrada, data_cadastro, tipo_transacao) VALUES (?,?,?,?,?,?);";
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
     * @param financa financa a ser deletada
     * Deleta a financa passada como parametro
     * @throws SQLException 
     */
    public static void deleteLixeira(Financa financa) throws SQLException{
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "DELETE FROM " + SCHEMA + ".lixeira WHERE "
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
    
    /**
     * 
     * <p>
     * Remove todos os itens da lixeira
     * </p>
     * @throws SQLException 
     */
    public static void removeAllItensLixeira() throws SQLException{
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "DELETE FROM " + SCHEMA + ".lixeira;";
        PreparedStatement query = c.prepareStatement(SQL);
        query.executeUpdate();
        c.close();
    }
    
    /**
     * 
     * <p>
     * Cria uma lista de financas com todas as financas da tabela lixeira
     * </p>
     * @return Uma lista de financas
     * @throws SQLException 
     */
    public static List<Financa> getAllItensLixeira() throws SQLException{
        List<Financa> list = new ArrayList<>();
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "SELECT * FROM " + SCHEMA + ".lixeira;";
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
     * <p>
     * Busca a ultima financa apagada
     * </p>
     * @return Uma financa
     * @throws SQLException 
     */
    public static Financa getFirstItemLixeira() throws SQLException{
        Connection c = FactoryConnectionDB.getInstace();
        String SQL = "SELECT * FROM " + SCHEMA + ".lixeira ORDER BY id DESC LIMIT 1;";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String classificacao = rs.getString("classificacao");
            float valor = rs.getFloat("valor");
            Date dataEntrada = rs.getDate("data_entrada");
            Date dataCadastro = rs.getDate("data_cadastro");
            String tipoTransacao = rs.getString("tipo_transacao");
            
            Financa financa = new Financa(id, nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
            
            return financa;
        }
        
        return null;
    }
}
