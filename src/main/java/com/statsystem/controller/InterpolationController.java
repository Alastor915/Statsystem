package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.*;
import com.statsystem.entity.impl.NewtonAnalysisData;
import com.statsystem.entity.impl.SplineAnalysisData;
import com.statsystem.logic.AnalysisService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
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
import java.util.*;

import static com.statsystem.utils.Message.showErrorMessage;
import static com.statsystem.utils.Message.showInfoMessage;

/**
 * Created by User on 10.11.2016.
 *
 */
public class InterpolationController implements Initializable, CalculationController {
    @FXML SplitPane splitPane;
    @FXML LineChart<Number,Number> lineChart;
    @FXML StableTicksAxis xAxis;
    @FXML StableTicksAxis yAxis;
    @FXML CheckBox drawChart;
    @FXML Button calcBtn;
    @FXML TextField xField;
    @FXML TextArea resultTextArea;
    @FXML Tab tab;
    private MainController mainController;
    private static String DEFULT_X_FIELD_VALUE = "08.04.2013 21:19:14";
    private Analysis analysis;
    AnalysisData analysisData;
    private Sample sample;
    private DateFormat format;
    private DateFormat formatView;
    private boolean isInterpolationDrawn = false;
    private UnivariateFunction f;
    private DBService dbService;

    public void initialize(URL location, ResourceBundle resources) {
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        xField.setText(DEFULT_X_FIELD_VALUE);
        resultTextArea.setEditable(false);
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
        analysisData = analysis.getData();
        sample = analysis.getSample();
        tab.setText(analysis.getName());
        if (analysisData != null) {
            List<Unit> units;
            if(analysis.getType() == AnalysisType.NEWTON) {
                units = ((NewtonAnalysisData) analysisData).getUnits();
            }
            else {
                units = ((SplineAnalysisData) analysisData).getUnits();
            }
            for (Unit unit : units){
                resultTextArea.setText(resultTextArea.getText() + "\n" + format.format(unit.getDate()) + "; " + String.format("%.5f", unit.getValue()));
            }
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
        calcBtn.setOnAction(e -> {
            if (analysisData == null) {
                try {
                    if (analysis.getType() == AnalysisType.NEWTON) {
                        analysisData = AnalysisService.getNewtonInterpolationFunction(sample);
                    }
                    else {
                        analysisData = AnalysisService.getSplineInterpolationFunction(sample);
                    }
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
            if (analysis.getType() == AnalysisType.NEWTON) {
                f = ((NewtonAnalysisData)analysisData).getF();
            }
            else {
                f = ((SplineAnalysisData)analysisData).getF();
            }
            interpolate();
        });
    }
    private void interpolate() {
        try {
            double date = new Long(format.parse(xField.getText().trim()).getTime()).doubleValue();
            if (drawChart.isSelected()) {
                if (!isInterpolationDrawn) {
                    XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                    series2.setName("Интерполяционный полином");
                    double start = sample.getData().get(0).getDate();
                    double end = sample.getData().get(sample.getData().size() - 1).getDate();
                    double step = (end - start) / 3000;
                    while (start < end) {
                        XYChart.Data<Number, Number> data = new XYChart.Data<>(start, f.value(start));
                        Rectangle rect = new Rectangle(0, 0);
                        rect.setVisible(false);
                        data.setNode(rect);
                        series2.getData().add(data);
                        start += step;
                    }
                    lineChart.getData().add(series2);
                    List<Unit> units;
                    if(analysis.getType() == AnalysisType.NEWTON) {
                        units = ((NewtonAnalysisData) analysisData).getUnits();
                    }
                    else {
                        units = ((SplineAnalysisData) analysisData).getUnits();
                    }
                    for (Unit unit : units) {
                        XYChart.Series<Number, Number> series = new XYChart.Series<>();
                        XYChart.Data<Number, Number> data = new XYChart.Data<>(unit.getDate(), unit.getValue());
                        series.getData().add(data);
                        series.setName("x = " + format.format(new Date(unit.getDate().longValue())));
                        lineChart.getData().add(series);
                        series.getData().get(0).getNode().setCursor(Cursor.HAND);
                        Tooltip.install(series.getData().get(0).getNode(), new Tooltip('(' + formatView.format(new Date(unit.getDate().longValue())) + "; " + String.format("%.5f", unit.getValue()) + ')'));
                    }
                    isInterpolationDrawn = true;
                }
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                XYChart.Data<Number, Number> data = new XYChart.Data<>(date, f.value(date));
                series.getData().add(data);
                series.setName("x = " + format.format(new Date(data.getXValue().longValue())));
                lineChart.getData().add(series);
                series.getData().get(0).getNode().setCursor(Cursor.HAND);
                Tooltip.install(series.getData().get(0).getNode(), new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
            }
            resultTextArea.setText(resultTextArea.getText() + "\n" + format.format(date) + "; " + String.format("%.5f", f.value(date)));
            if(analysis.getType() == AnalysisType.NEWTON) {
                ((NewtonAnalysisData) analysisData).getUnits().add(new Unit(date, f.value(date)));
            }
            else {
                ((SplineAnalysisData) analysisData).getUnits().add(new Unit(date, f.value(date)));
            }
            if (analysis.getId() < 0) {
                dbService.insertAnalysis(analysis);
                tab.setText(analysis.getName());
                showInfoMessage("Расчет записан в базу данных", analysis.toString());
            } else {
                dbService.updateAnalysis(analysis);
                tab.setText(analysis.getName());
                showInfoMessage("Расчет обновлен в базе данных", analysis.toString());
            }
        } catch (ParseException ex){
            showErrorMessage("Ошибка при распознавании даты",
                    "Ошибка при распознавании даты. Убедитесь, что дата (по значению x) соответсвует требуемому формату" +
                            "(см. Справка, Создание проекта). Отчет об ошибке: \n"
                            + ex.toString());
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
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        lineChart.setTitle("График зависимости мощности от времени. Рассматриваемый день: " +
                dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
        lineChart.setAnimated(false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        xAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
        xAxis.setLabel("Время");
        xAxis.setForceZeroInRange(false);
        yAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
        yAxis.setLabel("Мощность");
        yAxis.setForceZeroInRange(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(sample.getName());
        for (int i = 0; i < sample.getData().size(); ++i) {
            series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
        }
        lineChart.getData().add(series);
        ChartPanManager panner = new ChartPanManager( lineChart );
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

        JFXChartUtil.setupZooming(lineChart, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                    mouseEvent.isShortcutDown() ||
                    mouseEvent.getButton() == MouseButton.SECONDARY)
                mouseEvent.consume();
        });

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(lineChart);

        for (Data<Number, Number> data : series.getData()) {
            Node node = data.getNode() ;
            Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(e -> {
                if(e.getButton() == MouseButton.PRIMARY) {
                    Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                    double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                    Number y = yAxis.getValueForDisplay(yAxisLoc);
//                    data.setXValue(x);
                    data.setYValue(y);
                }
            });
            node.setOnMouseReleased(ev -> {
                Point2D pointInScene = new Point2D(ev.getSceneX(), ev.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
                Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                Unit unit = analysis.getSample().getUnitByDate((Double) data.getXValue());
                unit.setValue((Double) y);
                try {
                    dbService.updateUnit(unit);
                    showInfoMessage("Значение элемента выборки обновлено", unit.toString());
                } catch (DBException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

}
