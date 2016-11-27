package com.statsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 27.11.2016.
 */
public class LoadProjectController implements Initializable {
    @FXML Button newBtn;
    @FXML ComboBox chooseBox;
    @FXML Button cancelBtn;
    @FXML Button okBtn;
    private Stage m_stage;
    private Stage create_project_stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelBtn.setOnAction(e->{
            m_stage.close();
        });
        okBtn.setOnAction(e-> {

        });

        chooseBox.setOnAction(e-> {

        });
    }
    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }
}