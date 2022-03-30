package com.karys.jtools.utils;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.concurrent.Callable;

public class TaskUtil {

    public static <T> void simple(Callable<T> runnable, EventHandler<WorkerStateEvent> successHandler) {

        Task<Object> task = new Task<Object>() {
            @Override
            protected Object call() {

                boolean error = false;
                try {
                    running();
                    return runnable.call();
                } catch (Exception e) {
                    failed();
                    error = true;
                    return null;
                } finally {
                    if (error) {
                        succeeded();
                    }
                }
            }
        };

        task.setOnSucceeded(successHandler);
        Thread thread = new Thread(task);
        thread.start();
    }
}
