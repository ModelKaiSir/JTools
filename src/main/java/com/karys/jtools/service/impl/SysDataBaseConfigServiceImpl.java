package com.karys.jtools.service.impl;

import com.karys.jtools.config.AppConfig;
import com.karys.jtools.constants.MapDbConstant;
import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.service.SysDataBaseConfigService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.IndexTreeList;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerJava;
import org.mapdb.serializer.SerializerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class SysDataBaseConfigServiceImpl implements SysDataBaseConfigService {

    @Resource
    private DB mapDb;

    @Resource
    private AppConfig appConfig;

    ObservableList<String> driverList;

    @PostConstruct
    public void start() {

        IndexTreeList<String> dl = mapDb.indexTreeList(MapDbConstant.DRIVER_LIST, Serializer.STRING).createOrOpen();

        if (dl.isEmpty()) {
            dl.add("com.mysql.cj.jdbc.Driver");
        }

        driverList = FXCollections.observableArrayList(dl);
    }

    @Override
    public SysDataSourceConfig getSysDataBaseConfig() {

        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG, Serializer.JAVA).createOrOpen();

        if (null != var) {
            return (SysDataSourceConfig) var.get();
        }

        return new SysDataSourceConfig();
    }

    @Transactional
    @Override
    public void setSysDataBaseConfig(SysDataSourceConfig config) {
        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG, Serializer.JAVA).createOrOpen();
        var.set(config);
    }

    @Override
    public ObservableValue<? extends ObservableList<String>> driverListProperty() {
        return new SimpleObjectProperty<>(driverList);
    }

    @Override
    public List<String> getProjectList() {
        return appConfig.getItems();
    }

    @Override
    public List<String> getObjectPackageList() {
        return appConfig.getPackages();
    }
}
