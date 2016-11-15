package com.statsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;

import java.net.URL;
import java.util.*;

/**
 * Created by Нестеренко on 06.11.2016.
 */
public class MainController implements Initializable {

    @FXML private InterpolationController interpolationController;

    public void initialize(URL location, ResourceBundle resources) {
        List<Point2D> staticSelection = new ArrayList<>();

        staticSelection.add(new Point2D(100000000000d,2));
        staticSelection.add(new Point2D(200000000000d,3d));
        staticSelection.add(new Point2D(300000000000d,4d));
        staticSelection.add(new Point2D(400000000000d,5d));
        staticSelection.add(new Point2D(500000000000d, 6d));
        staticSelection.add(new Point2D(600000000000d,7d));
        staticSelection.add(new Point2D(700000000000d, 8d));
        staticSelection.add(new Point2D(800000000000d, 9d));
        interpolationController.setMainController(this);
        interpolationController.setSelection(staticSelection);
        interpolationController.start();
    }
}
