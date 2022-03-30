package com.karys.jtools.utils;

import net.synedra.validatorfx.Check;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ValidateUtil {

    public static void notNull(Object value, Check.Context context) {
        if (Objects.isNull(value) || StringUtils.isBlank((String) value)) {
            context.error("不能为空");
        }
    }
}
