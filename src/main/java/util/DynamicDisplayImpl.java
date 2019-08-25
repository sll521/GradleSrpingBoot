package util;

import domain.HeaderI18Name;

/**
 * @program: GradleSpringBoot
 * @description:
 * @author: songleilei
 * @create: 2019-08-25 22:17
 **/
public class DynamicDisplayImpl implements DynamicDisplay {
    @Override
    public boolean isDisplay(String name) {
        return !name.equals(HeaderI18Name.BANK_ACCOUNT);
    }
}
