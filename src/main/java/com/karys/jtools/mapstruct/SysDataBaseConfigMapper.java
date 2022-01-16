package com.karys.jtools.mapstruct;

import com.karys.jtools.controller.SettingController;
import com.karys.jtools.entity.SysDataSourceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysDataBaseConfigMapper {

    SysDataBaseConfigMapper INSTANCE = Mappers.getMapper(SysDataBaseConfigMapper.class);

    @Mappings({
            @Mapping(source = "setting.url.value", target = "dataBaseUrl", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "setting.driver.value", target = "driverName", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "setting.userName.value", target = "userName", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "setting.password.value", target = "password", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "setting.author.value", target = "author", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "setting.rootPath.value", target = "rootPath", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    SysDataSourceConfig settingToEntity(SettingController.SettingProperty setting);
}
