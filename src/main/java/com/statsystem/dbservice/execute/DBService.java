package com.statsystem.dbservice.execute;

import com.statsystem.entity.Analysis;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;

import java.util.List;

public interface DBService {
    Project getProject(long id) throws DBException;
    List<Project> getAllProjects() throws DBException;
    long insertProject(Project project) throws DBException;
    long insertSample(Sample sample) throws DBException;
    long insertAnalysis(Analysis analysis) throws DBException;
    void updateProject(Project project) throws DBException;
    void updateSample(Sample sample) throws DBException;
    void updateAnalysis(Analysis analysis) throws DBException;
    void updateUnit(Unit unit) throws DBException;
    void deleteProject(Project project) throws DBException;
    void deleteSample(Sample sample) throws DBException;
    void deleteAnalysis(Analysis analysis) throws DBException;
}