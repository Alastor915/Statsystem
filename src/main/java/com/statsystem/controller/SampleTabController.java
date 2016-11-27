package com.statsystem.controller;

import com.statsystem.entity.Sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 27.11.2016.
 */
public class SampleTabController implements Initializable {
    private MainController mainController;
    private Sample sample;
    @FXML private InterpolationController interpolationController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
