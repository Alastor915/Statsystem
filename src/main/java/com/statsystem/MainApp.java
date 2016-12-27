package com.statsystem;

import com.statsystem.controller.MainController;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.DBServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
         launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        File file = new File("h2db.mv.db");
        DBService dbService;
        if (file.exists()) {
            dbService = new DBServiceImpl();
        } else {
            dbService = new DBServiceImpl(false,"create");
        }
        String fxmlFile = "/fxml/main_window.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController controller = loader.getController();
        stage.setTitle("Система обработки данных");
        stage.setScene(new Scene(root));
        root.getStylesheets().addAll(getClass().getResource("/css/style.css").toExternalForm());
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