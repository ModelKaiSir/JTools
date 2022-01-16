package com.karys.jtools.listener;

import org.mapdb.DB;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AppStopListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private DB mapDb;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        mapDb.close();
    }
}
