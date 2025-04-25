package control;

import dao.FinancaDAO;
import model.Financa;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ControllerFinancas {

    /**
     *
     * @param financa
     * <p>
     * Verifica se financa eh valida
     * </p>
     * @return {@code true} se financa não ter nenhum campo vazio, caso
     * contrário, retorna {@code false}
     */
    public static boolean verificarFinanca(Financa financa) {
        if (financa == null) {
            return false;
        }
        String nome = financa.getNome();
        String classificacao = financa.getClassificacao();
        Date dataEntrada = financa.getDataFinanca();
        Date dataCadastro = financa.getDataCadastro();
        String tipoTransacao = financa.getTipoTransacao();

        return !(nome.isEmpty() || classificacao.isEmpty() || tipoTransacao.isEmpty() || dataCadastro.toString().isEmpty() || dataEntrada.toString().isEmpty());
    }

    /**
     * 
     * @param nome nome da financa
     * @param classificacao classificacao da financa
     * @param valor valor da financa
     * @param dataEntrada data informada pelo usuario
     * @param dataCadastro data em que a financa foi cadastrada
     * @param tipoTransacao se eh "ganho" ou "gasto"
     * <p>
     * Cria uma financa a partir dos dados informados pelo usuário
     * </p>
     * @return {@linkplain model.Financa} se a financa for valida, se nao, {@code null}
     */
    public static Financa criarFinanca(String nome, String classificacao, float valor, Date dataEntrada, Date dataCadastro, String tipoTransacao) {
        Financa financa = new Financa(nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
        return verificarFinanca(financa) ? financa : null;
    }

    /** 
     * 
     * @param financa financa a ser adicionada
     * <p>
     * Adiciona Financa ao banco de dados na tabela financa
     * </p> 
     * @throws SQLException 
     */
    public static void addFinanca(Financa financa) throws SQLException {
        FinancaDAO.createFinanca(financa);
    }
    
    /**
     * 
     * @param financa financa a ser removida
     * <p>
     * Deleta a financa passada como parametro e adiciona ela na lixeira
     * </p>
     * @throws SQLException 
     */
    public static void deleteFinanca(Financa financa) throws SQLException {
        FinancaDAO.deleteFinanca(financa);
        ControllerLixeira.addFinancaLixeira(financa);
    }
    
    /**
     * 
     * @param date data que foi buscada
     * <p>
     * busca todas as financas do mes da data
     * </p>
     * @return um {@linkplain java.util.List} de {@linkplain model.Financa} que estao no mes de data
     * @throws SQLException 
     */
    public static List<Financa> getFinancesByDate(Date date) throws SQLException{
        return FinancaDAO.getFinancas(date);
    }
    
    /**
     * 
     * @param date1 primeira data
     * @param date2 segunda data
     * <p>
     * 
     * Busca todas as financas que estao entre a data1 e data2
     * </p>
     * @return um {@linkplain java.util.List} de {@linkplain model.Financa} que estao no intervalo entre data1 e data2
     * @throws SQLException 
     */
    public static List<Financa> getFinancasByDates(Date date1, Date date2) throws SQLException{
        return FinancaDAO.getFinancas(date1, date2);
    }
    
    /**
     * <p>
     * Busca todas as financas
     * </p>
     * @return um {@linkplain java.util.List} de {@linkplain model.Financa} com todas as financas
     * @throws SQLException 
     */
    public static List<Financa> getAllFinancas() throws SQLException {
        return FinancaDAO.getAllFinancas();
    }
}
