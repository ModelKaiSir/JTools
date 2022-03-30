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
import java.util.Objects;
import java.util.Optional;

@Component
public class SysDataBaseConfigServiceImpl implements SysDataBaseConfigService {

    @Resource
    private DB mapDb;

    @Resource
    private AppConfig appConfig;

    public static final ObservableList<String> driverList = FXCollections.observableArrayList();

    static {
        driverList.add("com.mysql.cj.jdbc.Driver");
        driverList.add("com.mysql.cj.jdbc.Driver2");
    }

    @PostConstruct
    public void start() {

    }

    @Override
    public SysDataSourceConfig getSysDataBaseConfig() {

        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG, Serializer.JAVA).createOrOpen();

        if (null != var) {
            return (SysDataSourceConfig) var.get();
        }

        return new SysDataSourceConfig();
    }

    @Override
    public boolean getConfigFlag() {
        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG_FLAG, Serializer.JAVA).createOrOpen();

        if (null != var) {
            return Objects.equals(var.get(), 1);
        }

        return false;
    }

    @Transactional
    @Override
    public void setSysDataBaseConfig(SysDataSourceConfig config) {
        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG, Serializer.JAVA).createOrOpen();
        var.set(config);
        setConfigFlag(1);
    }

    @Override
    public void setConfigFlag(Integer flag) {
        Atomic.Var var = (Atomic.Var) mapDb.atomicVar(MapDbConstant.SYS_DATA_SOURCE_CONFIG_FLAG, Serializer.JAVA).createOrOpen();
        var.set(flag);
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
