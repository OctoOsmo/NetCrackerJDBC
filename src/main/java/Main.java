import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.beans.Statement;
import java.io.*;
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

        System.out.println("JDBC!");

        try{
            SqlStuff ss = new SqlStuff();
            ss.doSQL();
            ss.doPreparedStatement();
            ss.doCallableStatement();
        } catch (SQLException e) {
            log.error("Issue with connection to database");
//            e.printStackTrace();
        }
    }
}
