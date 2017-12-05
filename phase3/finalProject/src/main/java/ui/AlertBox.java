package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author jialin
 * This class represents an alert box
 */

@SuppressWarnings("restriction")
public class AlertBox {

    public static void display(String title, String message) {
        Stage window;
        Scene scene;
        Label lblAlert;
        Button btnClose;

        // Stage
        window = new Stage();
        // block input events or user interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(150);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

        // Label
        lblAlert = new Label();
        lblAlert.getStyleClass().add("lblAlert1");
        lblAlert.setText(message);

        // Button
        btnClose = new Button("Confirm!");
        btnClose.getStyleClass().add("btnConfirm1");
        btnClose.setOnAction(event -> window.close());

        // Layout
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(lblAlert, btnClose);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(10));

        // Scene
        scene = new Scene(mainLayout, 300, 100);
        scene.getStylesheets().add("ui/plag.css");
        window.setScene(scene);
        // display the window and before going back, it needs to be closed
        window.showAndWait();
    }
}
