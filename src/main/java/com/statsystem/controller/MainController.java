package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static com.statsystem.utils.Message.showErrorMessage;

/**
 * Created by Нестеренко Д. Ю. on 06.11.2016.
 *
 */
public class MainController implements Initializable {

    //@FXML private InterpolationController interpolationController;
    @FXML private Tab welcome;
    @FXML private MenuItem createProject;
    @FXML private MenuItem loadProject;
    @FXML private Button createBut;
    @FXML private Button loadBut;
    @FXML private Button helpBut;
    @FXML private MenuItem help;
    @FXML private TabPane samplesTab;
    private List<SampleTabController> tabControllers = Collections.EMPTY_LIST;
    private Stage m_stage;
    private DBService dbService;
    private Project project;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TabPane getSamplesTab() {
        return samplesTab;
    }

    public Tab getWelcome(){
        return welcome;
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
                createProjectController.setDbService(dbService);
                stage.showAndWait();
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании диалогового окна", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
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
                loadProjectController.setMainController(this);
                loadProjectController.setDbService(dbService);
                stage.showAndWait();
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании диалогового окна", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }
        });

        createBut.setOnAction(e->{
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
                createProjectController.setDbService(dbService);
                stage.showAndWait();
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании диалогового окна", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }

        });

        loadBut.setOnAction(e->{
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
                loadProjectController.setMainController(this);
                loadProjectController.setDbService(dbService);
                stage.showAndWait();
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании диалогового окна", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }
        });

        help.setOnAction(e->{
            final WebView browser = new WebView();
            final WebEngine webEngine = browser.getEngine();
            URL url = getClass().getResource("/html/main.html");
            webEngine.load(url.toExternalForm());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(m_stage);
            stage.setTitle("Система обработки данных");
            stage.setScene(new Scene(browser));
            stage.setMinWidth(840);
            stage.setMinHeight(600);
            stage.showAndWait();

        });
        helpBut.setOnAction(e->{
            final WebView browser = new WebView();
            final WebEngine webEngine = browser.getEngine();
            URL url = getClass().getResource("/html/main.html");
            webEngine.load(url.toExternalForm());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(m_stage);
            stage.setTitle("Система обработки данных");
            stage.setScene(new Scene(browser));
            stage.setMinWidth(840);
            stage.setMinHeight(600);
            stage.showAndWait();

        });
    }

    public void loadXLSXSamples(List<Sample> samples) {
        if(samples.isEmpty())
            return;
        //samplesTab.getTabs().remove(addSample);
        samplesTab.getTabs().removeAll(samplesTab.getTabs());
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
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании новой вкладки", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }
        }
//        samplesTab.getTabs().addAll(addSample);
    }
}
