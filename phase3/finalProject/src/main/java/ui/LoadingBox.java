package ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingBox {

    public static void display(String title) {

        // Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);
        window.setMinHeight(100);
        window.show();

        // ProgressBar
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(-1.0f);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1.0f);

        // Layout
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(progressBar, progressIndicator);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(hBox, 200, 200);

        scene.getStylesheets().add("plag.css");
        window.setScene(scene);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished( event -> window.close() );
        delay.play();
    }
}
