package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnectionDB {
    static {
        ip = "200.18.128.56";
        user = "aula";
        password = "aula";
        database = "aula";
    }
    
    private static String ip;
    private static String database;
    private static String user;
    private static String password;
    
    public static void setConfig(String ip, String database, String user, String password) {
        FactoryConnectionDB.ip = ip.concat(user);
        FactoryConnectionDB.database = database;
        FactoryConnectionDB.user = user;
        FactoryConnectionDB.password = password;
    }
    
    /**
     * <p>Cria uma instância de {@linkplain java.sql.Connection} com as configurações padrões 
     * ou as passadas pelo usuário </p>
     * 
     * @return Uma instância de {@linkplain java.sql.Connection}
     * @throws SQLException 
     */
    public static Connection getInstace() throws SQLException{
        String host = "jdbc:postgresql://" + ip + "/" + database;
        return DriverManager.getConnection(host, user, password);
    }
}
