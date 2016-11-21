/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Analysis;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface AnalysisDAO {
    void addAnalysis(Analysis analysis) throws SQLException;
    void updateAnalysis(Analysis analysis) throws SQLException;
    Analysis getAnalysisById(Integer id) throws SQLException;
    List<Analysis> getAllAnalyses() throws SQLException;
    void deleteAnalysis(Analysis analysis) throws SQLException;
}
