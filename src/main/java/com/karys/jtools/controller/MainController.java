package com.karys.jtools.controller;

import com.karys.jtools.core.notify.MessageNotification;
import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.mapstruct.GenerateConfigMapper;
import com.karys.jtools.menu.Menu;
import com.karys.jtools.views.SettingView;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import io.github.palexdev.materialfx.controls.cell.MFXNotificationCell;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@FXMLController
public class MainController extends AbstractFxmlController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private StackPane right;

    @FXML
    private AnchorPane menuContent;

    private MFXListView<Menu> menuTree;

    @FXML
    private VBox componentContent;

    private final static AtomicInteger EGG = new AtomicInteger(0);

    @FXML
    public void onTest(ActionEvent e) {

        System.out.println("log :" + log);
        log.info("test info log");
        log.warn("test warn log");
        log.error("test error log");
        log.debug("test debug log");

        MessageNotification message = null;
        int i = EGG.incrementAndGet();

        switch (i) {
            case 3:
                message = new MessageNotification();
                message.setTitleText("提示");
                message.setContentText("这是一个彩蛋");
                break;
            case 7:
                message = new MessageNotification();
                message.setTitleText("提示");
                message.setContentText("吔屎啦雷");
                break;
            case 10:
                message = new MessageNotification();
                message.setTitleText("提示");
                message.setContentText("Are You OK?");
                break;
            case 11:
                EGG.set(0);
                break;
        }

        Optional.ofNullable(message).ifPresent(_m -> MFXNotificationSystem.instance().setPosition(NotificationPos.BOTTOM_RIGHT).publish(_m));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenuWidgets(getBeanList(Menu.class));

        // Stage stage = getFxmlView(this).getStage();
        Platform.runLater(() -> {
            MFXNotificationSystem.instance().initOwner(root.getScene().getWindow());
            MFXNotificationCenterSystem.instance().initOwner(root.getScene().getWindow());

            MFXNotificationCenter center = MFXNotificationCenterSystem.instance().getCenter();
            center.setCellFactory(notification -> new MFXNotificationCell(center, notification));
        });
    }

    private void loadMenuWidgets(Collection<Menu> beanList) {

        // menu tree
        menuTree = new MFXListView<>();
        // test data
        StringConverter<Menu> converter = FunctionalStringConverter.to(menu -> (menu == null) ? "" : menu.getName());
        menuTree.setItems(FXCollections.observableArrayList(beanList));

        menuTree.setConverter(converter);
        menuTree.getSelectionModel().setAllowsMultipleSelection(false);
        menuTree.setBlendMode(BlendMode.MULTIPLY);
        menuTree.getSelectionModel().selectionProperty().addListener(this::menuChangeListener);
        menuContent.getChildren().add(menuTree);

        // set full anchor
        AnchorPane.setTopAnchor(menuTree, 0.00);
        AnchorPane.setLeftAnchor(menuTree, 0.00);
        AnchorPane.setRightAnchor(menuTree, 0.00);
        AnchorPane.setBottomAnchor(menuTree, 0.00);
    }

    private void menuChangeListener(MapChangeListener.Change<? extends Integer, ? extends Menu> change) {

        componentContent.getChildren().clear();

        if (Objects.nonNull(change.getValueRemoved())) {
            change.getValueRemoved().clean();
        }

        if (Objects.isNull(change.getValueAdded())) {
            for (Menu value : change.getMap().values()) {
                componentContent.getChildren().add(value.getParent());
            }
        } else {
            componentContent.getChildren().add(change.getValueAdded().getParent());
        }
    }

    public StackPane getRightContainer() {
        return right;
    }
}
