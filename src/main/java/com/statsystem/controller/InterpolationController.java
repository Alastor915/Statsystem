package com.statsystem.controller;

import com.statsystem.entity.Sample;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
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
    MainController mainController;
    private Sample sample;


    public void initialize(URL location, ResourceBundle resources) {
    }
    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public void setMainController(MainController controller) {
        mainController = controller;

    }
    public void start() {
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setTitle("График");
        lineChart.setAnimated(false);


        xAxis.setLabel("Параметр X");
        yAxis.setLabel("Параметр Y");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(sample.getName());
        for (int i = 0; i < sample.getSize(); ++i) {
            series.getData().add(new XYChart.Data<>((double) sample.getData().get(i).getKey().getTime(), sample.getData().get(i).getValue()));
        }
        lineChart.getData().add(series);

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
                if (mouseEvent.getButton() != MouseButton.PRIMARY ||
                        mouseEvent.isShortcutDown())
                    mouseEvent.consume();
            }
        });

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(lineChart);

        for (Data<Number, Number> data : series.getData()) {
            Node node = data.getNode() ;
            Tooltip.install(node, new Tooltip('(' + String.format("%.2f", data.getXValue()) + ';' + String.format("%.2f", data.getYValue()) + ')'));
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(e -> {
                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
                data.setXValue(x);
                data.setYValue(y);
                Tooltip.install(node, new Tooltip('(' + String.format("%.2f", data.getXValue()) + ';' + String.format("%.2f", data.getYValue()) + ')'));
            });
        }
    }

}
