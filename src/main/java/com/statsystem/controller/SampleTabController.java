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
    @FXML private InterpolationController interpolationController;
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

        //Удаление вкладки при нажатии ПКМ
        sampleTab.setOnClosed(event -> showDelDialog(sampleTab));
    }

    public void showDelDialog(Tab sampleTab){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Удаление выборки");
        alert.setContentText("Вы уверены, что хотите удалить эту выборку?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                mainController.getDbService().deleteSample(sample);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        interpolationController.setMainController(mainController);
        interpolationController.setDbService(mainController.getDbService());
        Analysis analysis;
        if (sample.getAnalyses().isEmpty()){
            analysis = new Analysis(-1L, "Расчет", AnalysisType.SPLINE, null, sample);
        } else {
            analysis = sample.getAnalyses().get(0);
        }
        interpolationController.setAnalysis(analysis);
        interpolationController.start();
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
