package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import com.statsystem.entity.impl.SimpleAnalysisData;
import com.statsystem.logic.AnalysisService;
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
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import static com.statsystem.utils.Message.showErrorMessage;
import static com.statsystem.utils.Message.showInfoMessage;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Нестеренко on 13.12.2016.
 */
public class ExpectedValueController implements Initializable, CalculationController {

        @FXML Tab meanTab;
        @FXML CheckBox expectedValueDrawChart;
        @FXML TextArea expectedValueResultTextArea;
        @FXML Button expectedValueCalcBtn;
        @FXML LineChart<Number,Number> expectedValueLineChart;
        @FXML StableTicksAxis expectedValueXAxis;
        @FXML StableTicksAxis expectedValueYAxis;

        private Stage m_stage;
        SimpleAnalysisData analysisData;
        private Sample sample;
        private DateFormat format;
        private DateFormat formatView;
        private Double mean;
        Analysis analysis;
        DBService dbService;
        MainController mainController;
        private boolean isExDrawn = false;

        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {
                format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
                formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                expectedValueResultTextArea.setEditable(false);
        }

        @Override
        public void setAnalysis(Analysis analysis) {
                this.analysis = analysis;
                analysisData =(SimpleAnalysisData)analysis.getData();
                sample = analysis.getSample();
                meanTab.setText(analysis.getName());
                if (analysisData != null) {
                        expectedValueResultTextArea.setText(analysisData.getValue().toString());
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
                expectedValueCalcBtn.setOnAction(e -> {
                        if (analysisData == null) {
                                try {
                                        analysisData = AnalysisService.getMeanValue(sample);
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
                        mean = analysisData.getValue();
                        calculate();
                });

        }

        private void calculate() {
                try {
                        if (!isExDrawn) {
                                XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                                series2.setName("Мат ожидание");
                                XYChart.Data<Number, Number> data = new XYChart.Data<>(sample.getData().get(0).getDate(), mean);
                                XYChart.Data<Number, Number> data2 = new XYChart.Data<>(sample.getData().get(sample.getData().size() - 1).getDate(), mean);
                                Rectangle rect = new Rectangle(0, 0);
                                rect.setVisible(false);
                                Rectangle rect2 = new Rectangle(0, 0);
                                rect2.setVisible(false);
                                data.setNode(rect);
                                data2.setNode(rect2);
                                series2.getData().add(data);
                                series2.getData().add(data2);
                                expectedValueResultTextArea.setText(mean.toString());
                                expectedValueLineChart.getData().add(series2);
                                if (analysis.getId() < 0) {
                                        dbService.insertAnalysis(analysis);
                                        meanTab.setText(analysis.getName());
                                        showInfoMessage("Расчет записан в базу данных", analysis.toString());
                                } else {
                                        dbService.updateAnalysis(analysis);
                                        meanTab.setText(analysis.getName());
                                        showInfoMessage("Расчет обновлен в базе данных", analysis.toString());
                                }
                                isExDrawn = true;
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
                expectedValueLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                expectedValueLineChart.setTitle("График зависимости мощности от времени. Рассматриваемый день: " +
                        dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
                expectedValueLineChart.setAnimated(false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                expectedValueXAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
                expectedValueXAxis.setLabel("Время");
                expectedValueXAxis.setForceZeroInRange(false);
                expectedValueYAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
                expectedValueYAxis.setLabel("Мощность");
                expectedValueYAxis.setForceZeroInRange(false);

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(sample.getName());
                for (int i = 0; i < sample.getData().size(); ++i) {
                        series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
                }
                expectedValueLineChart.getData().add(series);
                ChartPanManager panner = new ChartPanManager( expectedValueLineChart );
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

                JFXChartUtil.setupZooming(expectedValueLineChart, mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                                mouseEvent.isShortcutDown() ||
                                mouseEvent.getButton() == MouseButton.SECONDARY)
                                mouseEvent.consume();
                });

                JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(expectedValueLineChart);

                for (XYChart.Data<Number, Number> data : series.getData()) {
                        Node node = data.getNode() ;
                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                        node.setCursor(Cursor.HAND);
                        node.setOnMouseDragged(e -> {
                                if(e.getButton() == MouseButton.PRIMARY) {
                                        Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                                        double yAxisLoc = expectedValueYAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                                        Number y = expectedValueYAxis.getValueForDisplay(yAxisLoc);
//                    data.setXValue(x);
                                        data.setYValue(y);
                                }
                        });
                        node.setOnMouseReleased(ev -> {
                                Point2D pointInScene = new Point2D(ev.getSceneX(), ev.getSceneY());
//                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                                double yAxisLoc = expectedValueYAxis.sceneToLocal(pointInScene).getY();
//                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                                Number y = expectedValueYAxis.getValueForDisplay(yAxisLoc);
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


