package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Project;
import org.hibernate.HibernateException;

import java.util.List;

public interface ProjectDAO {
    long insertProject(Project project) throws HibernateException;
    void updateProject(Project project) throws HibernateException;
    Project getProject(long id) throws HibernateException;
    List<Project> getAllProjects() throws HibernateException;
    void deleteProject(Project project) throws HibernateException;
}
