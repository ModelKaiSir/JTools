package com.karys.jtools;

import com.karys.jtools.views.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JToolsApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(JToolsApplication.class, MainView.class, args);
    }
}
