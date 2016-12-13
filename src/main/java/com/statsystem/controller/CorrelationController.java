package com.statsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 13.12.2016.
 */
public class CorrelationController implements Initializable {

        @FXML CheckBox correlationDrawChart;
        @FXML TextArea correlationResultTextArea;
        @FXML Button correlationCalcBtn;
        @FXML LineChart<Number,Number> expectedValueLineChart;
        @FXML StableTicksAxis correlationXAxis;
        @FXML StableTicksAxis correlationYAxis;
        private Stage m_stage;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {

                correlationCalcBtn.setOnAction(e -> {
                });
        }

        public void start() {
        }
}


