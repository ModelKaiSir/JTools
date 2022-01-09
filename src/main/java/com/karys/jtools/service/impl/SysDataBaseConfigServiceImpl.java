package com.karys.jtools.service.impl;

import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.service.SysDataBaseConfigService;
import org.springframework.stereotype.Component;

@Component
public class SysDataBaseConfigServiceImpl implements SysDataBaseConfigService {

    @Override
    public SysDataSourceConfig getSysDataBaseConfig() {
        return new SysDataSourceConfig();
    }
}
