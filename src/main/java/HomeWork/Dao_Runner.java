package HomeWork;

import HomeWork.dao.InvoiceDao;
import HomeWork.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Dao_Runner {
    public static void main(String[] args) {
        InvoiceDao invoiceDao = InvoiceDao.getInstance();
        List<Invoice> allInvoices = invoiceDao.findAll();
//
        allInvoices.forEach(invoice -> System.out.println(invoice));
//
/*        Invoice invoice = new Invoice(20,"333-33-33", 2,
                BigDecimal.valueOf(222.22),BigDecimal.valueOf(222.22),
                LocalDate.of(2024,02,19),
                LocalDate.of(2024,05,25),
                LocalDate.of(2024,04,01));
        invoiceDao.save(invoice);
*/


//        invoiceDao.update(3);
//        invoiceDao.deleteByInvoiceID(1);
//        invoiceDao.deleteByClientId(2);
        System.out.println(invoiceDao.findById(18));
        System.out.println("after update");
//        List<Invoice> allInvoices2 = invoiceDao.findAll();
//
 //       allInvoices2.forEach(invoice -> System.out.println(invoice));


    }
}
