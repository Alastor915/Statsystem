/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Sample;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sereg
 */
public interface SampleDAO {
    void addSample(Sample sample) throws SQLException;
    void updateSample(Sample sample) throws SQLException;
    Sample getSampleById(Integer id) throws SQLException;
    List getAllSamples() throws SQLException;
    void deleteSample(Sample sample) throws SQLException;
}
