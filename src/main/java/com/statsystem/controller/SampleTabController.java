package com.statsystem.controller;

import com.statsystem.entity.Sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        newCalc.setOnSelectionChanged(e->{
            try {
                if (newCalc.isSelected()) {
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
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    public void start(){
        interpolationController.setMainController(mainController);
        interpolationController.setSample(sample);
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
