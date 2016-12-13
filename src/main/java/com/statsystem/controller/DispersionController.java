package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import com.statsystem.entity.impl.SplineAnalysisData;
import com.statsystem.logic.AnalysisService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.statsystem.utils.ErrorMessage.showErrorMessage;

/**
 * Created by Нестеренко on 13.12.2016.
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


