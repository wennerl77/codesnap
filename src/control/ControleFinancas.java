package control;

import dao.FinancaDAO;
import model.Financa;
import java.sql.Date;
import java.sql.SQLException;

public class ControleFinancas {
    /**
     * 
     * @param financa
     * @return {@code true} se financa não ter nenhum campo vazio, caso contrário, retorna {@code false}
     */
    public static boolean criarFinanca(Financa financa) {
        if (financa == null) return false;
        String nome = financa.getNome();
        String classificacao = financa.getClassificacao();
        float valor = financa.getValor();
        Date dataEntrada = financa.getDataFinanca();
        Date dataCadastro = financa.getDataCadastro();
        String tipoTransacao = financa.getTipoTransacao();
        
        if (nome.isEmpty() || classificacao.isEmpty() || tipoTransacao.isEmpty()) return false;
        
        try {
            FinancaDAO.createFinanca(financa);
        } catch (SQLException ex) {
            System.err.println("ERRO ao criar financa: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
