package start;
import java.sql.*;
import java.io.*;
import java.util.Properties;
/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args )
    {

        String conString = "jdbc:firebirdsql:lao:F:\\test\\02\\LiWest.fdb";
        Properties paramConnection = new Properties();
        paramConnection.setProperty("user", "SYSDBA");
        paramConnection.setProperty("password", "ktybdsq,fnjy");
        paramConnection.setProperty("encoding", "WIN1251");

        System.out.println( "Hello World!" );
        try {


            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Connection con= DriverManager.getConnection( conString,paramConnection);
            Statement stm= con.createStatement();
            ResultSet res= stm.executeQuery("select * from ");
            while (res.next()) {
                System.out.println("Bsumm_balls:"
                        + res.getString("summ_balls"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
