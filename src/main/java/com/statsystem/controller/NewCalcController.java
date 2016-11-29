package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 27.11.2016.
 *
 */
    public class NewCalcController implements Initializable {
    @FXML ComboBox choiceCalcBox;
    @FXML Button cancelBtn;
    @FXML Button okBtn;
    private Stage m_stage;
    private SampleTabController sampleTabController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelBtn.setOnAction(e -> m_stage.close());
        okBtn.setOnAction(e -> {
            LoadProjectController.Choice selected = (LoadProjectController.Choice) choiceCalcBox.getValue();
            try {
                m_stage.close();
                String fxmlFile = AnalysisType.getPath(selected.id);
                Sample sample = sampleTabController.getSample();
                FXMLLoader loader = new FXMLLoader();
                Tab tab = loader.load(getClass().getResource(fxmlFile).openStream());
                tab.setText(selected.displayString);
                InterpolationController interpolationController = loader.getController();
                interpolationController.setSample(sample);
                interpolationController.start();
                sampleTabController.getCalcTabPane().getTabs().remove(sampleTabController.getCalcNew());
                sampleTabController.getCalcTabPane().getTabs().addAll(tab);
                sampleTabController.getCalcTabPane().getSelectionModel().selectLast();
                sampleTabController.getCalcTabPane().getTabs().addAll(sampleTabController.getCalcNew());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        choiceCalcBox.setOnShowing(e -> {
            ObservableList<LoadProjectController.Choice> choices = FXCollections.observableArrayList();

            List<AnalysisType> analysisTypes = null;
                analysisTypes = Arrays.asList(AnalysisType.values());
             if (analysisTypes != null) {
                for (AnalysisType analysisType : analysisTypes) {
                    choices.add(new LoadProjectController.Choice(analysisType.getValue(), analysisType.getName()));
                }
            }
            choiceCalcBox.setItems(choices);
            choiceCalcBox.getSelectionModel().select(0);
            choiceCalcBox.setOnShowing(Event::consume);
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
