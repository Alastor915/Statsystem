/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.DAO;

/**
 *
 * @author sereg
 */

import com.statsystem.entity.Project;
import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO {
    void addProject(Project project) throws SQLException;
    void updateProject(Project project) throws SQLException;
    Project getProjectById(long id) throws SQLException;
    List<Project> getAllProjects() throws SQLException;
    void deleteProject(Project project) throws SQLException;
}
