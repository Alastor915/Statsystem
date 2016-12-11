package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.utils.Parser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.POIXMLException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import static com.statsystem.utils.ErrorMessage.showErrorMessage;

/**
 * Created by User on 20.11.2016.
 *
 */
public class CreateProjectController implements Initializable {
    @FXML Button chooseBtn;
    @FXML TextField pathField;
    @FXML TextField projectName;
    @FXML Button cancelBtn;
    @FXML Button okBtn;
    private FileChooser fileChooser;
    private Stage m_stage;
    private MainController mainController;
    private DBService dbService;


    public Stage getM_stage() {
        return m_stage;
    }

    public void setM_stage(Stage m_stage) {
        this.m_stage = m_stage;
    }
    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathField.setEditable(false);
        fileChooser = new FileChooser();
        chooseBtn.setOnAction(e->{
            File file = fileChooser.showOpenDialog(m_stage);
            if (file != null)
                pathField.setText(file.getAbsolutePath());
        });
        cancelBtn.setOnAction(e-> m_stage.close());
        okBtn.setOnAction(e-> {
            try {
                if (pathField.getText() != null && !pathField.getText().trim().isEmpty()) {
                    if (!projectName.getText().trim().isEmpty()) {
                        List<Sample> samples = Parser.parse(pathField.getText().trim());
                        Project project = new Project(projectName.getText().trim());
                        project.setSamples(samples);
                        dbService.insertProject(project);
                        mainController.setProject(project);
                        mainController.loadXLSXSamples(samples);
                        mainController.getM_stage().setTitle("Система обработки данных - " + project.getName());
                        m_stage.close();
                    }
                    else {
                        projectName.setPromptText("Введите название проекта");
                    }
                }
            } catch (DBException ex){
                showErrorMessage("Ошибка при работе с базой данных", "Ошибка при сохранении нового проекта в базу данных." +
                        " Отчет об ошибке: \n" + ex.toString());
            } catch (IOException ex){
                showErrorMessage("Ошибка при работе с файлом", "Ошибка при загрузке данных из файла " + pathField.getText()
                        + ". Отчет об ошибке: \n" + ex.toString());
            } catch (ParseException ex){
                showErrorMessage("Ошибка при работе с содержимым файла " + pathField.getText(),
                        "Ошибка при работе с содержимым файла " + pathField.getText() + ". Убедитесь, что файл " +
                                "соответсвует требуемому формату(см. Справка, Создание проекта). Отчет об ошибке: \n"
                                + ex.toString());
            } catch (POIXMLException ex){
                showErrorMessage("Неверный формат файла",
                        "Отчет об ошибке: \n" + ex.toString());
            } catch (Exception ex){
                showErrorMessage("Непридвиденная ошибка",
                        "Отчет об ошибке: \n" + ex.toString());
            }
        });
    }
}
