package com.emma.bubblenote;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Label l_name = new Label("Nom : ");
        Label l_pwd = new Label("Password : ");
        TextField t_name = new TextField();
        PasswordField p_pwd = new PasswordField();

        Button login = new Button("Connecter");
        Button clear = new Button("Clear");

        //设置用户名，密码
        t_name.setUserData("duo");
        p_pwd.setUserData("123");

        GridPane gr = new GridPane();
        gr.setStyle("-fx-background-color: #a087db");

        gr.add(l_name,0,0);
        gr.add(t_name,1,0);

        gr.add(l_pwd,0,1);
        gr.add(p_pwd,1,1);

        gr.add(login,1,2);
        gr.add(clear,0,2);
        //设置水平间距
        gr.setHgap(5);
        //设置垂直间距
        gr.setVgap(15);
        gr.setMargin(login,new Insets(0,0,0,120));

        // 全部居中
        gr.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gr);
        stage.setScene(scene);
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setTitle("Bubble Note");
        //不允许拉伸
        stage.setResizable(false);
        stage.show();

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                t_name.setText("");
                p_pwd.setText("");
            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = t_name.getText();
                String pwd = p_pwd.getText();

                if(name.equals("duo") && pwd.equals("123")){
                    System.out.println("Login Success");
                    //打开新窗口
                    //NoteWindow noteWindow = new NoteWindow();
                    notePage();
                    //需要关闭之前的窗口
                    //stage.close();
                    stage.hide();
                }else{
                    System.out.println("Failed to login");
                    //errorPage();
                }

            }
        });
    }

    public void notePage(){
        Group group = new Group();
        group.setStyle("-fx-background-color: #a087db");
        Scene scene = new Scene(group);

        Stage myStage = new Stage();
        myStage.setScene(scene);
        myStage.setTitle("Welcome to Bubble Note");
        myStage.setHeight(500);
        myStage.setHeight(500);
        myStage.show();
        System.out.println("notePage");
    }

    public void errorPage(){
        Text msg = new Text("Nom ou mot de passe incorrect !");

        Group groupError = new Group();
        groupError.setStyle("-fx-background-color: #a087db");
        groupError.getChildren().add(msg);


        Scene sceneError = new Scene(groupError);

        Stage myStageError = new Stage();
        myStageError.setScene(sceneError);
        myStageError.setTitle("Error");
        myStageError.setHeight(500);
        myStageError.setHeight(500);
        myStageError.show();
    }
}


