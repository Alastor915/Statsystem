/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

/**
 *
 * @author sereg
 */

import DataBase.Entities.T_PROJECT_Entity;
import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO {
    public void addProject(T_PROJECT_Entity project) throws SQLException;
    public void updateProject(T_PROJECT_Entity project) throws SQLException;
    public T_PROJECT_Entity getProjectById(Integer id) throws SQLException;
    public List getAllProjects() throws SQLException;
    public void deleteProject(T_PROJECT_Entity project) throws SQLException;
}
