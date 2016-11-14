/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbService.DAO.impl;

/**
 *
 * @author sereg
 */


import com.statsystem.dbService.DAO.ProjectDAO;
import com.statsystem.entity.Project;
import com.statsystem.dbService.execute.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

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

    public Project getProjectById(Integer id) throws SQLException {
        Session session = null;
        Project project = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            project =(Project) session.load(Project.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
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