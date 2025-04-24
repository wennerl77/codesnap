package Util;

import java.time.LocalDate;

public class DateUtil {

    public static boolean isDateFormatted(String d) {
        if (d.isEmpty() || d.isEmpty()) {
            return false;
        }
        String[] data = d.split("/");
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
    
    public static java.sql.Date getDateSQL(String d) {
        if (!isDateFormatted(d)) return null;
        String[] data = d.split("/");
        int dia, mes, ano;
        dia = Integer.parseInt(data[0]);
        mes = Integer.parseInt(data[1]);
        ano = Integer.parseInt(data[2]);
        
        LocalDate localDate = LocalDate.of(ano, mes, dia);
        return java.sql.Date.valueOf(localDate);
    }
    
    public static java.sql.Date getDateSQL(long timeMillis) {
        return new java.sql.Date(timeMillis);
    }
    
    public static String getDate(java.sql.Date d) {
        String[] data = d.toString().split("-");
        int dia, mes, ano;
        dia = Integer.parseInt(data[2]);
        mes = Integer.parseInt(data[1]);
        ano = Integer.parseInt(data[0]);
        
        return dia + "/" + mes + "/" + ano;
    }
    
    public static String formattedDate(String date) {
        date = date.replace('-', '/');
        String[] d = date.split("/");
        if (d.length != 3) return null;
        if (d[0].length() < 2) {
            d[0] = "0" + d[0];
        }
        if (d[1].length() < 2) {
            d[1] = "0" + d[1];
        }
        if (d[2].length() < 4) {
            d[2] = "%04s" .formatted(d[2]);
        }
        
        String dataFormatted = "";
        dataFormatted += d[0] + "/" + d[1] + "/" + d[2];
        return dataFormatted;
    }
}
