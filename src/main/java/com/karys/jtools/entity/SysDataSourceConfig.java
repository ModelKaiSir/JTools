package com.karys.jtools.entity;

import lombok.Data;

/**
 * 数据库配置
 */
@Data
public class SysDataSourceConfig {

    private String dataBaseUrl;
    private String driverName;
    private String userName;
    private String password;

    /**
     *
     * 生成代码目录根路径
     */
    private String toCodePath;

    /**
     *
     * 代码生成的作者（默认值）
     */
    private String author;
}