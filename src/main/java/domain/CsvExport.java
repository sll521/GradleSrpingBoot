package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.CsvAttribute;
import util.CsvHeaderAttribute;

import java.math.BigDecimal;

/**
 * @program: GradleSpringBoot
 * @description:
 * @author: songleilei
 * @create: 2019-08-25 13:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvExport {

    @CsvHeaderAttribute(name = "name")
    @CsvAttribute(international = false)
    private String name;

    @CsvHeaderAttribute(name = "gender")
    @CsvAttribute
    private String gender;

    @CsvHeaderAttribute(name = "bankAccount", dynamicDisplay = true)
    @CsvAttribute
    private String bankAccount;

    @CsvHeaderAttribute(name = "amount")
    @CsvAttribute(isMoney = true)
    private BigDecimal amount;
}
