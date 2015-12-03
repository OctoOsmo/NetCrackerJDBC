import java.beans.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by al on 03.12.2015.
 */
public class SqlStuff {

    String url = "jdbc:postgresql://who.duckdns.org/NetCracker";
    Connection conn;

    public SqlStuff(){
        Properties props = new Properties();
        InputStream propFile = null;
        try {
            propFile = new FileInputStream("JDBC_Properties.txt");
            props.load(propFile);
            conn = DriverManager.getConnection(url, props);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doSQL() {

        try (java.sql.Statement stmt = conn.createStatement()){
        ResultSet rs = stmt.executeQuery("select * from url_decomposed");
        int colsCount = rs.getMetaData().getColumnCount();
        String tableName = rs.getMetaData().getTableName(1);

        int i = 0;
        while (rs.next() && i <= 99){
            int url_id = rs.getInt("url_id");
            String raw_data = rs.getString("raw_data");
            String schema = rs.getString("schema");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String host = rs.getString("host");
            String port = rs.getString("port");
            String path = rs.getString("path");
            String parameters  = rs.getString("parameters");
            String anchor = rs.getString("anchor");
            System.out.println(rs.getString("raw_data"));
            System.out.printf("url_id = %d" +
                    ", schema = %s" +
                    ", login = %s" +
                    ", password = %s" +
                    ", host = %s" +
                    ", port = %s" +
                    "\n", url_id, schema, login, password, host, port);
            i++;
        }

        System.out.println("ROWS COUNT: " + colsCount);
        System.out.println("Table name: " + tableName);
        for (int j = 1; j < colsCount; j++) {
            System.out.println(rs.getMetaData().getColumnLabel(j));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doPrepearedStatement(){
//        Pre
    }

}
