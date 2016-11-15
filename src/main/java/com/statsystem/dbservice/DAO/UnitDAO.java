/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbService.DAO;

import com.statsystem.entity.Unit;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface UnitDAO {
    void addUnit(Unit unit) throws SQLException;
    void updateUnit(Unit unit) throws SQLException;
    Unit getUnitById(Integer id) throws SQLException;
    List getAllUnits() throws SQLException;
    void deleteUnit(Unit unit) throws SQLException;
}
