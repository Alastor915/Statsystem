package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import com.statsystem.entity.impl.DistributionAnalysisData;
import com.statsystem.logic.AnalysisService;
import com.statsystem.logic.statchars.DistributionFunction;
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
import org.h2.util.DbDriverActivator;
import sun.applet.Main;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.statsystem.utils.ErrorMessage.showErrorMessage;
/**
 * Created by Нестеренко on 13.12.2016.
 */
public class FuncDistributionController implements Initializable, CalculationController {

        @FXML CheckBox funcDistrbDrawChart;
        @FXML TextArea funcDistrbResultTextArea;
        @FXML Button funcDistrbCalcBtn;
        @FXML LineChart<Number,Number> funcDistrbLineChart;
        @FXML StableTicksAxis funcDistrbXAxis;
        @FXML StableTicksAxis funcDistrbYAxis;
        @FXML Tab funcDistrbTab;
        private Stage m_stage;

        private Analysis analysis;
        DistributionAnalysisData analysisData;
        private Sample sample;
        private DateFormat format;
        private DateFormat formatView;
        private DBService dbService;
        private MainController mainController;
        private DistributionFunction f;
        private boolean isDistribDrawn = false;
        public void initialize(URL location, ResourceBundle resources) {
                format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
                formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                funcDistrbResultTextArea.setEditable(false);
        }

        public void setAnalysis(Analysis analysis) {
                this.analysis = analysis;
                analysisData = (DistributionAnalysisData)analysis.getData();
                sample = analysis.getSample();
                funcDistrbTab.setText(analysis.getName());
                if (analysisData != null) {
                        funcDistrbResultTextArea.setText(analysisData.getF().toString());
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
                funcDistrbCalcBtn.setOnAction(e -> {
                        if (analysisData == null) {
                                try {
                                        analysisData = AnalysisService.getDistributionFunction(sample);
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
                        interpolate();
                });
        }
        private void interpolate() {
                try {
                        if (funcDistrbDrawChart.isSelected()) {
                                if (!isDistribDrawn) {
                                        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                                        series2.setName("Интерполяционный полином");
                                        double[] variational = new double[sample.getValues().length];
                                        System.arraycopy(sample.getValues(),0, variational,0,sample.getValues().length);
                                        Arrays.sort(variational);
                                        double start = variational[0] - variational[0]/1000;
                                        double end = variational[variational.length - 1] + variational[variational.length - 1]/1000;
                                        double step = (end - start) / 3000;
                                        while (start < end) {
                                                XYChart.Data<Number, Number> data = new XYChart.Data<>(start, f.value(start));
                                                Rectangle rect = new Rectangle(0, 0);
                                                rect.setVisible(false);
                                                data.setNode(rect);
                                                series2.getData().add(data);
                                                start += step;
                                        }
                                        funcDistrbLineChart.getData().add(series2);
                                        isDistribDrawn = true;
                                }
                        }
                        funcDistrbResultTextArea.setText(f.toString());
                        if (analysis.getId() < 0) {
                                analysis.setName("Расчет в базе");
                                dbService.insertAnalysis(analysis);
                                funcDistrbTab.setText(analysis.getName());
                        } else {
                                analysis.setName(analysis.getName() + "+");
                                dbService.updateAnalysis(analysis);
                                funcDistrbTab.setText(analysis.getName());
                        }
//                } catch (ParseException ex){
//                        showErrorMessage("Ошибка при распознавании даты",
//                                "Ошибка при распознавании даты. Убедитесь, что дата (по значению x) соответсвует требуемому формату" +
//                                        "(см. Справка, Создание проекта). Отчет об ошибке: \n"
//                                        + ex.toString());
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
                funcDistrbLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                funcDistrbLineChart.setTitle("График зависимости мощности от времени. Рассматриваемый день: " +
                        dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
                funcDistrbLineChart.setAnimated(false);

//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                funcDistrbXAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
                funcDistrbXAxis.setLabel("Время");
                funcDistrbXAxis.setForceZeroInRange(false);
                funcDistrbYAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
                funcDistrbYAxis.setLabel("Мощность");
                funcDistrbYAxis.setForceZeroInRange(false);

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(sample.getName());
                for (int i = 0; i < sample.getData().size(); ++i) {
                        series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
                }
//                funcDistrbLineChart.getData().add(series);
                ChartPanManager panner = new ChartPanManager( funcDistrbLineChart );
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

                JFXChartUtil.setupZooming(funcDistrbLineChart, mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                                mouseEvent.isShortcutDown() ||
                                mouseEvent.getButton() == MouseButton.SECONDARY)
                                mouseEvent.consume();
                });

                JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(funcDistrbLineChart);

//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                        Node node = data.getNode() ;
//                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
//                        node.setCursor(Cursor.HAND);
//                        node.setOnMouseDragged(e -> {
//                                if(e.getButton() == MouseButton.PRIMARY) {
//                                        Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                                        double xAxisLoc = funcDistrbXAxis.sceneToLocal(pointInScene).getX();
//                                        double yAxisLoc = funcDistrbYAxis.sceneToLocal(pointInScene).getY();
//                                        Number x = funcDistrbXAxis.getValueForDisplay(xAxisLoc);
//                                        Number y = funcDistrbYAxis.getValueForDisplay(yAxisLoc);
//                                        data.setXValue(x);
//                                        data.setYValue(y);
//                                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
//                                }
//                        });
//                }
        }
}


