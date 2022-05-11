package com.emma.bubblenote;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NoteBookController {

    @FXML
    private MenuItem SaveMenu;
    @FXML
    private MenuItem FindMenu;
    @FXML
    private CheckMenuItem WrapMenu;
    @FXML
    private AnchorPane layoutPane;
    @FXML
    private MenuItem ReplaceMenu;
    @FXML
    private CheckMenuItem StateMenu;
    @FXML
    private MenuItem OpenMenu;
    @FXML
    private MenuItem TypefaceMenu;
    @FXML
    private MenuItem NewMenu;
    @FXML
    private TextArea ta;
    @FXML
    private Label label;
    @FXML
    private MenuItem Redo;
    @FXML
    private MenuItem Undo;

    private File result;

    int startIndex = 0;
    int position = 0;

    // Grayscale control
    public void initialize() {
        // The find and replace function is not available in the initial state
        FindMenu.setDisable(true);
        ReplaceMenu.setDisable(true);
        //Undo and redo are not available in the initial state
        Redo.setDisable(true);
        Undo.setDisable(true);

        //Status bar not visible
        label.setVisible(false);

        ta.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                position = ta.getCaretPosition();
                label.setText("No." + position + "characters");
            }
        });

        ta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (ta.getLength() > 0) {
                    FindMenu.setDisable(false);
                    ReplaceMenu.setDisable(false);
                }
                //Otherwise find and replace is disabled
                else {
                    FindMenu.setDisable(true);
                    ReplaceMenu.setDisable(true);
                }
                Redo.setDisable(false);
                Undo.setDisable(false);
                // cursor position
                position = ta.getCaretPosition();
                label.setText("No. " + position + "character");
            }
        });
    }

    // save before change
    void saveadvance() {
        if (result != null && ta.getLength() > 0) {

            FileTools.writeFile(result, ta.getText());
        } else if (result == null && ta.getLength() > 0) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Save current content.");
            result = fileChooser.showSaveDialog(null);
            if (result != null) {
                FileTools.writeFile(result, ta.getText());
            }
        }
    }

    // Create
    @FXML
    void onNewMenu(ActionEvent event) {

        // save before create
        saveadvance();

        ta.clear();
        result = null;
    }

    //Open function
    @FXML
    void onOpenMenu(ActionEvent event) {

        // save before open
        saveadvance();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        result = fileChooser.showOpenDialog(null);
        if (result != null) {
            ta.setText(FileTools.readFile(result));
        }
    }

    // Save function
    @FXML
    void onSaveMenu(ActionEvent event) throws IOException {
        if (result != null)//if exsit path of save
        {
            FileTools.writeFile(result, ta.getText());
        } else
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            result = fileChooser.showSaveDialog(null);
            if (result != null) {
                FileTools.writeFile(result, ta.getText());
            }
        }
    }

    //revoke--cancel
    @FXML
    void onUndoMenu(ActionEvent event) {
        ta.undo();
        Undo.setDisable(true);
    }

    //redo
    @FXML
    void onRedoMenu(ActionEvent event) {
        ta.redo();
        Redo.setDisable(true);
    }

    // search
    @FXML
    void onFindMenu(ActionEvent event) throws IOException {
        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 20, 5));
        h1.setSpacing(5);
        Label lable1 = new Label("Search info(N):");
        TextField tf1 = new TextField();
        h1.getChildren().addAll(lable1, tf1);

        VBox v1 = new VBox();
        v1.setPadding(new Insets(20, 5, 20, 10));
        Button btn1 = new Button("Search next(F)");
        v1.getChildren().add(btn1);

        HBox findRootNode = new HBox();
        findRootNode.getChildren().addAll(h1, v1);

        Stage findStage = new Stage();
        Scene scene1 = new Scene(findRootNode, 450, 90);
        findStage.setTitle("Search");
        findStage.setScene(scene1);
        findStage.setResizable(false); // fixed window size
        findStage.show();

        btn1.setOnAction((ActionEvent e) -> {
            String textString = ta.getText();
            String tfString = tf1.getText(); //word for search
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    if (startIndex == -1) {// not found
                        Alert alert1 = new Alert(AlertType.WARNING);
                        alert1.titleProperty().set("Hint");
                        alert1.headerTextProperty().set("Can't find any related content!");
                        alert1.show();
                    }
                    startIndex = ta.getText().indexOf(tf1.getText(), startIndex);
                    if (startIndex >= 0 && startIndex < ta.getText().length()) {
                        ta.selectRange(startIndex, startIndex + tf1.getText().length());
                        startIndex += tf1.getText().length();
                    }
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(AlertType.WARNING);
                    alert1.titleProperty().set("Hint");
                    alert1.headerTextProperty().set("No related content found！");
                    alert1.show();
                }
            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.titleProperty().set("Error!");
                alert1.headerTextProperty().set("Input is empty");
                alert1.show();
            }
        });
    }

    // Replace
    @FXML
    void onReplaceMenu(ActionEvent event) throws IOException {
        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 10, 8));
        h1.setSpacing(5);
        Label label1 = new Label("Search Next(F)");
        TextField tf1 = new TextField();
        h1.getChildren().addAll(label1, tf1);

        HBox h2 = new HBox();
        h2.setPadding(new Insets(5, 5, 20, 8));
        h2.setSpacing(5);
        Label label2 = new Label("Replace content(N):");
        TextField tf2 = new TextField();
        h2.getChildren().addAll(label2, tf2);

        VBox v1 = new VBox();
        v1.getChildren().addAll(h1, h2);

        VBox v2 = new VBox();
        v2.setPadding(new Insets(21, 5, 20, 10));
        v2.setSpacing(13);
        Button btn1 = new Button("Search next");
        Button btn2 = new Button("Replace by");
        v2.getChildren().addAll(btn1, btn2);

        HBox replaceRootNode = new HBox();
        replaceRootNode.getChildren().addAll(v1, v2);

        Stage replaceStage = new Stage();
        Scene scene = new Scene(replaceRootNode, 430, 120);
        replaceStage.setTitle("Replace");
        replaceStage.setScene(scene);
        replaceStage.setResizable(false); // fixed size of window
        replaceStage.show();

        btn1.setOnAction((ActionEvent e) -> {
            String textString = ta.getText();
            String tfString = tf1.getText();

            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    if (startIndex == -1) {// not found
                        Alert alert1 = new Alert(AlertType.WARNING);
                        alert1.titleProperty().set("Hint");
                        alert1.headerTextProperty().set("Can't find any related content！");
                        alert1.show();
                    }
                    startIndex = ta.getText().indexOf(tf1.getText(), startIndex);
                    if (startIndex >= 0 && startIndex < ta.getText().length()) {
                        ta.selectRange(startIndex, startIndex + tf1.getText().length());
                        startIndex += tf1.getText().length();
                    }
                    btn2.setOnAction((ActionEvent e2) -> {
                        ta.replaceSelection(tf2.getText());
                    });
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(AlertType.WARNING);
                    alert1.titleProperty().set("Hint");
                    alert1.headerTextProperty().set("No related content found！");
                    alert1.show();
                }

            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.titleProperty().set("Error");
                alert1.headerTextProperty().set("Empty");
                alert1.show();
            }
        });
    }

    // 自动换行 word wrap
    @FXML
    void onWrapMenu(ActionEvent event) {
        if (WrapMenu.isSelected())
            ta.setWrapText(true);
        else
            ta.setWrapText(false);
    }

    // 字体 Style
    @FXML
    void onTypefaceMenu(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Typeface.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        // wait for the window to be closed before reacting to the next step
        stage.showAndWait();
        if (scene.getUserData() != null)// If the user has a style that sets the font, change the font in Notepad to this style
        {
            Font font = (Font) scene.getUserData();
            ta.setFont(font);
        }
    }

    // Status bar
    @FXML
    void onStateMenu(ActionEvent event) {
        if (StateMenu.isSelected())
            label.setVisible(true);
        else
            label.setVisible(false);
    }

}


