package Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Financa;

public class FilesUtil {

    /**
     * 
     * @param list lista de financas
     * @param ganho saldo positivo das financas
     * @param gasto saldo negativo das financas
     * @param diferenca diferenca entre os saldos
     * <p>
     * Gera um extrato no formato .txt na pasta raiz do projeto e abre ela com o notepad
     * </p>
     * @throws IOException 
     */
    public static void gerarExtrato(List<Financa> list, String ganho, String gasto, String diferenca) throws IOException {
        // Cada String da lista 'texto' eh uma linha no arquivo
        List<String> texto = new ArrayList<>();
        
        // Cabecalho onde indica o que cada coluna significa
        String cabecalho = "%20s|%20s|%20s|%20s|%20s|%20s|".formatted("nome", "classificacao", "valor", "data_entrada", "data_cadastro", "tipo_transacao");;
        // Adiciona o cabecalho
        texto.add(cabecalho);
        // Cria uma linha para separar o cabecalho das financas
        texto.add("%126s".formatted("").replace(' ', '-'));
        // Percorre todas as financas e adiciona na lista
        for (Financa f : list) {
            String nome = f.getNome();
            String classificacao = f.getClassificacao();
            float valor = f.getValor();
            java.sql.Date dataEntrada = f.getDataFinanca();
            java.sql.Date dataCadastro = f.getDataCadastro();
            String tipoTransacao = f.getTipoTransacao();
            String linha = "%20s|%20s|%20s|%20s|%20s|%20s|".formatted(nome, classificacao, String.valueOf(valor), dataEntrada.toString(), dataCadastro.toString(), tipoTransacao);
            
            texto.add(linha);
        }
        // Adiciona uma linha para indicar o final das financas
        texto.add("%126s".formatted("").replace(' ', '-'));
        // Adiciona o ganho, gasto e diferenca
        texto.add("Ganho:     " + ganho);
        texto.add("Gasto:     " + gasto);
        texto.add("Diferenca: " + diferenca);
        // Caminho do arquivo, eh utilizado a data e o tempo atual para um extrato nao sobrepor outro
        String path = "./extrato" + DateUtil.getDateSQL(System.currentTimeMillis()) + "_" + LocalTime.now().toString().replace(':', '-') + ".txt";
        // Cria o arquivo
        Files.write(Paths.get(path), texto);
        // Abre o arquivo
        abrirExtrato(path);
    }
    
    /**
     * 
     * @param path caminho do arquivo
     * <p>
     * Abre o extrato .txt no notepad
     * </p>
     * @throws IOException 
     */
    public static void abrirExtrato(String path) throws IOException {
        // Executa um comando no cmd
        try{
            Runtime.getRuntime().exec(new String[] {"cmd", "/c", "notepad " + path});
        } catch (IOException ex) {
            throw new IOException("Comando notepad " + path + " falhou, verifique se existe o arquivo e está utilizando o windows");
        }
    }
}
