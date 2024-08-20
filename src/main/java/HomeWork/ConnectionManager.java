package HomeWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String url ="db.url";
    private static final String username="db.username";
    private static final String password ="db.password";


    private ConnectionManager(){

    }

    public static Connection openConnection(){
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(url),
                    PropertiesUtil.get(username),
                    PropertiesUtil.get(password)
            );
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
