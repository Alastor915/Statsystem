package com.statsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 27.11.2016.
 *
 */
    public class NewCalcController implements Initializable {
    @FXML
    ComboBox choiceCalcBox;
    @FXML
    Button cancelBtn;
    @FXML
    Button okBtn;
    private Stage m_stage;
    private SampleTabController sampleTabController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelBtn.setOnAction(e -> m_stage.close());
        okBtn.setOnAction(e -> {
            try {
                m_stage.close();
                String fxmlFile = "/fxml/interpolation_tab.fxml";
                sampleTabController.getCalcTabPane().getTabs().remove(sampleTabController.getCalcNew());
                sampleTabController.getCalcTabPane().getTabs().addAll((Tab) FXMLLoader.load(this.getClass().getResource(fxmlFile)));
                sampleTabController.getCalcTabPane().getTabs().addAll(sampleTabController.getCalcNew());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        choiceCalcBox.setOnAction(e -> {

        });
    }

    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }

    public SampleTabController getSampleTabController() {
        return sampleTabController;
    }

    public void setSampleTabController(SampleTabController sampleTabController) {
        this.sampleTabController = sampleTabController;
    }
}