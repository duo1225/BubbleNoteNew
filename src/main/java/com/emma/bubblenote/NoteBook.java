package com.emma.bubblenote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class NoteBook extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage myNoteStage) throws Exception {
        FXMLLoader noteBook = new FXMLLoader();
        // todo: change
        //AnchorPane root = noteBook.load(new FileInputStream(new File("/Users/zhangduo/Downloads/BubbleNote/src/main/resources/com/emma/bubblenote/NoteBook.fxml")));
        Parent root = FXMLLoader.load(getClass().getResource("NoteBook.fxml"));
        Scene scene = new Scene(root);

        myNoteStage.setScene(scene);
        myNoteStage.setTitle("Welcome to Bubble Note");
        myNoteStage.setHeight(800);
        myNoteStage.setWidth(800);
        myNoteStage.show();
    }
}
