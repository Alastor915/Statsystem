package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Sample;
import com.statsystem.entity.impl.CorrelationAnalysisData;
import com.statsystem.logic.AnalysisService;
import com.statsystem.logic.statchars.CorrelationFunction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
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

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.statsystem.utils.Message.showErrorMessage;
import static com.statsystem.utils.Message.showInfoMessage;

/**
 * Created by Нестеренко on 13.12.2016.
 */
public class CorrelationController implements Initializable, CalculationController {

    @FXML
    CheckBox correlationDrawChart;
    @FXML
    TextArea correlationResultTextArea;
    @FXML
    Button correlationCalcBtn;
    @FXML
    LineChart<Number, Number> correlationLineChart;
    @FXML
    StableTicksAxis correlationXAxis;
    @FXML
    StableTicksAxis correlationYAxis;
    @FXML
    Tab correlTab;

    private Stage m_stage;
    private MainController mainController;
    private Analysis analysis;
    CorrelationAnalysisData analysisData;
    private Sample sample;
    private DateFormat format;
    private DateFormat formatView;
    private boolean isCorrelDrawn = false;
    private CorrelationFunction f;
    private DBService dbService;

    public void initialize(URL location, ResourceBundle resources) {
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        correlationResultTextArea.setEditable(false);
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
        analysisData = (CorrelationAnalysisData) analysis.getData();
        sample = analysis.getSample();
        correlTab.setText(analysis.getName());
        if (analysisData != null) {
            correlationResultTextArea.setText(analysisData.getF().toString());
        }
    }

    public void setMainController(MainController controller) {
        mainController = controller;

    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public void start() {
        chartInit();
        correlationCalcBtn.setOnAction(e -> {
            if (analysisData == null) {
                try {
                    analysisData = AnalysisService.getCorrelationFunction(sample);
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
                } catch (Exception ex) {
                    showErrorMessage("Непридвиденная ошибка",
                            "Отчет об ошибке: \n" + ex.toString());
                }
            }
            f = analysisData.getF();
            corr();
        });
    }

    private void corr() {
        try {
            if (correlationDrawChart.isSelected()) {
                if (!isCorrelDrawn) {
                    XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                    series2.setName("Автокорреляционная функция");
                    for (int i = 0; i < f.getQuantity().length; i++) {
                        XYChart.Data<Number, Number> data = new XYChart.Data<>(f.getValues()[i], f.getQuantity()[i]);
                        series2.getData().add(data);
                    }
                    correlationLineChart.getData().add(series2);
                    isCorrelDrawn = true;
                }
            }
            correlationResultTextArea.setText(analysisData.getF().toString());
            if (analysis.getId() < 0) {
                dbService.insertAnalysis(analysis);
                showInfoMessage("Расчет записан в базу данных", analysis.toString());
            } else {
                dbService.updateAnalysis(analysis);
                showInfoMessage("Расчет обновлен в базе данных", analysis.toString());
            }
        } catch (DBException ex) {
            showErrorMessage("Ошибка при работе с базой данных", "Ошибка при сохранении результатов расчета в базу данных." +
                    " Отчет об ошибке: \n" + ex.toString());
        } catch (Exception ex) {
            showErrorMessage("Непридвиденная ошибка",
                    "Отчет об ошибке: \n" + ex.toString());
        }
    }

    private void chartInit() {
        correlationLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        correlationLineChart.setTitle("Автокорреляционная функция. Рассматриваемый день: " +
                dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
        correlationLineChart.setAnimated(false);

        correlationXAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
        correlationXAxis.setForceZeroInRange(false);
        correlationYAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
        correlationYAxis.setForceZeroInRange(false);

//                XYChart.Series<Number, Number> series = new XYChart.Series<>();
//                series.setName(sample.getName());
//                for (int i = 0; i < sample.getData().size(); ++i) {
//                        series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
//                }
//                correlationLineChart.getData().add(series);
        ChartPanManager panner = new ChartPanManager(correlationLineChart);
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

        JFXChartUtil.setupZooming(correlationLineChart, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                    mouseEvent.isShortcutDown() ||
                    mouseEvent.getButton() == MouseButton.SECONDARY)
                mouseEvent.consume();
        });

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(correlationLineChart);

//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                        Node node = data.getNode() ;
//                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
//                        node.setCursor(Cursor.HAND);
//                        node.setOnMouseDragged(e -> {
//                                if(e.getButton() == MouseButton.PRIMARY) {
//                                        Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                                        double xAxisLoc = correlationXAxis.sceneToLocal(pointInScene).getX();
//                                        double yAxisLoc = correlationYAxis.sceneToLocal(pointInScene).getY();
//                                        Number x = correlationXAxis.getValueForDisplay(xAxisLoc);
//                                        Number y = correlationYAxis.getValueForDisplay(yAxisLoc);
//                                        data.setXValue(x);
//                                        data.setYValue(y);
//                                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
//                                }
//                        });
//                }
    }
}


