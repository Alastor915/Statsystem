package com.statsystem.controller;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.StableTicksAxis;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
    List<Point2D> selection;


    public void initialize(URL location, ResourceBundle resources) {
    }
    public void setSelection(List<Point2D> selection) {
        this.selection = selection;
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
        series.setName("Выборка");
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Выборка 2");
        for (int i = 0; i < selection.size(); ++i) {
            series.getData().add(new XYChart.Data<>(selection.get(i).getX(), selection.get(i).getY()));
            series2.getData().add(new XYChart.Data<>(selection.get(i).getX()/100, selection.get(i).getY()/100));
        }
        lineChart.getData().add(series);
        lineChart.getData().add(series2);

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
