/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

import DataBase.Entities.T_PROJECT_PARAMETER_Entity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface ProjectParameterDAO {
    public void addParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException;
    public void updateParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException;
    public T_PROJECT_PARAMETER_Entity getParameterById(Integer id) throws SQLException;
    public List getAllParameters() throws SQLException;
    public void deleteParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException;
}
