package com.statsystem;

import com.statsystem.controller.MainController;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.DBServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
         launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DBService dbService = new DBServiceImpl();
        String fxmlFile = "/fxml/main_window.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController controller = (MainController)loader.getController();
        stage.setTitle("Система обработки данных");
        stage.setScene(new Scene(root));
        controller.setM_stage(stage);
        controller.setDbService(dbService);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}