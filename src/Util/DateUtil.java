package Util;

import java.time.LocalDate;

public class DateUtil {

    /**
     *
     * @param d data
     * <p>
     * Verifica se d é um formato de data utilizado no Brasil: dd/MM/yyyy e
     * verifica tambêm se d é uma data válida, ex: <br> 32/02/2025 -
     * {@code false}<br> 03/12/2023 - {@code true}
     * </p>
     * @return {@code true} se a data estiver no formato dd/MM/yyyy e se a data
     * for válida, caso contrario, {@code false}
     */
    public static boolean isDateFormatted(String d) {
        if (d.isEmpty() || d.isEmpty()) {
            return false;
        }
        String[] data = d.split("/");
        // Se não for data[0] = dd, data[1] = MM, data[2] == yyyy
        if (data.length != 3) {
            return false;
        }
        int dia, mes, ano;

        try {
            dia = Integer.parseInt(data[0]);
            mes = Integer.parseInt(data[1]);
            ano = Integer.parseInt(data[2]);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (ano < 0) {
            return false;
        }

        // Cada mes possui um limite de dias (não considera ano bissexto)
        return switch (mes) {
            case 1, 3, 5, 7, 8, 10, 12 ->
                dia >= 1 && dia <= 31;
            case 4, 6, 9, 11 ->
                dia >= 1 && dia <= 30;
            case 2 ->
                dia >= 1 && dia <= 29;
            default ->
                false;
        };
    }

    /**
     *
     * @param d data
     * <p>
     * Transforma a data recebida em uma data no formato
     * {@linkplain java.sql.Date} "yyyy-MM-dd", ex: <br>
     * 21/03/2025 - 2025-03-21
     * </p>
     *
     * @return retorna uma data {@linkplain java.sql.Date}
     */
    public static java.sql.Date getDateSQL(String d) {
        if (!isDateFormatted(d)) {
            return null;
        }
        String[] data = d.split("/");
        int dia, mes, ano;
        dia = Integer.parseInt(data[0]);
        mes = Integer.parseInt(data[1]);
        ano = Integer.parseInt(data[2]);

        LocalDate localDate = LocalDate.of(ano, mes, dia);
        return java.sql.Date.valueOf(localDate);
    }

    /**
     *
     * @param timeMillis tempo em millisegundos
     * <p>
     * Gera uma data {@linkplain java.sql.Date} a partir de milisegundos
     * </p>
     * @return {@linkplain java.sql.Date} de milisegundos
     */
    public static java.sql.Date getDateSQL(long timeMillis) {
        return new java.sql.Date(timeMillis);
    }

    /**
     *
     * @param d data
     * <p>
     * Transforma a data em {@linkplain java.sql.Date} para o formato utilizado
     * no Brasil, dd/MM/yyyy
     * </p>
     * @return Uma String com a data no formato dd/MM/yyyy
     */
    public static String getDate(java.sql.Date d) {
        String[] data = d.toString().split("-");
        int dia, mes, ano;
        dia = Integer.parseInt(data[2]);
        mes = Integer.parseInt(data[1]);
        ano = Integer.parseInt(data[0]);

        return dia + "/" + mes + "/" + ano;
    }

    /**
     *
     * @param date data
     * <p>
     * Formata uma data caso ela esteja simplificada, exemplo: <br>
     * 4/1/2025 - 04/01/2025
     * </p>
     * @return A data formatada
     */
    public static String formattedDate(String date) {
        date = date.replace('-', '/');
        String[] d = date.split("/");
        if (d.length != 3) {
            return null;
        }
        // Formata o dia, mes e ano, caso não tenha a quantidade de caracteres suficiente
        d[0] = "%2s".formatted(d[0]).replace(' ', '0');
        d[1] = "%2s".formatted(d[1]).replace(' ', '0');
        d[2] = "%4s".formatted(d[2]).replace(' ', '0');

        // Data formatada
        String dataFormatted = "";
        dataFormatted += d[0] + "/" + d[1] + "/" + d[2];
        return dataFormatted;
    }
}
