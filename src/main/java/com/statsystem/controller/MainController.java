package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.DBServiceImpl;
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

    //@FXML private InterpolationController interpolationController;
    @FXML private Tab sampleTab;
    @FXML private Tab addSample;
    @FXML private MenuItem createProject;
    @FXML private MenuItem loadProject;
    @FXML private TabPane samplesTab;
    private List<SampleTabController> tabControllers = Collections.EMPTY_LIST;
    private Stage m_stage;
    private DBService dbService;

    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public void initialize(URL location, ResourceBundle resources) {
        tabControllers = new ArrayList<>();
       /* Sample sample = hardcode();
        interpolationController.setMainController(this);
        interpolationController.setSample(sample);
        interpolationController.start();
        sampleTab.setText(sample.getName());*/
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
                createProjectController.setMainController(this);
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
                loadProjectController.setDBService(dbService);
                stage.showAndWait();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void loadXLSXSamples(List<Sample> samples) {
        if(samples.isEmpty())
            return;
        samplesTab.getTabs().remove(addSample);
        for(Sample sample : samples) {
            try {
                String fxmlFile = "/fxml/samples_tabs.fxml";
                FXMLLoader loader = new FXMLLoader();
                Tab tab = loader.load(getClass().getResource(fxmlFile).openStream());
                tab.setText(sample.getName());
                SampleTabController sampleTabController = loader.getController();
                sampleTabController.setMainController(this);
                sampleTabController.setSample(sample);
                sampleTabController.start();
                samplesTab.getTabs().addAll(tab);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        samplesTab.getTabs().addAll(addSample);
    }
}
