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
public class FuncDistributionController implements Initializable {

        @FXML CheckBox funcDistrbDrawChart;
        @FXML TextArea funcDistrbResultTextArea;
        @FXML Button funcDistrbCalcBtn;
        @FXML LineChart<Number,Number> funcDistrbLineChart;
        @FXML StableTicksAxis funcDistrbXAxis;
        @FXML StableTicksAxis funcDistrbYAxis;
        private Stage m_stage;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {

                funcDistrbCalcBtn.setOnAction(e -> {
                });
        }

        public void start() {
        }
}


