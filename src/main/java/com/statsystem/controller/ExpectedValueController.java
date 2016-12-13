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
public class ExpectedValueController implements Initializable {

        @FXML CheckBox expectedValueDrawChart;
        @FXML TextArea expectedValueResultTextArea;
        @FXML Button expectedValueCalcBtn;
        @FXML LineChart<Number,Number> expectedValueLineChart;
        @FXML StableTicksAxis expectedValueXAxis;
        @FXML StableTicksAxis expectedValueYAxis;
        private Stage m_stage;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {

                expectedValueCalcBtn.setOnAction(e -> {
                });
        }

        public void start() {
        }
}


