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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    //connecter database
    DatabaseConnection jdbcUtils = new DatabaseConnection();
    //Page Inscription
    TextField it_name;
    TextField it_email;
    PasswordField ip_pwd;

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
        Button inscrire = new Button("Inscription");

        //nom and pwd par default
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
        gr.add(inscrire,1,3);
        //设置水平间距
        gr.setHgap(5);
        //设置垂直间距
        gr.setVgap(15);
        gr.setMargin(login,new Insets(0,0,0,120));
        gr.setMargin(inscrire,new Insets(0,0,0,120));

        // all center
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
                   //Open Editeur
                    //notePage();
                    NoteBook noteBook = new NoteBook();
                    // hide page de connect
                    stage.hide();
                }else if(user_exist(name)){
                    NoteBook noteBook2 = new NoteBook();
                    stage.hide();
                }
                else{
                    System.out.println("Failed to login");
                    errorPage();
                }

            }
        });

        inscrire.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                inscrirePage();
                stage.hide();
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

        BorderPane groupError = new BorderPane();
        groupError.setStyle("-fx-background-color: #a087db");
        // besoin test
        groupError.setCenter(msg);


        Scene sceneError = new Scene(groupError);

        Stage myStageError = new Stage();
        myStageError.setScene(sceneError);
        myStageError.setTitle("Error");
        myStageError.setHeight(300);
        myStageError.setHeight(300);
        myStageError.show();
    }

    public void  inscrirePage(){
        Stage inscrirePage = new Stage();

        Label i_name = new Label("Nom : ");
        Label i_email = new Label("Email : ");
        Label i_pwd = new Label("Password : ");
        it_name = new TextField();
        it_email = new TextField();
        ip_pwd = new PasswordField();

        Button clear = new Button("Clear");
        Button inscrire = new Button("Inscrire");

        GridPane gr = new GridPane();
        gr.setStyle("-fx-background-color: #a087db");

        gr.add(i_name,0,0);
        gr.add(it_name,1,0);

        gr.add(i_email,0,1);
        gr.add(it_email,1,1);

        gr.add(i_pwd,0,2);
        gr.add(ip_pwd,1,2);

        gr.add(inscrire,1,3);
        gr.add(clear,0,3);
        //horizontal
        gr.setHgap(5);
        //Vertical
        gr.setVgap(15);
        gr.setMargin(inscrire,new Insets(0,0,0,120));

        // All center
        gr.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gr);
        inscrirePage.setScene(scene);
        inscrirePage.setWidth(300);
        inscrirePage.setHeight(300);
        inscrirePage.setTitle("Bubble Note - Inscription");
        //No stretching allowed
        inscrirePage.setResizable(false);
        inscrirePage.show();

        inscrire.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Success inscrire!");
                String ins_name = it_name.getText();
                String ins_email = it_email.getText();
                String ins_pwd = ip_pwd.getText();

                if(user_exist(ins_name)){
                    System.out.println("Utilisateur déja exixt!");
                    HBox hBox = new HBox();
                    Label label = new Label("Utilisateur déja exixt!");

                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(10);
                    hBox.getChildren().add(label);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(hBox,300,200));
                    stage1.setTitle("ERROR");
                    stage1.show();
                }
                //nom not exist and no problem
                else
                {
                    try {
                        Connection conn=jdbcUtils.getConnection();
                        String sql = "insert into user (nom,password,email) values (?,?,?)";
                        PreparedStatement ps=conn.prepareStatement(sql);
                        ps.setString(1,ins_name);
                        ps.setString(2,ins_pwd);
                        ps.setString(3,ins_email);
                        ps.executeUpdate();
                        //free resources
                        jdbcUtils.close(conn,ps);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    HBox hBox = new HBox();
                    Label label = new Label("Success Inscription !");

                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(10);
                    hBox.getChildren().add(label);
                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(hBox,300,200));
                    stage1.show();
                    //todo: changer to notePage
                    //todo : inscrire pas réussir
                    NoteBook insNoteBook = new NoteBook();
                }

            }
        });
    }

    //判断是否已存在用户
    public boolean user_exist(String nom){

        String sql = "select count(*) from user where nom = '"+nom+"'";
        try {
            Connection conn=jdbcUtils.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getInt(1)==1){
                jdbcUtils.close(conn,ps,rs);
                return true;
            }
            jdbcUtils.close(conn,ps,rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
    //verfier nom avec password
    public boolean user_right(String nom, String password){
        String sql = "select count(*) from user where nom = ? and password = ?";
        try {
            Connection conn=jdbcUtils.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,nom);
            ps.setString(2,password);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getInt(1)==1){
                jdbcUtils.close(conn,ps,rs);
                return true;
            }
            jdbcUtils.close(conn,ps,rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Nom ou password not correct!");
        HBox hBox = new HBox();
        Label label = new Label("Nom or password not correct");
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label);
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(hBox,300,200));
        stage1.setTitle("ERROR");
        stage1.show();
        return false;
    }

}


