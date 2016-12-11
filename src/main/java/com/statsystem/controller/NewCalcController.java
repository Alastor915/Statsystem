package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.entity.Analysis;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.statsystem.utils.ErrorMessage.showErrorMessage;

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
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        cancelBtn.setOnAction(e -> m_stage.close());
        okBtn.setOnAction(e -> {
            if(choiceCalcBox.getSelectionModel().getSelectedIndex() != -1) {
                Choice selected = (Choice) choiceCalcBox.getValue();
                try {
                    m_stage.close();
                    String fxmlFile = selected.id.getPath();
                    Sample sample = sampleTabController.getSample();
                    Analysis analysis = new Analysis(-1L, selected.displayString, selected.id, null, sample);
                    FXMLLoader loader = new FXMLLoader();
                    Tab tab = loader.load(getClass().getResource(fxmlFile).openStream());
                    tab.setText(selected.displayString);
                    InterpolationController interpolationController = loader.getController();
                    interpolationController.setAnalysis(analysis);
                    interpolationController.setDbService(sampleTabController.getMainController().getDbService());
                    interpolationController.start();
                    sampleTabController.getCalcTabPane().getTabs().remove(sampleTabController.getCalcNew());
                    sampleTabController.getCalcTabPane().getTabs().addAll(tab);
                    sampleTabController.getCalcTabPane().getSelectionModel().selectLast();
                    sampleTabController.getCalcTabPane().getTabs().addAll(sampleTabController.getCalcNew());

                } catch (IOException ex){
                    showErrorMessage("Ошибка при создании новой вкладки", "Невозможно загрузить fxml форму. Возможно, программа " +
                            "повреждена. Отчет об ошибке: \n" + ex.toString());
                }  catch (Exception ex){
                    showErrorMessage("Непридвиденная ошибка",
                            "Отчет об ошибке: \n" + ex.toString());
                }
            }
        });


        choiceCalcBox.setOnShowing(e -> {
            ObservableList<Choice> choices = FXCollections.observableArrayList();

            List<AnalysisType> analysisTypes = null;
            analysisTypes = Arrays.asList(AnalysisType.values());
            for (AnalysisType analysisType : analysisTypes) {
                choices.add(new Choice(analysisType, analysisType.getName()));
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

    static class Choice {
        AnalysisType id;
        String displayString;
        Choice(AnalysisType id, String displayString) { this.id = id; this.displayString = displayString; }
        @Override public String toString() { return displayString; }
    }
}
