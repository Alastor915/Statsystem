/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.DAO.impl;

/**
 *
 * @author sereg
 */


import com.statsystem.dbservice.DAO.ProjectDAO;
import com.statsystem.entity.Project;
import com.statsystem.dbservice.execute.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.*;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ProjectDAOImpl implements ProjectDAO {

    public void addProject(Project project) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(project);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateProject(Project project) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(project);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public Project getProjectById(long id) throws SQLException, LazyInitializationException {
        Session session = null;
        Project project = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            project = (Project) session.createCriteria(Project.class).list().get((int) (id - 1));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException, LazyInitializationException {
        Session session = null;
        List<Project> projects = new ArrayList<Project>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projects = session.createCriteria(Project.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return projects;
    }

    public void deleteProject(Project project) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(project);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }
}