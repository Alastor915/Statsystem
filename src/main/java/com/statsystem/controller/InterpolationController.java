package com.statsystem.controller;

import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import com.statsystem.entity.impl.NewtonAnalysisData;
import com.statsystem.logic.interpolation.NewtonInterpolation;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.FixedFormatTickFormatter;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by User on 10.11.2016.
 */
public class InterpolationController implements Initializable {
    @FXML SplitPane splitPane;
    @FXML LineChart<Number,Number> lineChart;
    @FXML StableTicksAxis xAxis;
    @FXML StableTicksAxis yAxis;
    @FXML CheckBox drawChart;
    @FXML Button calcBtn;
    @FXML TextField xField;
    @FXML TextArea resultTextArea;
    private MainController mainController;
    private static String DEFULT_X_FIELD_VALUE = "08.04.2013 21:19:14";
    private Sample sample;
    private DateFormat format;
    private DateFormat formatView;
    private boolean isInterpolationDrawn = false;
    private UnivariateFunction f;

    public void initialize(URL location, ResourceBundle resources) {
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        formatView = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        xField.setText(DEFULT_X_FIELD_VALUE);
        resultTextArea.setEditable(false);
    }
    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public void setMainController(MainController controller) {
        mainController = controller;

    }
    public void start() {
        f =  NewtonInterpolation.interpolite(sample).getF();
        chartInit();
        calcBtn.setOnAction(e -> {
            interpolate();
        });
    }
    public void interpolate() {
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
        }
        catch (Exception ex){
            ex.printStackTrace();
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
                    double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                    double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                    Number x = xAxis.getValueForDisplay(xAxisLoc);
                    Number y = yAxis.getValueForDisplay(yAxisLoc);
                    data.setXValue(x);
                    data.setYValue(y);
                    Tooltip.install(node, new Tooltip('(' + formatView.format(new Date(data.getXValue().longValue())) + "; " + String.format("%.5f", data.getYValue()) + ')'));
                }
            });
        }
    }

}
