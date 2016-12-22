package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.*;
import com.statsystem.entity.impl.SimpleAnalysisData;
import com.statsystem.entity.impl.SplineAnalysisData;
import com.statsystem.logic.AnalysisService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.statsystem.utils.Message.showErrorMessage;
import static com.statsystem.utils.Message.showInfoMessage;

/**
 * Created by Нестеренко on 13.12.2016.
 */
public class DispersionController implements Initializable, CalculationController {

        @FXML TextArea dispersionResultTextArea;
        @FXML Button dispersionCalcBtn;
        @FXML LineChart<Number,Number> dispersionLineChart;
        @FXML StableTicksAxis dispersionXAxis;
        @FXML StableTicksAxis dispersionYAxis;
        @FXML Tab varianceTab;

        private Stage m_stage;
        private Analysis analysis;
        private MainController mainController;
        private DBService dbService;
        private DateFormat format;
        private DateFormat formatView;
        private SimpleAnalysisData analysisData;
        private Sample sample;
        private Double variance;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {
                format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
                formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                dispersionResultTextArea.setEditable(false);
        }

        @Override
        public void setAnalysis(Analysis analysis) {
                this.analysis = analysis;
                analysisData =(SimpleAnalysisData)analysis.getData();
                sample = analysis.getSample();
                varianceTab.setText(analysis.getName());
                if (analysisData != null) {
                        dispersionResultTextArea.setText(analysisData.getValue().toString());
                }
        }

        @Override
        public void setMainController(MainController controller) {
                mainController = controller;
        }

        @Override
        public void setDbService(DBService dbService) {
                this.dbService = dbService;
        }

        public void start() {
                chartInit();
                dispersionCalcBtn.setOnAction(e -> {
                        if (analysisData == null) {
                                try {
                                        analysisData = AnalysisService.getVariance(sample);
                                        analysis.setData(analysisData);
                                } catch (DimensionMismatchException ex) {
                                        showErrorMessage("Невозможно построить интерполирующую функцию", "Необходимо, чтобы выборка имела" +
                                                "равные интервалы по x. Отчет об ошибке: " + ex.toString());
                                } catch (NonMonotonicSequenceException ex) {
                                        showErrorMessage("Невозможно построить интерполирующую функцию", "Необходимо, чтобы выборка была" +
                                                "монотонна по x. Отчет об ошибке: " + ex.toString());
                                } catch (NumberIsTooSmallException ex) {
                                        showErrorMessage("Невозможно построить интерполирующую функцию", "Выборка имеет слишком мальнькие" +
                                                "значения. Отчет об ошибке: " + ex.toString());
                                } catch (Exception ex){
                                        showErrorMessage("Непридвиденная ошибка",
                                                "Отчет об ошибке: \n" + ex.toString());
                                }
                        }
                        variance = analysisData.getValue();
                        calculate();
                });

        }

        private void calculate() {
                try {
                        dispersionResultTextArea.setText(variance.toString());
                        if (analysis.getId() < 0) {
                                dbService.insertAnalysis(analysis);
                                varianceTab.setText(analysis.getName());
                                showInfoMessage("Расчет записан в базу данных", analysis.toString());
                        } else {
                                dbService.updateAnalysis(analysis);
                                varianceTab.setText(analysis.getName());
                                showInfoMessage("Расчет обновлен в базе данных", analysis.toString());
                        }
                } catch (DBException ex){
                        showErrorMessage("Ошибка при работе с базой данных", "Ошибка при сохранении результатов расчета в базу данных." +
                                " Отчет об ошибке: \n" + ex.toString());
                } catch (OutOfRangeException ex){
                        showErrorMessage("Ошибка при расчете интерполяции", "Полученное значение x не попадает в интевал выборки. " +
                                "Измените параметр x. Отчет об ошибке: \n" + ex.toString());
                } catch (Exception ex){
                        showErrorMessage("Непридвиденная ошибка",
                                "Отчет об ошибке: \n" + ex.toString());
                }
        }

        private void chartInit(){
                dispersionLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                dispersionLineChart.setTitle("График зависимости мощности от времени. Рассматриваемый день: " +
                        dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
                dispersionLineChart.setAnimated(false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                dispersionXAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
                dispersionXAxis.setLabel("Время");
                dispersionXAxis.setForceZeroInRange(false);
                dispersionYAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
                dispersionYAxis.setLabel("Мощность");
                dispersionYAxis.setForceZeroInRange(false);

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(sample.getName());
                for (int i = 0; i < sample.getData().size(); ++i) {
                        series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
                }
                dispersionLineChart.getData().add(series);
                ChartPanManager panner = new ChartPanManager( dispersionLineChart );
                panner.setMouseFilter(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY ||
                                (mouseEvent.getButton() == MouseButton.PRIMARY &&
                                        mouseEvent.isShortcutDown())) {
                                // do action
                        } else {
                                mouseEvent.consume();
                        }
                });
                panner.start();

                JFXChartUtil.setupZooming(dispersionLineChart, mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                                mouseEvent.isShortcutDown() ||
                                mouseEvent.getButton() == MouseButton.SECONDARY)
                                mouseEvent.consume();
                });

                JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(dispersionLineChart);

                for (XYChart.Data<Number, Number> data : series.getData()) {
                        Node node = data.getNode() ;
                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                        node.setCursor(Cursor.HAND);
                        node.setOnMouseDragged(e -> {
                                if(e.getButton() == MouseButton.PRIMARY) {
                                        Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                                        double yAxisLoc = dispersionYAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                                        Number y = dispersionYAxis.getValueForDisplay(yAxisLoc);
//                    data.setXValue(x);
                                        data.setYValue(y);
                                }
                        });
                        node.setOnMouseReleased(ev -> {
                                Point2D pointInScene = new Point2D(ev.getSceneX(), ev.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                                double yAxisLoc = dispersionYAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                                Number y = dispersionYAxis.getValueForDisplay(yAxisLoc);
                                Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                                Unit unit = analysis.getSample().getUnitByDate((Double) data.getXValue());
                                unit.setValue((Double) y);
                                try {
                                        dbService.updateUnit(unit);
                                        showInfoMessage("Значение элемента выборки обновлено", unit.toString());
                                } catch (DBException ex) {
                                        showErrorMessage("Ошибка при работе с базой данных", "Ошибка при обновлении элемента выборки в базе данных." +
                                                " Отчет об ошибке: \n" + ex.toString());
                                }
                        });
                }
        }
}


