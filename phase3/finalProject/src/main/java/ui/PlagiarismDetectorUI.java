package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utility.Report;

import java.io.*;
import java.util.*;

import driver.PlagiarismDetector;

public class PlagiarismDetectorUI extends Application {

    private static final String LAYER_0_INTRO =
            "Layer 0 is purposed for hash code comparison. If both uploaded files are totally same, the score" +
                    "will be 100.0, otherwise it will be 0.0.";
    private static final String LAYER_1_INTRO =
            "Layer 1 is purposed for function signature comparison. It compares if two uploaded files have similar" +
                    "return types and parameter types. The score is computed based on our own formula.";
    private static final String LAYER_2_INTRO =
            "Layer 2 is purposed for abstract syntax tree comparison. It compares ast of two uploaded files and " +
                    "analyzes it by GST algorithm.";

    private Stage window;
    private Scene mainScene, secondScene;
    private BorderPane borderPane_main, borderPane_second;
    private GridPane grid_Pane;
    private VBox vBox_main_center;
    private HBox hBox_main_top, hBox_main_center_file1, hBox_main_center_file2, hBox_table_view;
    private Button btnHelp, btnCheck, btnFile1, btnFile2;
    private Button btnReturn, btnDownload;
    private Label lblFile1, lblFile2, lblAlert, lblTitle, lblScore, lblLayer2Intro;
    private FileChooser fileChooser;
    private TextField txtFileName1, txtFileName2, txtScore;
    private TextArea txtLayer2Intro;
    private File dirPath;
    private File[] files = new File[2];

    private TableView<CodeLine> table1, table2;
    private TableColumn<CodeLine, Integer> lineNumberCol1;
    private TableColumn<CodeLine, String> codeCol1;
    private TableColumn<CodeLine, Integer> lineNumberCol2;
    private TableColumn<CodeLine, String> codeCol2;

    private TabPane tabPane;
    private LayerTab tabLayer0;
    private LayerTab tabLayer1;
    private Tab tabLayer2;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Stage
        window = primaryStage;
        window.setTitle("Plagiarism Detector for Java code");
        window.setFullScreen(true);
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();
//
//        window.setX(bounds.getMinX());
//        window.setY(bounds.getMinY());
//        window.setWidth(bounds.getWidth());
//        window.setHeight(bounds.getHeight());
        window.setResizable(true);

        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        window.show();

        /******************************* Main Scene ************************************/

        // TextField
        txtFileName1 = new TextField();
        txtFileName2 = new TextField();

        // Label
        lblFile1 = new Label("File 1: ");
        lblFile1.getStyleClass().add("lblFile11");
        lblFile1.setTextAlignment(TextAlignment.CENTER);
        lblFile2 = new Label("File 2: ");
        lblFile2.getStyleClass().add("lblFile12");
        lblFile2.setTextAlignment(TextAlignment.CENTER);

        lblAlert = new Label("Two files have the same name! Please check again!");
        lblAlert.setTextAlignment(TextAlignment.CENTER);
        lblAlert.setTextFill(Color.RED);
        lblAlert.setVisible(false);

        lblTitle = new Label("Plagiarism Detector for Java Language Code");
        lblTitle.getStyleClass().add("lblTitle1");
        lblTitle.translateXProperty().set(-200);
        lblTitle.translateYProperty().set(150);

        // FileChooser
        fileChooser = new FileChooser();

        // Buttons
        btnHelp = new Button("Ask Help?");
        btnHelp.getStyleClass().add("btnHelp1");
        btnHelp.translateYProperty().set(40);
        btnHelp.translateXProperty().set(-40);
        btnCheck = new Button("Check Plagiarism");
        btnCheck.getStyleClass().add("btnCheck1");
        btnFile1 = new Button("Upload");
        btnFile1.getStyleClass().add("btnFile1");
        btnFile2 = new Button("Upload");
        btnFile2.getStyleClass().add("btnFile2");
        DropShadow shadow = new DropShadow();

        /******** Shadow Effect *********/
        btnHelp.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnHelp.setEffect(shadow));
        btnHelp.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnHelp.setEffect(null));

        btnCheck.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnCheck.setEffect(shadow));
        btnCheck.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnCheck.setEffect(null));

        btnFile1.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnFile1.setEffect(shadow));
        btnFile1.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnFile1.setEffect(null));

        btnFile2.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnFile2.setEffect(shadow));
        btnFile2.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnFile2.setEffect(null));

        /********** Action *************/
        btnHelp.setOnAction(event -> {
            HelpPage.display("Help");
        });

        btnCheck.setOnAction(event -> {
            List<Integer> lineNumbers1 = new ArrayList<>();
            List<String> codes1 = new ArrayList<>();
            List<Integer> lineNumbers2 = new ArrayList<>();
            List<String> codes2 = new ArrayList<>();
            if(files[0] != null & files[1] != null) {
                String random = generateRandomString();
                int counter = 1;
                for(File file : files) {
                    dirPath = new File("C:\\uploads\\" + random);
                    dirPath.mkdir();
                    String fileName = "File_" + counter + "_" + file.getName();
                    counter++;
                    File saveFile = new File(dirPath, fileName);
                    try {
                        FileWriter fileWriter = new FileWriter(saveFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                readUploadFile(files[0].getAbsolutePath(), lineNumbers1, codes1);
                readUploadFile(files[1].getAbsolutePath(), lineNumbers2, codes2);
                table1.setItems(setUpData(lineNumbers1, codes1));
                table2.setItems(setUpData(lineNumbers2, codes2));
                LoadingBox.display("Loading...");


                /*********** render UI *******************/
                
                
            	try {
					PlagiarismDetector plagiarismDetector = new PlagiarismDetector(files[0], files[1]);
					Report[] reports=plagiarismDetector.generateFinalReport();
					
	                // Layer 0
	                tabLayer0.getTxtLayerIntro().setText(LAYER_0_INTRO);
	                tabLayer0.getTxtScore().setText(String.valueOf(reports[0].getScore()));
	                tabLayer0.getTxtMessage().setText(reports[0].getMessage());

	                // Layer 1
	                tabLayer1.getTxtLayerIntro().setText(LAYER_1_INTRO);
	                tabLayer1.getTxtScore().setText(String.valueOf(reports[1].getScore()));
	                tabLayer1.getTxtMessage().setText(reports[1].getMessage());

	                // Layer 2
	                Map<Integer, List<Integer>> map = getPlagLineNumbers(reports[2].getMessage());
	                List<Integer> lines1 = map.get(1);
	                List<Integer> lines2 = map.get(2);

	                plagiarismShow(table1, lines1);
	                plagiarismShow(table2, lines2);
	                txtScore.setText(String.valueOf(reports[2].getScore()));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                secondScene.getStylesheets().add("plag.css");
                window.setScene(secondScene);
            } else {
                AlertBox.display("ERROR", "Please upload two files!");
            }
        });

        btnFile1.setOnAction(event -> {
            configureFileChooser();
            File selectedFile1 = fileChooser.showOpenDialog(window);
            if (selectedFile1 != null) {
                files[0] = selectedFile1;
                txtFileName1.setText(files[0].getName());
                changeAlertVisibility();
            }
            // OK Label
        });

        btnFile2.setOnAction(event -> {
            configureFileChooser();
            File selectedFile2 = fileChooser.showOpenDialog(window);
            if (selectedFile2 != null) {
                files[1] = selectedFile2;
                txtFileName2.setText(files[1].getName());
                changeAlertVisibility();
            }
            // OK Label
        });



        // Layout
        hBox_main_top = new HBox(20);
        hBox_main_top.getChildren().addAll(lblTitle,btnHelp);
        hBox_main_top.setAlignment(Pos.CENTER);

        hBox_main_center_file1 = new HBox(20);
        hBox_main_center_file1.getChildren().addAll(lblFile1, txtFileName1, btnFile1);
        hBox_main_center_file1.setPadding(new Insets(20));
        hBox_main_center_file1.setAlignment(Pos.CENTER);

        hBox_main_center_file2 = new HBox(20);
        hBox_main_center_file2.getChildren().addAll(lblFile2, txtFileName2, btnFile2);
        hBox_main_center_file2.setPadding(new Insets(20));
        hBox_main_center_file2.setAlignment(Pos.CENTER);

        vBox_main_center = new VBox(20);
        vBox_main_center.getChildren().addAll(hBox_main_center_file1, hBox_main_center_file2, btnCheck, lblAlert);
        vBox_main_center.setPadding(new Insets(20));
        vBox_main_center.setAlignment(Pos.CENTER);

        borderPane_main = new BorderPane();
        borderPane_main.setTop(hBox_main_top);
        borderPane_main.setCenter(vBox_main_center);

        // Scene
        mainScene = new Scene(borderPane_main, 1300,650);
        mainScene.getStylesheets().add("plag.css");
        window.setScene(mainScene);


        /********************* Second Scene ****************************************/

        // Two tableViews

        table1 = new TableView<>();
        table2 = new TableView<>();
        table1.setEditable(false);
        table2.setEditable(false);
        table1.setPrefWidth(500);
        table2.setPrefWidth(500);

        lineNumberCol1 = new TableColumn<>("Lines");
        lineNumberCol2 = new TableColumn<>("Lines");
        codeCol1 = new TableColumn<>("Codes");
        codeCol2 = new TableColumn<>("Codes");

        lineNumberCol1.setCellValueFactory(new PropertyValueFactory<CodeLine, Integer>("lineNumber"));
        codeCol1.setCellValueFactory(new PropertyValueFactory<CodeLine, String>("code"));
        lineNumberCol2.setCellValueFactory(new PropertyValueFactory<CodeLine, Integer>("lineNumber"));
        codeCol2.setCellValueFactory(new PropertyValueFactory<CodeLine, String>("code"));

        lineNumberCol1.setPrefWidth(75);

        codeCol1.setPrefWidth(425);
        lineNumberCol2.setPrefWidth(75);
        codeCol2.setPrefWidth(425);
        lineNumberCol1.setSortable(false);
        codeCol1.setSortable(false);
        lineNumberCol2.setSortable(false);
        codeCol2.setSortable(false);

        table1.getColumns().addAll(lineNumberCol1, codeCol1);
        table2.getColumns().addAll(lineNumberCol2, codeCol2);


        // Buttons

        btnReturn = new Button("Return");

        btnReturn.getStyleClass().addAll("btnReturn1");
        btnDownload = new Button("Download");

        btnReturn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnHelp.setEffect(shadow));
        btnReturn.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnHelp.setEffect(null));

        btnDownload.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnHelp.setEffect(shadow));
        btnDownload.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnHelp.setEffect(null));

        btnReturn.setOnAction(event -> {
            txtFileName1.setText("");
            txtFileName2.setText("");
            files = new File[2];
            window.setScene(mainScene);
        });


        // Layouts

        tabPane = new TabPane();
        tabLayer0 = new LayerTab("Layer 0");
        tabLayer1 = new LayerTab("Layer 1");
        tabLayer2 = new Tab("Layer 2");

        hBox_table_view = new HBox(30);
        hBox_table_view.getChildren().addAll(table1, table2);
        hBox_table_view.setAlignment(Pos.CENTER);
        hBox_table_view.setPadding(new Insets(20));

        borderPane_second = new BorderPane();
        borderPane_second.setCenter(hBox_table_view);
        hBox_table_view = new HBox(40);
        hBox_table_view.getChildren().addAll(table1, table2);
        hBox_table_view.setAlignment(Pos.CENTER_LEFT);
        hBox_table_view.setPadding(new Insets(40));

        borderPane_second = new BorderPane();
        borderPane_second.setRight(hBox_table_view);

        lblScore = new Label("Score: ");
        txtScore = new TextField();
        txtScore.setEditable(false);
        lblLayer2Intro = new Label("Introduction: ");
        txtLayer2Intro = new TextArea();
        txtLayer2Intro.setEditable(false);
        txtLayer2Intro.setPrefWidth(75);
        txtLayer2Intro.setWrapText(true);
        txtLayer2Intro.setText(LAYER_2_INTRO);

        grid_Pane = new GridPane();
        grid_Pane.addRow(0, lblScore, txtScore);
        grid_Pane.addRow(1, lblLayer2Intro, txtLayer2Intro);
        grid_Pane.addRow(2, btnReturn);
        borderPane_second.setCenter(grid_Pane);

        tabLayer2.setContent(borderPane_second);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(tabLayer0, tabLayer1, tabLayer2);

        secondScene = new Scene(tabPane, 1300,650);
    }


    /****************** helper methods *****************/

    // Pop up confirm box to ensure the user wants to close the window
    private void closeProgram() {
        boolean toClose = ConfirmBox.display("Exit", "Do you want to exit now?");
        if(toClose) {
            window.close();
        }
    }


    // limit start point to search files and the postfix of files
    private void configureFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("View Java Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Java Files", "*.java")
        );
    }

    // Generate random string
    private String generateRandomString() {
        String aToz0To9 = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int randIndex = random.nextInt(aToz0To9.length());
            stringBuilder.append(aToz0To9.charAt(randIndex));
        }
        return stringBuilder.toString();
    }

    // change the visibility of lblAlert according to two upload file names
    private void changeAlertVisibility() {
        if(txtFileName1.getText().equals(txtFileName2.getText()))
            lblAlert.setVisible(true);
        else
            lblAlert.setVisible(false);
    }

    // read two uploaded Java files
    private void readUploadFile(String filePath, List<Integer> lineNumbers, List<String> codes) {
        int count = 0;
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String temp = "temp";
        try {
            while (temp != null) {
                temp = br.readLine();
                if (temp == null)
                    break;
                lineNumbers.add(++count);
                codes.add(temp);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Initialize the data inside TableView controls
    public static ObservableList<CodeLine> setUpData(List<Integer> lineNumbers, List<String> codes){
        if(lineNumbers == null || codes == null || lineNumbers.size() != codes.size()) {
            return null;
        }
        ArrayList<CodeLine> codeLines = new ArrayList<>();
        for(int i = 0; i < lineNumbers.size(); i++) {
            System.out.println();
            codeLines.add(new CodeLine(lineNumbers.get(i), codes.get(i)));
        }

        ObservableList<CodeLine> data = FXCollections.observableArrayList(codeLines);
        return data;
    }

    // Render the tableView to show code lines which are plagiarism
    private void plagiarismShow(TableView table, List<Integer> plagLineNums){

        table.setRowFactory(t -> {
            return new TableRow<CodeLine>() {
                @Override
                protected void updateItem(CodeLine item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item == null | empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        if(plagLineNums.contains(item.getLineNumber())) {

                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });
    }


    // Parse the report message got from back end
    public static Map<Integer, List<Integer>> getPlagLineNumbers(String reportMessage) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> plagLineNumbers;
        int mapKey = 1;

        String[] plagLineNumbersList = reportMessage.split("\n");

        for(String numberList: plagLineNumbersList) {
            System.out.println(numberList);
            plagLineNumbers = new ArrayList<>();
            int length = numberList.length();
            numberList = numberList.substring(1, length - 1);
            System.out.println(numberList);
            if(numberList.length() > 0) {
                String[] numbers = numberList.split(", ");
                for(String number: numbers) {
                    int num = Integer.parseInt(number.trim());
                    plagLineNumbers.add(num);
                }
            }
            map.put(mapKey, plagLineNumbers);
            mapKey++;
        }
        return map;
    }



    /*********** main method ***********/
    public static void main(String[] args) {
        launch(args);
    }
}

