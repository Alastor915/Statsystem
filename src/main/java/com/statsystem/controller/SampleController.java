package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.*;
import com.statsystem.entity.impl.NewtonAnalysisData;
import com.statsystem.entity.impl.SplineAnalysisData;
import com.statsystem.logic.AnalysisService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.statsystem.utils.Message.showErrorMessage;
import static com.statsystem.utils.Message.showInfoMessage;

/**
 * Created by User on 10.11.2016.
 *
 */
public class SampleController implements Initializable{
    @FXML SplitPane splitPane;
    @FXML LineChart<Number,Number> lineChart;
    @FXML StableTicksAxis xAxis;
    @FXML StableTicksAxis yAxis;
    @FXML CheckBox drawChart;
    @FXML Button newCalcBtn;
    @FXML Tab tab;
    private MainController mainController;
    private Sample sample;
    private DateFormat format;
    private DateFormat formatView;
    private DBService dbService;
    private SampleTabController sampleTabController;

    public void initialize(URL location, ResourceBundle resources) {
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public void setMainController(MainController controller) {
        mainController = controller;
    }

    public void setSampleTabController(SampleTabController sampleTabController) {
        this.sampleTabController = sampleTabController;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public void start() {
        chartInit();
        newCalcBtn.setOnAction(e -> {
            try {
                //if (newCalc.isSelected()) {
                String fxmlFile = "/fxml/choice_type_calc_dialog.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
                NewCalcController newCalcController = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.initOwner(m_stage);
                stage.setTitle("Система обработки данных");
                stage.setScene(new Scene(root));
                newCalcController.setM_stage(stage);
                newCalcController.setSampleTabController(sampleTabController);
                stage.showAndWait();
                //}
            } catch (IOException ex){
                showErrorMessage("Ошибка при создании диалогового окна", "Невозможно загрузить fxml форму. Возможно, программа " +
                        "повреждена. Отчет об ошибке: \n" + ex.toString());
            }  catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }
        });
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
            series.getData().add(new Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
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
                Unit unit = sample.getUnitByDate((Double) data.getXValue());
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
