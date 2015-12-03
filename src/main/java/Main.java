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
        System.out.println("JDBC!");
        SqlStuff ss = new SqlStuff();
        ss.doSQL();
    }
}
