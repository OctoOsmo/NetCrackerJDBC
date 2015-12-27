import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by al on 02.12.2015.
 */
public class Main {
    public static void main(String[] args) {
        final Logger log = LogManager.getLogger(Main.class);

        try (InputStream propFile = new FileInputStream("JDBC_Properties.txt")){
            Properties props = new Properties();
            props.load(propFile);
            log.info("trying to connect to database on url = " + props.getProperty("url"));
            try (Connection conn = DriverManager.getConnection(props.getProperty("url"), props)){
                log.info("Connection established");
                SqlStuff ss = new SqlStuff(conn);
                ss.doSQL();
                ss.doPreparedStatement();
                ss.doCallableStatement();
            }
        } catch (IOException |SQLException e){
            log.error(e.getMessage());
        }
    }
}
