package com.karys.jtools;

import com.karys.jtools.core.screen.JToolsScreen;
import com.karys.jtools.views.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
public class JToolsApplication extends AbstractJavaFxApplicationSupport implements ApplicationRunner {

    public static void main(String[] args) {

        AbstractJavaFxApplicationSupport.setErrorAction(e -> {
            log.error("start app error", e);

            TextArea area = new TextArea();
            area.setEditable(false);
            area.appendText("应用程序启动失败：" + e.getMessage() + "\n");

            for (StackTraceElement element : e.getStackTrace()) {
                area.appendText(element.toString() + "\n");
            }

            MFXDialogs.error().setHeaderText("错误").setContent(area).toStageDialogBuilder().initModality(Modality.APPLICATION_MODAL).setOnCloseRequest(e1 -> {
                Platform.exit();
            }).get().showAndWait();
        });
        launch(JToolsApplication.class, MainView.class, new JToolsScreen(), args);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        return Arrays.asList(new Image(this.getClass().getResource("/icons/icon.png").toExternalForm()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("jdk {}", System.getProperty("java.version"));
        log.info("java home {}", System.getProperty("java.home"));
    }
}
