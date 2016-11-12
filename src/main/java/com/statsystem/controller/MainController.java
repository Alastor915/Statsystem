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

        staticSelection.add(new Point2D(1,2));
        staticSelection.add(new Point2D(2d,3d));
        staticSelection.add(new Point2D(3d,4d));
        staticSelection.add(new Point2D(4d,5d));
        staticSelection.add(new Point2D(5d, 6d));
        staticSelection.add(new Point2D(6d,7d));
        staticSelection.add(new Point2D(7d, 8d));
        staticSelection.add(new Point2D(8d, 9d));
        interpolationController.setMainController(this);
        interpolationController.setSelection(staticSelection);
        interpolationController.start();
    }
}
