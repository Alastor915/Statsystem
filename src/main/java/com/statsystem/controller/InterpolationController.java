package com.statsystem.controller;

import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
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
import java.text.SimpleDateFormat;
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
    MainController mainController;
    private static String DEFULT_X_FIELD_VALUE = "08.04.2013 21:19:14";
    Sample sample;
    Unit result;
    double defTickXAxis = 0;
    int defPointsCount = 3000;
    DateFormat format;
    int temp = 0;
    int temp2 = 0;
    UnivariateFunction f;

    public void initialize(URL location, ResourceBundle resources) {
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        xField.setText(DEFULT_X_FIELD_VALUE);
    }
    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public void setResult(Unit result) {
        this.result = result;
    }

    public void setMainController(MainController controller) {
        mainController = controller;

    }
    public void start() {
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setTitle("График");
        lineChart.setAnimated(false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
        xAxis.setAxisTickFormatter(new FixedFormatTickFormatter(simpleDateFormat));
        xAxis.setLabel("Параметр X");
        xAxis.setForceZeroInRange(false);
        yAxis.setLabel("Параметр Y");
        yAxis.setForceZeroInRange(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(sample.getName());
        for (int i = 0; i < sample.getData().size(); ++i) {
            series.getData().add(new XYChart.Data<>(sample.getData().get(i).getDate(), sample.getData().get(i).getValue()));
        }
        lineChart.getData().add(series);
        //interpolate();
//        series2.getData().add(new XYChart.Data<>(result.getDate(),result.getValue()));
//        lineChart.getData().add(series2);
        defTickXAxis = xAxis.getTickLength();
        ChartPanManager panner = new ChartPanManager( lineChart );
        panner.setMouseFilter(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY ||
                        (mouseEvent.getButton() == MouseButton.PRIMARY &&
                                mouseEvent.isShortcutDown())) {
                    //let it through
                } else {
                    mouseEvent.consume();
                }
            }
        });
        panner.start();

        //Zooming works only via primary mouse button without ctrl held down
       JFXChartUtil.setupZooming(lineChart, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY ||
                        mouseEvent.isShortcutDown() ||
                        mouseEvent.getButton() == MouseButton.SECONDARY)
                    mouseEvent.consume();
            }
        });
        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(lineChart);

        for (Data<Number, Number> data : series.getData()) {
            Node node = data.getNode() ;
            Tooltip.install(node, new Tooltip('(' + format.format(new Date(data.getXValue().longValue())) + ';' + String.format("%.3f", data.getYValue()) + ')'));
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(e -> {
                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
                data.setXValue(x);
                data.setYValue(y);
                Tooltip.install(node, new Tooltip('(' + format.format(new Date(data.getXValue().longValue())) + ';' + String.format("%.3f", data.getYValue()) + ')'));
            });
        }
        calcBtn.setOnAction(e -> {
            interpolate();
        });
    }
    public void interpolate() {
        try {
            double date = new Long(format.parse(xField.getText().trim()).getTime()).doubleValue();
            if (temp2 == 0) {
                f = NewtonInterpolation.interpolite(sample, 0);
                ++temp2;
            }
            if (drawChart.isSelected()) {
                if (temp == 0) {
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
                    ++temp;
                }
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                XYChart.Data<Number, Number> data = new XYChart.Data<>(date, f.value(date));
                series.getData().add(data);
                series.setName("x = " + format.format(new Date(data.getXValue().longValue())));
                lineChart.getData().add(series);
                series.getData().get(0).getNode().setCursor(Cursor.HAND);
                Tooltip.install(series.getData().get(0).getNode(), new Tooltip('(' + format.format(new Date(data.getXValue().longValue())) + ';' + String.format("%.3f", data.getYValue()) + ')'));
            }
            resultTextArea.setText(resultTextArea.getText() + "\n" + format.format(date) + "         " + String.format("%.3f", f.value(date)));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
