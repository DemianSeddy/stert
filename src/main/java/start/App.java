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
        String sqlString="select name,doc_name,discount,sum(sum_discount) sumdiscount,sum(SummaFull) sumfull from" +
                " (select  br.name," +
                " case dh.dtype when 151 then 'заказов в личный объем'" +
                " when 152 then ' заказов в резерв'" +
                " when 153 then 'заказов по дисконтной карте'" +
                " when 154 then ' заказов реализации уценки'" +
                " when 155 then ' заказов на сотрудника'" +
                " when 156 then ' заказов на сотрудника под з/п'" +
                " when 157 then ' заказов на клиента'" +
                " when 158 then ' заказов прочие расходы'" +
                " when 159 then ' заказов на перемещение в представительства'" +
                " when 1501 then 'лист заказов под реализацию'" +
                " when 1502 then 'лист заказов по чековой книжке'" +
                " when 1503 then 'лист заказов возврат в главное пр-во'" +
                " when 1504 then 'лист заказов возврат от клиента'" +
                " when 1505 then 'лист заказов постоянного покупателя'" +
                " when 1506 then 'лист заказов возврат от партнера (дистрибьютор, ПП и др.)'" +
                " when 1507 then 'лист заказов по рецепту врача'" +
                " when 1508 then 'лист заказов при подписании нового дистрибьютора по проекту'" +
                " when 1509 then 'лист заказов на приём китайского специалиста'" +
                " when 1510 then 'лист заказов на клиента'" +
                " end  doc_name," +
                " round((1-ord.Markup)*100) discount," +
                " sum(ord.Amount*ord.Price*os.CurrencyRate*ord.Markup) sum_discount," +
                " sum(ord.Amount*ord.Price*os.CurrencyRate) SummaFull" +
                " from doc_headers dh" +
                " inner join orders os on ((os.id=dh.id) and (os.id_branch=dh.branch))" +
                " inner join orderdetails ord on ((ord.orderbranch=dh.branch) and (ord.orderid=dh.id))" +
                " inner join branches br on (br.id = dh.branch)" +
                " join goods g on g.id=ord.good" +
                " where dh.doc_date between '01.01.2019' and '31.03.2019'" +
                " and DH.DTYPE in (151,152,1504,153,154,155,157,1501,1502,1504,1505,1506,1507,1508,1509,1510)" +
                " and g.name like '%#%'" +
                " and  br.id=1" +
                " and os.delete_date is null" +
                " group by ord.Markup,br.name,dh.dtype,g.name" +
                " order by dh.dtype,br.name)" +
                " group by discount,name,doc_name" +
                " order by discount DESC";

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Connection con= DriverManager.getConnection( conString,paramConnection);
            Statement stm= con.createStatement();
            ResultSet res= stm.executeQuery(sqlString);
            while (res.next()) {
                System.out.print("ИСЦ|"+ res.getString("name"));
                System.out.print("|Документ|"+ res.getString("doc_name"));
                System.out.print("|Процент|"+ res.getString("discount"));
                System.out.print("|Сумма без скидки|"+ res.getString("sumdiscount"));
                System.out.print("|Сумма|"+ res.getString("sumfull"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
