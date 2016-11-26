package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.ProjectDAO;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class ProjectDAOImpl implements ProjectDAO{

    private Session session;

    public ProjectDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public long insertProject(String name) throws HibernateException {
        return (Long) session.save(new Project(name));
    }

    @Override
    public void updateProject(Project project) throws HibernateException {

    }

    @Override
    public Project getProject(long id) throws HibernateException {
        Project project = (Project) session.get(Project.class, id);
        if (project != null) {
            List<Sample> samples = project.getSamples();
            if (samples.isEmpty()) {
                samples.size();
                for (Sample next : samples) {
                    List<Analysis> analyses = next.getAnalyses();
                    if (!analyses.isEmpty()) {
                        analyses.size();
                    }
                }
            }
        }
        return project;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> getAllProjects() throws HibernateException {
        return session.createCriteria(Project.class).list();
    }

    @Override
    public void deleteProject(Project project) throws HibernateException {

    }
}