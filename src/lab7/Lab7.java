/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ahmedshokry
 */
public class Lab7 extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar m_bar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        MenuItem New = new MenuItem("New");
        MenuItem Open = new MenuItem("Open...");
        MenuItem Save = new MenuItem("Save");
        MenuItem Exit = new MenuItem("Exit");

        MenuItem Undo = new MenuItem("Undo");
        MenuItem Cut = new MenuItem("Cut");
        MenuItem Copy = new MenuItem("Copy");
        MenuItem Paste = new MenuItem("Paste");
        MenuItem Delete = new MenuItem("Delete");
        MenuItem SelectAll = new MenuItem("Select All");

        MenuItem about = new MenuItem("About");

        file.getItems().addAll(New, Open, Save,Exit);
        edit.getItems().addAll(Undo, Cut, Copy, Paste, Delete, SelectAll);
        help.getItems().addAll(about);
        m_bar.getMenus().addAll(file, edit, help);

        TextArea tArea = new TextArea();
        tArea.setMaxWidth(600);
        tArea.setMaxHeight(400);
        
        New.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            tArea.clear();
        });
        Open.setOnAction(ActionEvent -> {
            FileChooser fc = new FileChooser();
            File openFile = fc.showOpenDialog(primaryStage);
            if(openFile != null){
                FileInputStream finput = null;
                int size;
                byte[] by;
                try{
                    finput = new FileInputStream(openFile);
                    //available() method returns number of remaining bytes that can be read from this input stream
                    size = finput.available();
                    by = new byte[size];
                    finput.read(by);
                    tArea.setText(new String(by));
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }finally{
                    try{
                        finput.close();
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        Save.setOnAction(ActionEvent ->{
            FileChooser fc = new FileChooser();
            File save = fc.showSaveDialog(null);
            try {
                Dialog<String> dialog = new Dialog<String>();
                dialog.setTitle("Save path");
                ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                dialog.setContentText(save.toString());
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        });
        Exit.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            Platform.exit();
        });
        Undo.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            tArea.undo();
        });
        Cut.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            tArea.cut();
        });        
        Copy.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            tArea.copy();
        });
        Delete.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
             IndexRange selection=tArea.getSelection();
             int start=selection.getStart();
             int end=selection.getEnd();
             tArea.deleteText(start,end);
        });
        SelectAll.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            tArea.selectAll();
        });
        about.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            Dialog<String> dialog2 = new Dialog<String>();
            dialog2.setTitle("Dialog");
            ButtonType type2 = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog2.setContentText("Ahmed Shokry Open Source app development");
            dialog2.getDialogPane().getButtonTypes().add(type2);
            dialog2.showAndWait();
        });

        GridPane gp = new GridPane();
        gp.add(m_bar, 0, 0);
        gp.add(tArea, 0, 1);
        
        BorderPane pane = new BorderPane();
        pane.getChildren().add(gp);
        pane.setTop(m_bar);
        pane.setCenter(tArea);

        Scene s = new Scene(pane);
        primaryStage.setScene(s);
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
