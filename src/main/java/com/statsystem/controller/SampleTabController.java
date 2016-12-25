package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Sample;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.statsystem.utils.Message.showErrorMessage;

/**
 * Created by User on 27.11.2016.
 *
 */
public class SampleTabController implements Initializable {
    private MainController mainController;
    private Sample sample;
    @FXML private Tab newCalc;
    @FXML private TabPane calcTabPane;
    @FXML private Tab sampleTab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button addButton = new Button("+");
        addButton.getStyleClass().add("tab-button");
        addButton.setOnAction(e -> {
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
                newCalcController.setSampleTabController(this);
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
        newCalc.setGraphic(addButton);
        newCalc.getStyleClass().add("tab-button-holder");
        sampleTab.setOnCloseRequest(event -> showDelDialogReq(event));
        sampleTab.setOnClosed(event -> showDelSampleDialog());
    }

    private void showDelDialogReq(Event e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вы уверены, что хотите закрыть вкладку?");
        alert.setHeaderText("Закрытие вкладки");
        alert.setContentText("Вы уверены, что хотите закрыть вкладку?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        } else {
            e.consume();
        }
    }


    public void showDelSampleDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вы хотите удалить эту выборку из базы данных?");
        alert.setHeaderText("Удаление выборки");
        alert.setContentText("Вы хотите удалить эту выборку из базы данных?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                mainController.getDbService().deleteSample(sample);
            } catch (DBException e) {
                showErrorMessage("Ошибка при работе с базой данных", "Ошибка при удалении выборки из базы данных." +
                        " Отчет об ошибке: \n" + e.toString());
            }
        }
    }

    public void showDelAnalysisDialog(Analysis analysis){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вы хотите удалить эти результаты расчета из базы данных?");
        alert.setHeaderText("Удаление результатов расчета");
        alert.setContentText("Вы хотите удалить эти результаты расчета из базы данных?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                mainController.getDbService().deleteAnalysis(analysis);
            } catch (DBException e) {
                showErrorMessage("Ошибка при работе с базой данных", "Ошибка при удалении расчетов из базы данных." +
                        " Отчет об ошибке: \n" + e.toString());
            }
        }
    }

    public void start(){
        Tab tab;
        FXMLLoader loader = new FXMLLoader();
        try {
            tab = loader.load(getClass().getResource("/fxml/sample.fxml").openStream());
            tab.setText("Выборка");
            SampleController controller = loader.getController();
            controller.setDbService(mainController.getDbService());
            controller.setSample(sample);
            controller.setSampleTabController(this);
            controller.start();
            this.getCalcTabPane().getTabs().remove(this.getCalcNew());
            this.getCalcTabPane().getTabs().addAll(tab);
            this.getCalcTabPane().getSelectionModel().selectLast();
            this.getCalcTabPane().getTabs().addAll(this.getCalcNew());
        } catch (IOException ex) {
            showErrorMessage("Ошибка при создании новой вкладки", "Невозможно загрузить fxml форму. Возможно, программа " +
                    "повреждена. Отчет об ошибке: \n" + ex.toString());
        }
        if (!sample.getAnalyses().isEmpty()){
            for (Analysis analysis : sample.getAnalyses()) {
                String fxmlFile = analysis.getType().getPath();
                loader = new FXMLLoader();
                try {
                    tab = loader.load(getClass().getResource(fxmlFile).openStream());
                    tab.setText(analysis.getName());
                    tab.setOnCloseRequest(e -> showDelDialogReq(e));
                    tab.setOnClosed(event -> showDelAnalysisDialog(analysis));
                    CalculationController calcController = loader.getController();
                    calcController.setAnalysis(analysis);
                    calcController.setDbService(mainController.getDbService());
                    calcController.start();
                    this.getCalcTabPane().getTabs().remove(this.getCalcNew());
                    this.getCalcTabPane().getTabs().addAll(tab);
                    this.getCalcTabPane().getSelectionModel().selectLast();
                    this.getCalcTabPane().getTabs().addAll(this.getCalcNew());
                } catch (IOException ex) {
                    showErrorMessage("Ошибка при создании новой вкладки", "Невозможно загрузить fxml форму. Возможно, программа " +
                            "повреждена. Отчет об ошибке: \n" + ex.toString());
                }
            }
        }
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public TabPane getCalcTabPane(){
        return calcTabPane;
    }

    public Tab getCalcNew(){
        return newCalc;
    }
}
