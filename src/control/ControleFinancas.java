package control;

import dao.FinancaDAO;
import model.Financa;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ControleFinancas {

    /**
     *
     * @param financa
     * @return {@code true} se financa não ter nenhum campo vazio, caso
     * contrário, retorna {@code false}
     */
    public static boolean verificarFinanca(Financa financa) {
        if (financa == null) {
            return false;
        }
        String nome = financa.getNome();
        String classificacao = financa.getClassificacao();
        float valor = financa.getValor();
        Date dataEntrada = financa.getDataFinanca();
        Date dataCadastro = financa.getDataCadastro();
        String tipoTransacao = financa.getTipoTransacao();

        return !(nome.isEmpty() || classificacao.isEmpty() || tipoTransacao.isEmpty());
    }

    public static Financa criarFinanca(String nome, String classificacao, float valor, Date dataEntrada, Date dataCadastro, String tipoTransacao) {
        Financa financa = new Financa(nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
        return verificarFinanca(financa) ? financa : null;
    }

    public static void addFinanca(Financa financa) throws SQLException {
        FinancaDAO.createFinanca(financa);
    }
    
    public static void deleteFinanca(Financa financa) throws SQLException {
        FinancaDAO.deleteFinanca(financa);
        ControlerLixeira.addFinancaLixeira(financa);
    }
    
    public static List<Financa> getFinancesByDate(Date date) throws SQLException{
        return FinancaDAO.getFinancas(date);
    }
    
    public static List<Financa> getFinancasByDates(Date date1, Date date2) throws SQLException{
        return FinancaDAO.getFinancas(date1, date2);
    }
    
    public static List<Financa> getAllFinancas() throws SQLException {
        return FinancaDAO.getAllFinancas();
    }
}
