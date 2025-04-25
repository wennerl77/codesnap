package Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Financa;

public class FilesUtil {

    public static void gerarExtrato(List<Financa> list, String ganho, String gasto, String diferenca) throws IOException {
        List<String> texto = new ArrayList<>();

        String cabecalho = "%20s|%20s|%20s|%20s|%20s|%20s|".formatted("nome", "classificacao", "valor", "data_entrada", "data_cadastro", "tipo_transacao");;
        texto.add(cabecalho);
        texto.add("%126s".formatted("").replace(' ', '-'));
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
        texto.add("%126s".formatted("").replace(' ', '-'));
        texto.add("Ganho:     " + ganho);
        texto.add("Gasto:     " + gasto);
        texto.add("Diferenca: " + diferenca);
        String path = "./extrato" + DateUtil.getDateSQL(System.currentTimeMillis()) + "_" + LocalTime.now().toString().replace(':', '-') + ".txt";
        Files.write(Paths.get(path), texto);
        abrirExtrato(path);
    }
    
    public static void abrirExtrato(String path) throws IOException {
        Runtime.getRuntime().exec(new String[] {"cmd", "/c", "notepad " + path});
    }
}
