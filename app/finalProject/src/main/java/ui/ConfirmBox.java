package ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author jialin
 * This class represents a confirmation box
 */

@SuppressWarnings("restriction")
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {

        Stage window;
        Scene scene;
        Label lblConfirm;
        Button btnYes, btnNo;
        VBox mainLayout;
        HBox hBoxBtns;

        // Stage
        window = new Stage();
        // block input events or user interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(150);

        // Label
        lblConfirm = new Label();
        lblConfirm.setText(message);

        // Button
        btnYes = new Button("YES");
        btnYes.getStyleClass().add("btnYes1");
        btnNo = new Button("NO");
        btnNo.getStyleClass().add("btnNo1");

        btnYes.setOnAction(event -> {
            answer = true;
            window.close();
        });
        btnNo.setOnAction(event -> {
            answer = false;
            window.close();
        });

        // Layout
        hBoxBtns = new HBox();
        hBoxBtns.getChildren().addAll(btnYes,btnNo);
        hBoxBtns.setAlignment(Pos.CENTER);
        hBoxBtns.setSpacing(10);

        mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(lblConfirm, hBoxBtns);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(10));


        // Scene
        scene = new Scene(mainLayout, 350,150);
        scene.getStylesheets().add("ui/plag.css");
        window.setScene(scene);
        // display the window and before going back, it needs to be closed
        window.showAndWait();

        return answer;
    }

}
