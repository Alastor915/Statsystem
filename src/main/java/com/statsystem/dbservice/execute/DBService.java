package com.statsystem.dbservice.execute;

import com.statsystem.entity.Project;

import java.util.List;

public interface DBService {
    Project getProject(long id) throws DBException;
    List<Project> getAllProjects() throws DBException;
}