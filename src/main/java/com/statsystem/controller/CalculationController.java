package com.statsystem.controller;

import com.statsystem.dbservice.execute.DBService;
import com.statsystem.entity.Analysis;

/**
 * Created by User on 14.12.2016.
 */
public interface CalculationController {
    void setAnalysis(Analysis analysis);
    void setMainController(MainController controller);
    void setDbService(DBService dbService);
    void start();
}
