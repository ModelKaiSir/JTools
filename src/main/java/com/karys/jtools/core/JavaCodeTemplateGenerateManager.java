package com.karys.jtools.core;

import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.enums.CodeGenerateEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * mybatis 模板代码生成管理类
 */
@Component
public class JavaCodeTemplateGenerateManager {

    static final Map<CodeGenerateEnum, BiConsumer<SysDataSourceConfig, GenerateConfig>> GENERATE_ENUM_BI_FUNCTION_MAP = new HashMap<>();

    static {

        GENERATE_ENUM_BI_FUNCTION_MAP.put(CodeGenerateEnum.CONTROLLER, ControllerCodeGenerator::generate);
        GENERATE_ENUM_BI_FUNCTION_MAP.put(CodeGenerateEnum.ENTITY, EntityCodeGenerator::generate);
        GENERATE_ENUM_BI_FUNCTION_MAP.put(CodeGenerateEnum.SERVICE, ServiceCodeGenerator::generate);
    }

    public void generate(CodeGenerateEnum type, SysDataSourceConfig dataSourceConfig, GenerateConfig config) {
        GENERATE_ENUM_BI_FUNCTION_MAP.get(type).accept(dataSourceConfig, config);
    }
}
