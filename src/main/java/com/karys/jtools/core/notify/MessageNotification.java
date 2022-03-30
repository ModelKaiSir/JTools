package com.karys.jtools.core.notify;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.font.FontResources;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MessageNotification extends MFXSimpleNotification {

    private final SimpleStringProperty titleText = new SimpleStringProperty("系统提示");
    private final SimpleStringProperty contentText = new SimpleStringProperty();

    public MessageNotification() {

        VBox left = new VBox();
        MFXIconWrapper icon = new MFXIconWrapper(FontResources.INFO.getDescription(), 125, 150);
        left.getChildren().add(icon);
        left.setAlignment(Pos.CENTER);
        left.setPrefSize(200, 200);
        left.setStyle("-fx-background-color: #9D9D9DFF;");

        Text title = new Text();
        title.textProperty().bind(titleText);
        title.setFont(Font.font(32));
        title.setFill(Paint.valueOf("#286ada"));

        Text content = new Text();
        content.textProperty().bind(contentText);
        content.setFont(Font.font(18));
        content.setWrappingWidth(380);

        VBox center = new VBox();
        center.getChildren().add(title);
        center.getChildren().add(content);
        center.setSpacing(10);

        BorderPane container = new BorderPane();
        container.setLeft(left);
        container.setCenter(center);

        container.setStyle("-fx-background-color: white;");
        container.setPrefWidth(600);
        container.setMinHeight(200);
        container.setPadding(new Insets(5, 5, 5, 5));
        BorderPane.setMargin(center, new Insets(0, 0, 0, 5));
        setContent(container);
    }

    public void setTitleText(String titleText) {
        this.titleText.set(titleText);
    }

    public void setContentText(String contentText) {
        this.contentText.set(contentText);
    }
}
