package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Нестеренко on 27.11.2016.
 *
 */
public class LoadProjectController implements Initializable {
    @FXML Button newBtn;
    @FXML ComboBox chooseBox;
    @FXML Button cancelBtn;
    @FXML Button okBtn;
    private MainController mainController;
    private DBService dbService;
    private Stage m_stage;
    private Stage create_project_stage;

    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        cancelBtn.setOnAction(e-> m_stage.close());
        okBtn.setOnAction(e-> {
            Choice selected = (Choice) chooseBox.getValue();
            try {
                Project project = dbService.getProject(selected.id);
                mainController.setProject(project);
                mainController.loadXLSXSamples(project.getSamples());
                mainController.getM_stage().setTitle("Система обработки данных - " + project.getName());
                m_stage.close();
            } catch (DBException e1) {
                e1.printStackTrace();
            }
        });

        chooseBox.setOnShowing(e-> {
            ObservableList<Choice> choices = FXCollections.observableArrayList();
            List<Project> projects = null;
            try {
                projects = dbService.getAllProjects();
            } catch (DBException ex) {
                ex.printStackTrace();
            }
            if (projects != null) {
                for (Project project : projects) {
                    choices.add(new Choice(project.getId(), project.getName()));
                }
            }
            chooseBox.setItems(choices);
            chooseBox.getSelectionModel().select(0);
            chooseBox.setOnShowing(Event::consume);
        });
    }

    static class Choice {
        Long id;
        String displayString;
        Choice(Long id, String displayString) { this.id = id; this.displayString = displayString; }
        @Override public String toString() { return displayString; }
    }
}