package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.utils.Parser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by User on 20.11.2016.
 *
 */
public class CreateProjectController implements Initializable {
    @FXML Button chooseBtn;
    @FXML TextField pathField;
    @FXML TextField projectName;
    @FXML Button cancelBtn;
    @FXML Button okBtn;
    private FileChooser fileChooser;
    private Stage m_stage;
    private MainController mainController;
    private DBService dbService;


    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }
    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathField.setEditable(false);
        fileChooser = new FileChooser();
        chooseBtn.setOnAction(e->{
            File file = fileChooser.showOpenDialog(m_stage);
            if (file != null)
                pathField.setText(file.getAbsolutePath());
        });
        cancelBtn.setOnAction(e-> m_stage.close());
        okBtn.setOnAction(e-> {
            try {
                List<Sample> samples = Parser.parse(pathField.getText().trim());
                Project project = new Project(projectName.getText());
                project.setSamples(samples);
                dbService.insertProject(project);
                mainController.setProject(project);
                mainController.loadXLSXSamples(samples);
                mainController.getM_stage().setTitle("Система обработки данных - " + project.getName());
                m_stage.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
