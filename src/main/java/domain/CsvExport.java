package domain;

import lombok.Data;
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
public class CsvExport {

    @CsvHeaderAttribute(name = "name")
    @CsvAttribute
    private String name;

    @CsvHeaderAttribute(name = "gender")
    @CsvAttribute
    private String gender;

    @CsvHeaderAttribute(name = "bankAccount", dynamicDisplay = true)
    @CsvAttribute
    private String bankAccount;

    @CsvHeaderAttribute(name = "amout")
    @CsvAttribute
    private BigDecimal amout;
}
