/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

import DataBase.Entities.T_DATA_Entity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface DataDAO {
    public void addData(T_DATA_Entity data) throws SQLException;
    public void updateData(T_DATA_Entity data) throws SQLException;
    public T_DATA_Entity getDataById(Integer id) throws SQLException;
    public List getAllData() throws SQLException;
    public void deleteProject(T_DATA_Entity data) throws SQLException;
}
