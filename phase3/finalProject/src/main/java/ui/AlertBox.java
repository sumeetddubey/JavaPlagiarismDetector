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
<<<<<<< HEAD
        window.setMinWidth(100);
        window.setMinHeight(100);
=======
        window.setWidth(250);
        window.setHeight(150);
>>>>>>> master
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

        // Label
        lblAlert = new Label();
<<<<<<< HEAD
=======
        lblAlert.getStyleClass().add("lblAlert1");
>>>>>>> master
        lblAlert.setText(message);

        // Button
        btnClose = new Button("Confirm!");
<<<<<<< HEAD
=======
        btnClose.getStyleClass().add("btnConfirm1");
>>>>>>> master
        btnClose.setOnAction(event -> window.close());

        // Layout
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(lblAlert, btnClose);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(10));

        // Scene
        scene = new Scene(mainLayout, 100, 100);
<<<<<<< HEAD
=======
        scene.getStylesheets().add("plag.css");
>>>>>>> master
        window.setScene(scene);
        // display the window and before going back, it needs to be closed
        window.showAndWait();
    }
}
