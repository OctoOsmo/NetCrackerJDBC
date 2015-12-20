import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;

/**
 * Created by al on 02.12.2015.
 */
public class Main {
    public static void main(String[] args) {
        final Logger log = LogManager.getLogger(Main.class);

        try{
            SqlStuff ss = new SqlStuff();
            ss.doSQL();
            ss.doPreparedStatement();
            ss.doCallableStatement();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
