/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

import DataBase.Entities.T_ANALYSIS_TYPE_Entity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface AnalysisTypeDAO {
    public void addAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException;
    public void updateAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException;
    public T_ANALYSIS_TYPE_Entity getAnalysisTypeById(Integer id) throws SQLException;
    public List getAllAnalysisTypes() throws SQLException;
    public void deleteAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException;
}
