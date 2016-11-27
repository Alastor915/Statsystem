package com.statsystem.controller;

import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Нестеренко Д. Ю. on 06.11.2016.
 *
 */
public class MainController implements Initializable {

    @FXML private InterpolationController interpolationController;
    @FXML private Tab sampleTab;
    @FXML private MenuItem createProject;
    @FXML private MenuItem loadProject;
    @FXML private Tab newCalc;
    @FXML private TabPane calcTabPane;
    private Stage m_stage;

    public void initialize(URL location, ResourceBundle resources) {
        Sample sample = hardcode();
        interpolationController.setMainController(this);
        interpolationController.setSample(sample);
        interpolationController.start();
        sampleTab.setText(sample.getName());
        createProject.setOnAction(e->{
            try {
                String fxmlFile = "/fxml/create_project_dialog.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
                CreateProjectController createProjectController = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(m_stage);
                stage.setTitle("Система обработки данных");
                stage.setScene(new Scene(root));
                createProjectController.setM_stage(stage);
                stage.showAndWait();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        loadProject.setOnAction(e->{
            try {
                String fxmlFile = "/fxml/load_project_dialog.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
                LoadProjectController loadProjectController = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(m_stage);
                stage.setTitle("Система обработки данных");
                stage.setScene(new Scene(root));
                loadProjectController.setM_stage(stage);
                stage.showAndWait();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        newCalc.setOnSelectionChanged(e->{
            try {
                if (newCalc.isSelected()) {
                    String fxmlFile = "/fxml/choice_type_calc_dialog.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
                    NewCalcController newCalcController = loader.getController();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(m_stage);
                    stage.setTitle("Система обработки данных");
                    stage.setScene(new Scene(root));
                    newCalcController.setM_stage(stage);
                    newCalcController.setMainController(this);
                    stage.showAndWait();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }

    public static Sample hardcode() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Sample sample = new Sample("Выборка, полученная из xlsx");
        try {
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:04:02").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:14:02").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:24:04").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:34:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:44:05").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 0:53:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:04:04").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:14:05").getTime()).doubleValue(),2.759510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:24:05").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:34:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:44:02").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 1:54:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:03:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:14:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:24:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:34:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:44:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 2:54:05").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:04:03").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:14:02").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:24:01").getTime()).doubleValue(),2.769080235));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:34:05").getTime()).doubleValue(),2.769080235));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:44:01").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 3:54:05").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:04:03").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:14:01").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:24:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:34:01").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:43:04").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 4:54:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:04:05").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:14:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:24:05").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:34:02").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:44:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 5:54:16").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:04:02").getTime()).doubleValue(),2.759295499));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:14:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:24:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:34:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:44:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 6:53:03").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:04:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:14:03").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:23:02").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:34:01").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:44:02").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 7:54:05").getTime()).doubleValue(),2.749510763));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:04:03").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:14:06").getTime()).doubleValue(),2.739726027));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:24:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:34:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:44:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 8:54:07").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:04:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:14:02").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:24:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:34:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:44:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 9:54:04").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:04:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:14:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:24:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:34:19").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:44:05").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 10:54:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:04:04").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:14:11").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:24:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:34:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:44:05").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 11:54:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:04:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:14:03").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:24:04").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:34:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:44:01").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 12:54:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:04:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:14:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:24:06").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:34:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:44:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 13:54:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:04:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:14:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:24:02").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:34:07").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:44:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 14:54:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:04:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:14:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:24:06").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:34:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:44:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 15:54:07").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:04:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:14:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:24:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:34:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:44:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 16:54:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:04:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:14:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:24:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:34:04").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:44:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 17:54:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:04:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:14:06").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:24:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:34:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:44:04").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 18:54:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:04:06").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:14:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:24:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:34:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:44:04").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 19:54:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:04:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:14:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:24:06").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:34:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:44:05").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 20:54:01").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:04:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:14:02").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:24:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:34:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:44:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 21:54:04").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:04:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:14:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:24:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:34:02").getTime()).doubleValue(),2.700587084));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:44:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 22:54:01").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:04:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:14:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:24:03").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:34:03").getTime()).doubleValue(),2.720156556));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:44:02").getTime()).doubleValue(),2.71037182));
            sample.addUnit(new Unit(new Long(format.parse("08.04.2013 23:54:03").getTime()).doubleValue(),2.720156556));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sample;
    }
    public TabPane getCalcTabPane(){
        return calcTabPane;
    }

    public Tab getCalcNew(){
        return newCalc;
    }
}
