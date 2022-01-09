package com.karys.jtools.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenerateConfig {

    @NotBlank(message = "项目不能为空")
    private String project;

    @NotBlank(message = "项目包路径不能为空")
    private String packagePath;

    @NotBlank(message = "作者不能为空")
    private String author;

    @NotBlank(message = "表名不能为空")
    private String tableNames;
}
