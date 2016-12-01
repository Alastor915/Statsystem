package com.statsystem.controller;

import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 27.11.2016.
 *
 */
public class SampleTabController implements Initializable {
    private MainController mainController;
    private Sample sample;
    @FXML private InterpolationController interpolationController;
    @FXML private Tab newCalc;
    @FXML private TabPane calcTabPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button addButton = new Button("+");
        addButton.getStyleClass().add("tab-button");
        addButton.setOnAction(e -> {
            try {
                //if (newCalc.isSelected()) {
                String fxmlFile = "/fxml/choice_type_calc_dialog.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
                NewCalcController newCalcController = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.initOwner(m_stage);
                stage.setTitle("Система обработки данных");
                stage.setScene(new Scene(root));
                newCalcController.setM_stage(stage);
                newCalcController.setSampleTabController(this);
                stage.showAndWait();
                //}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        newCalc.setGraphic(addButton);
        newCalc.getStyleClass().add("tab-button-holder");

    }

    public void start(){
        interpolationController.setMainController(mainController);
        interpolationController.setDbService(mainController.getDbService());
        Analysis analysis;
        if (sample.getAnalyses().isEmpty()){
            analysis = new Analysis(-1L, "Расчет", AnalysisType.SPLINE, null, sample);
        } else {
            analysis = sample.getAnalyses().get(0);
        }
        interpolationController.setAnalysis(analysis);
        interpolationController.start();
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public TabPane getCalcTabPane(){
        return calcTabPane;
    }

    public Tab getCalcNew(){
        return newCalc;
    }
}
