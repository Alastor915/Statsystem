package com.statsystem.controller;

import com.statsystem.entity.Sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

/**
 * Created by Нестеренко on 06.11.2016.
 */
public class MainController implements Initializable {

    @FXML private InterpolationController interpolationController;

    public void initialize(URL location, ResourceBundle resources) {
        Sample staticSample = new Sample("Мега Выборка");

        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()),2d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+100000000000L),2.3d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+200000000000L),2.4d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+300000000000L),2.5d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+400000000000L),2.1d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+500000000000L),2.2d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+600000000000L),2.8d));
        staticSample.add(new Pair<>(new Date(System.currentTimeMillis()+700000000000L),2.9d));
        System.out.println(System.currentTimeMillis());
        interpolationController.setMainController(this);
        interpolationController.setSample(staticSample);
        interpolationController.start();
    }
}
