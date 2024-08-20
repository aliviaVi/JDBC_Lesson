package HomeWork;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final String CREATE_SQL= """
    CREATE TABLE jdbc1
    (
    id int auto_increment primary key,
    info text
    )
    """;

    private static final String ISERT_SQL= """
    INSERT INTO jdbc1(info)
    VALUES ('Test1'),
           ('Test2'),
           ('Test3')
    """;

    private static final String DELETE_SQL= """
    DELETE FROM jdbc1
    WHERE id=3;
    """;

    private static final String SELECT_SQL= """
    SELECT id,info FROM jdbc1 ;
    """;



    public static void main(String[] args) {

//        try(Connection connection= ConnectionManager.openConnection()){
//
//            Statement statement=connection.createStatement();
//            boolean execute=statement.execute(CREATE_SQL);
//            System.out.println(execute);

//            int update = statement.executeUpdate(ISERT_SQL);
//            System.out.println("update = " + update);

//            int update = statement.executeUpdate(DELETE_SQL);
//            System.out.println("update = " + update);

//            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
//            while (resultSet.next()){
//                int anInt = resultSet.getInt("id");
//                String string = resultSet.getString("info");
//                System.out.println(anInt+ " " +string);
//            }
//
//        }catch (SQLException e){
//            throw  new RuntimeException(e);
//        }

        String invoiceId ="1";
        List<Integer> integers = sqlInjectionMethod(invoiceId);
        integers.forEach(x-> System.out.println(x));
    }
    static List<Integer> sqlInjectionMethod(String invoiceId){
        String sql_injection = """
                select invoice_id
                from invoices
                where invoice_id = %s
                """.formatted(invoiceId);

        try (Connection connection = ConnectionManager.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_injection);
            List<Integer> invoiceIds = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("invoice_id");
                invoiceIds.add(id);
            }
            return invoiceIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}