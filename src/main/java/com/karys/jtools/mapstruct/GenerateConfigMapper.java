package com.karys.jtools.mapstruct;

import com.karys.jtools.controller.CodeGenerateController;
import com.karys.jtools.controller.SettingController;
import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.entity.SysDataSourceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenerateConfigMapper {

    GenerateConfigMapper INSTANCE = Mappers.getMapper(GenerateConfigMapper.class);

    @Mappings({
            @Mapping(source = "generate.project.value", target = "project"),
            @Mapping(source = "generate.packagePath.value", target = "packagePath"),
            @Mapping(source = "generate.tableNames.value", target = "tableNames"),
            @Mapping(source = "generate.author.value", target = "author")
    })
    GenerateConfig generateToEntity(CodeGenerateController.GenerateFormProperty generate);
}
