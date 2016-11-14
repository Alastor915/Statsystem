/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

import DataBase.Entities.T_SELECTION_Entity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface SelectionDAO {
    public void addSelection(T_SELECTION_Entity selection) throws SQLException;
    public void updateSelection(T_SELECTION_Entity selection) throws SQLException;
    public T_SELECTION_Entity getSelectionById(Integer id) throws SQLException;
    public List getAllSelections() throws SQLException;
    public void deleteSelection(T_SELECTION_Entity selection) throws SQLException;
}
