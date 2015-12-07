import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by al on 03.12.2015.
 */
public class SqlStuff {

    private Connection conn;
    private Logger log = LogManager.getLogger(SqlStuff.class);

    public SqlStuff(){
        Properties props = new Properties();
        InputStream propFile;
        try {
            propFile = new FileInputStream("JDBC_Properties.txt");
            props.load(propFile);
            log.info("trying to connect to database on url = " + props.getProperty("url"));
            conn = DriverManager.getConnection(props.getProperty("url"), props);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printResultSet(ResultSet rs) throws SQLException {
        int colCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String line = "";
            for (int i = 1; i <= colCount; i++) {
                line += rs.getString(i) + " ";
            }
            System.out.println(line);
        }
    }

    private void printUrlResultSet(ResultSet rs, int row_count) throws SQLException {
        int i = 0;
        while (rs.next() && i <= row_count){
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
            System.out.printf("schema = %s" +
                    ", login = %s" +
                    ", password = %s" +
                    ", host = %s" +
                    ", port = %s" +
                    "\n", schema, login, password, host, port);
            i++;
        }
    }

    public void doSQL() {
        log.debug("start of function");
        try (java.sql.Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("select * from url_decomposed where schema = \'smb\'");
            int colsCount = rs.getMetaData().getColumnCount();
            String tableName = rs.getMetaData().getTableName(1);

            printUrlResultSet(rs, 5);

            log.debug("Table name: " + tableName);
            log.debug("Column count = " + colsCount);
            String colNames = "column names:";
            for (int j = 1; j < colsCount; j++) {
                colNames += " " + (rs.getMetaData().getColumnLabel(j));
            }
            log.debug(colNames);
            log.debug("end of usual Statement");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("end of function");
    }

    public void doPreparedStatement(){
        log.debug("start of function");
        String sql = "select * from url_decomposed where schema = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "ftp");
            ResultSet rs = ps.executeQuery();
            printUrlResultSet(rs, 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("end of function");
    }

    public void doCallableStatement(){
        log.debug("start of function");
        String call = "{call url_max_pass_len(?, ?)}";
        try (CallableStatement cs = conn.prepareCall(call)){
            cs.setInt(1,0);
            cs.setInt(2,100);
            ResultSet rs = cs.executeQuery();
            log.debug("printing result of callable statement");
            rs.next();
            rs.getInt(1);
            log.info("max url length between 0 and 1000 url_id is " + rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("end of function");
    }
}
