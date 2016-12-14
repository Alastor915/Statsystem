package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import com.statsystem.entity.impl.NewtonAnalysisData;
import com.statsystem.entity.impl.SplineAnalysisData;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.statsystem.utils.ErrorMessage.showErrorMessage;

/**
 * Created by Нестеренко on 13.12.2016.
 */
public class LsmController implements Initializable, CalculationController {

        @FXML LineChart lsmLineChart;
        @FXML StableTicksAxis lsmXAxis;
        @FXML StableTicksAxis lsmYAxis;
        @FXML TextField lsmField;
        @FXML TextArea lsmResultTextArea;
        @FXML Button lsmCalcBtn;
        @FXML CheckBox lsmDrawChart;
        @FXML Tab lsmTab;


        private DateFormat format;
        private DateFormat formatView;
        private Sample sample;
        private MainController mainController;
        private DBService dbService;
        //private LsmAnalysisData analysisData;
        private boolean isLsmDrawn = false;

        private static String DEFULT_X_FIELD_VALUE = "08.04.2013 21:19:14";


        @Override
        @SuppressWarnings("unchecked")
        public void initialize(URL location, ResourceBundle resources) {
                format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
                formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                lsmField.setText(DEFULT_X_FIELD_VALUE);
                lsmResultTextArea.setEditable(false);

        }

        @Override
        public void setAnalysis(Analysis analysis){

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
                lsmCalcBtn.setOnAction(e -> {
                        /*if (analysisData == null) {
                                analysisData = AnalysisService.getLsmWTF(sample);
                                analysis.setData(analysisData);
                        }catch(DimensionMismatchException ex){
                                showErrorMessage("Невозможно построить интерполирующую функцию", "Необходимо, чтобы выборка имела" +
                                        "равные интервалы по x. Отчет об ошибке: " + ex.toString());
                        }catch(NonMonotonicSequenceException ex){
                                showErrorMessage("Невозможно построить интерполирующую функцию", "Необходимо, чтобы выборка была" +
                                        "монотонна по x. Отчет об ошибке: " + ex.toString());
                        }catch(NumberIsTooSmallException ex){
                                showErrorMessage("Невозможно построить интерполирующую функцию", "Выборка имеет слишком мальнькие" +
                                        "значения. Отчет об ошибке: " + ex.toString());
                        }catch(Exception ex){
                                showErrorMessage("Непридвиденная ошибка",
                                        "Отчет об ошибке: \n" + ex.toString());
                        }
                        }*/
                               // f = analysisData.getF();
                        //interpolate();
                });

        }

        private void chartInit(){
                lsmLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                lsmLineChart.setTitle("График зависимости мощности от времени. Рассматриваемый день: " +
                        dateFormat.format(new Date(sample.getData().get(0).getDate().longValue())));
                lsmLineChart.setAnimated(false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                lsmXAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
                lsmXAxis.setLabel("Время");
                lsmXAxis.setForceZeroInRange(false);
                lsmYAxis.setAxisTickFormatter(new FixedFormatTickFormatter(new DecimalFormat("#.0000")));
                lsmYAxis.setLabel("Мощность");
                lsmYAxis.setForceZeroInRange(false);

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(sample.getName());
                for (int i = 0; i < sample.getData().size(); ++i) {
                        series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
                }
                lsmLineChart.getData().add(series);
                ChartPanManager panner = new ChartPanManager( lsmLineChart );
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

                JFXChartUtil.setupZooming(lsmLineChart, mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                                mouseEvent.isShortcutDown() ||
                                mouseEvent.getButton() == MouseButton.SECONDARY)
                                mouseEvent.consume();
                });

                JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(lsmLineChart);

                for (XYChart.Data<Number, Number> data : series.getData()) {
                        Node node = data.getNode() ;
                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                        node.setCursor(Cursor.HAND);
                        node.setOnMouseDragged(e -> {
                                if(e.getButton() == MouseButton.PRIMARY) {
                                        Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                                        double xAxisLoc = lsmXAxis.sceneToLocal(pointInScene).getX();
                                        double yAxisLoc = lsmYAxis.sceneToLocal(pointInScene).getY();
                                        Number x = lsmXAxis.getValueForDisplay(xAxisLoc);
                                        Number y = lsmYAxis.getValueForDisplay(yAxisLoc);
                                        data.setXValue(x);
                                        data.setYValue(y);
                                        Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                                }
                        });
                }
        }

        /*private void interpolate() {
                try {
                        double date = new Long(format.parse(lsmField.getText().trim()).getTime()).doubleValue();
                        if (lsmDrawChart.isSelected()) {
                                if (!isLsmDrawn) {
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
                                        lsmLineChart.getData().add(series2);
                                        List<Unit> units = analysisData.getUnits();

                                        for (Unit unit : units) {
                                                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                                                XYChart.Data<Number, Number> data = new XYChart.Data<>(unit.getDate(), unit.getValue());
                                                series.getData().add(data);
                                                series.setName("x = " + format.format(new Date(unit.getDate().longValue())));
                                                lsmLineChart.getData().add(series);
                                                series.getData().get(0).getNode().setCursor(Cursor.HAND);
                                                Tooltip.install(series.getData().get(0).getNode(), new Tooltip('(' + formatView.format(new Date(unit.getDate().longValue())) + "; " + String.format("%.5f", unit.getValue()) + ')'));
                                        }
                                        isLsmDrawn = true;
                                }
                                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                                XYChart.Data<Number, Number> data = new XYChart.Data<>(date, f.value(date));
                                series.getData().add(data);
                                series.setName("x = " + format.format(new Date(data.getXValue().longValue())));
                                lsmLineChart.getData().add(series);
                                series.getData().get(0).getNode().setCursor(Cursor.HAND);
                                Tooltip.install(series.getData().get(0).getNode(), new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                        }
                        lsmResultTextArea.setText(lsmResultTextArea.getText() + "\n" + format.format(date) + "; " + String.format("%.5f", f.value(date)));
                        analysisData.getUnits().add(new Unit(date, f.value(date)));
                        if (analysis.getId() < 0) {
                                analysis.setName("Расчет в базе");
                                dbService.insertAnalysis(analysis);
                                lsmTab.setText(analysis.getName());
                        } else {
                                analysis.setName(analysis.getName() + "+");
                                dbService.updateAnalysis(analysis);
                                lsmTab.setText(analysis.getName());
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
        }*/
}


