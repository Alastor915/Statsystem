package com.statsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 13.12.2016.
 *
 */
public class DispersionController implements Initializable {

        @FXML TextArea dispersionResultTextArea;
        @FXML Button dispersionCalcBtn;
        @FXML LineChart<Number,Number> dispersionLineChart;
        @FXML StableTicksAxis dispersionXAxis;
        @FXML StableTicksAxis dispersionYAxis;
        private Stage m_stage;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {

            dispersionCalcBtn.setOnAction(e -> {
            });
        }

        public void start() {

        }
}


