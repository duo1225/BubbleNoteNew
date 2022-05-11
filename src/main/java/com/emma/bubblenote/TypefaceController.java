package com.emma.bubblenote;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TypefaceController {
    //Font
    @FXML
    private ComboBox<String> Cb1;

    @FXML
    private ComboBox<String> Cb2;
    //Size
    @FXML
    private ComboBox<String> Cb3;
    @FXML
    private Button SureBtn;
    @FXML
    private TextArea ta;

    public Font Txtfont=Font.font(12);
    private Font font=Font.font(12);


    String type="Arial";
    String special="Normal";
    int size=12;

    @FXML
    void initialize() {

        List<String> getFontName = Font.getFontNames();
        Cb1.getItems().addAll(getFontName);

        Cb2.getItems().addAll("Normal","Bold","Italic","BoldItalic");

        for(int i=2;i<=32;i=i+2)
        {
            Cb3.getItems().add(i+"");
        }

        Cb1.setOnAction(e->{
            type = Cb1.getValue();
            setFont();
        });

        Cb2.setOnAction(e->{
            special = Cb2.getValue();
            setFont();
        });

        Cb3.setOnAction(e->{
            size = Integer.parseInt(Cb3.getValue());
            setFont();
        });
    }

    //Style
    public void setFont() {
        font = Font.font(type, size);
        if(special.equals("Normal"))
            font=Font.font(type, FontWeight.NORMAL, FontPosture.REGULAR,size);
        else if(special.equals("Bold"))
            font=Font.font(type, FontWeight.BOLD, FontPosture.REGULAR,size);
        else if(special.equals("Italic"))
            font=Font.font(type, FontWeight.NORMAL, FontPosture.ITALIC,size);
        else if(special.equals("BoldItalic"))
            font=Font.font(type, FontWeight.BOLD, FontPosture.ITALIC,size);
        ta.setFont(font);
    }


    @FXML
    void onSureBtn(ActionEvent event) {
        Txtfont=font;
        Scene scene = SureBtn.getScene();
        //Record the selected font to user data
        scene.setUserData(Txtfont);
        // After clicking OK, close the window
        Stage stage=(Stage)SureBtn.getScene().getWindow();
        stage.close();
    }

}



