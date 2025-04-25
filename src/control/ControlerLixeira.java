package control;

import dao.LixeiraDAO;
import java.sql.SQLException;
import java.util.List;
import model.Financa;

public class ControlerLixeira {
    public static void addFinancaLixeira(Financa financa) throws SQLException{
        LixeiraDAO.addLixeira(financa);
    }
    
    public static void deleteFinancaLixeira(Financa financa) throws SQLException {
        LixeiraDAO.removeLixeira(financa);
    }
    
    public static List<Financa> getAllItensLixeira() throws SQLException {
        return LixeiraDAO.getAllItensLixeira();
    }
    
    public static Financa getFirstItemLixeira() throws SQLException {
        return LixeiraDAO.getFirstItemLixeira();
    }
}
