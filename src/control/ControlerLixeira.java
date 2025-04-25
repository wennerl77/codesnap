package control;

import dao.LixeiraDAO;
import java.sql.SQLException;
import java.util.List;
import model.Financa;

public class ControlerLixeira {
    /** 
     * 
     * @param financa financa a ser adicionada
     * <p>
     * Adiciona Financa ao banco de dados na tabela lixeira
     * </p> 
     * @throws SQLException 
     */
    public static void addFinancaLixeira(Financa financa) throws SQLException{
        LixeiraDAO.createLixeira(financa);
    }
    
    /**
     * 
     * @param financa financa a ser removida
     * <p>
     * Deleta a financa passada como parametro da lixeira
     * </p>
     * @throws SQLException 
     */
    public static void deleteFinancaLixeira(Financa financa) throws SQLException {
        LixeiraDAO.removeLixeira(financa);
    }
    
    /**
     * <p>
     * Deleta todas as financas da lixeira
     * </p>
     * @throws SQLException 
     */
    public static void deleteALlFinancaLixeira() throws SQLException {
        LixeiraDAO.removeAllItensLixeira();
    }
    
    /**
     * <p>
     * Busca todas as financas da lixeira
     * </p>
     * @return um {@linkplain java.util.List} de {@linkplain model.Financa} com todas as financas da lixeira
     * @throws SQLException 
     */
    public static List<Financa> getAllItensLixeira() throws SQLException {
        return LixeiraDAO.getAllItensLixeira();
    }
    
    /**
     * Pega a primeira financa, a ultima a ser excluida
     * @return Ultimo {@linkplain model.Financa} a ser apagado
     * @throws SQLException 
     */
    public static Financa getFirstItemLixeira() throws SQLException {
        return LixeiraDAO.getFirstItemLixeira();
    }
}
