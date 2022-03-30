package com.karys.jtools.menu;

import com.karys.jtools.views.SettingView;
import javafx.scene.Parent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SettingMenu implements Menu {

    @Resource
    private SettingView settingView;

    @Override
    public String getName() {
        return "设置";
    }

    @Override
    public Parent getParent() {
        return settingView.getView();
    }

    @Override
    public void clean() {
        settingView.getPresenter().reset();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
