package HomeWork.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Invoice {
    private Integer invoiceId;
    private String number;
    private Integer clientId;
    private BigDecimal invoiceTotal;
    private BigDecimal paymentTotal;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;
}
