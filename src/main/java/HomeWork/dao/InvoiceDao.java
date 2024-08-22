package HomeWork.dao;

import HomeWork.ConnectionManager;
import HomeWork.entity.Invoice;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceDao implements Dao<Integer, Invoice> {

    private static final InvoiceDao INSTANCE = new InvoiceDao();

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM invoices;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO invoices(INVOICE_ID, NUMBER, CLIENT_ID, INVOICE_TOTAL, PAYMENT_TOTAL, INVOICE_DATE, DUE_DATE, PAYMENT_DATE) 
            VALUES(?,?,?,?,?,?,?,?); 
            """;

    private static final String UPDATE_SQL = """
            UPDATE invoices SET DUE_DATE =? WHERE CLIENT_ID=?
            """;
    private static final String UPDATE_INVOICE_SQL = """
            UPDATE invoices SET (INVOICE_ID, NUMBER, CLIENT_ID, INVOICE_TOTAL, PAYMENT_TOTAL, INVOICE_DATE, DUE_DATE, PAYMENT_DATE) 
            VALUES(?,?,?,?,?,?,?,?);
            """;

    private static final String DELETE_WITH_CLIENT_ID_SQL = """
            DELETE FROM invoices WHERE CLIENT_ID = ?
            """;
    private static final String DELETE_WITH_INVOICE_ID_SQL = """
            DELETE FROM invoices WHERE INVOICE_ID = ?
            """;

    private static final String FIND_BY_CLIENT_ID_SQL = """
            SELECT * FROM invoices WHERE CLIENT_ID = ?
            """;
    private static final String FIND_BY_INVOICE_ID_SQL = """
            SELECT * FROM invoices WHERE INVOICE_ID = ?
            """;

    private InvoiceDao() {

    }

    @Override
    public List<Invoice> findAll() {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice invoice = null;
            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoice = new Invoice(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4),
                        resultSet.getBigDecimal(5),
                        resultSet.getObject(6, LocalDate.class),
                        resultSet.getObject(7, LocalDate.class),
                        resultSet.getObject(8, LocalDate.class)
                );
                invoices.add(invoice);

            }
            return invoices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Invoice entity) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, entity.getInvoiceId());
            preparedStatement.setString(2, entity.getNumber());
            preparedStatement.setInt(3, entity.getClientId());
            preparedStatement.setBigDecimal(4, entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(5, entity.getPaymentTotal());
            preparedStatement.setDate(6, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(7, Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(8, Date.valueOf(entity.getPaymentDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Integer id) {
        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteByClientId(Integer id) {
        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WITH_CLIENT_ID_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteByInvoiceID(Integer id) {
        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WITH_INVOICE_ID_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


 /*   @Override
    public void update(Invoice entity) {
        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INVOICE_SQL);
            preparedStatement.setInt(1,entity.getInvoiceId());
            preparedStatement.setString(2,entity.getNumber());
            preparedStatement.setInt(3,entity.getClientId());
            preparedStatement.setBigDecimal(4,entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(5,entity.getPaymentTotal());
            preparedStatement.setDate(6, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(7,Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(8,Date.valueOf(entity.getPaymentDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
*/

    @Override
    public Optional<Invoice> findById(Integer id) {
        Connection connection = ConnectionManager.openConnection();
        Invoice invoice = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_INVOICE_ID_SQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println(resultSet.getString(2));
                invoice = new Invoice(resultSet.getInt(1),
                        resultSet.getString(2), Integer.valueOf(resultSet.getInt(3)),
                        resultSet.getBigDecimal(4),
                        resultSet.getBigDecimal(5),
                        resultSet.getDate(6).toLocalDate(),
                        resultSet.getDate(7).toLocalDate(),
                        resultSet.getDate(8).toLocalDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(invoice);
    }

    public static InvoiceDao getInstance() {
        return INSTANCE;
    }

}