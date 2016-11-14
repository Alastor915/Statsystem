/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO.Impl;

/**
 *
 * @author sereg
 */

import DataBase.DAO.ProjectDAO;
import DataBase.Entities.T_PROJECT_Entity;
import DataBase.Hiber.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class ProjectDAOImpl implements ProjectDAO{

    public void addProject(T_PROJECT_Entity project) throws SQLException {
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

    public void updateProject(T_PROJECT_Entity project) throws SQLException {
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

    public T_PROJECT_Entity getProjectById(Integer id) throws SQLException {
        Session session = null;
        T_PROJECT_Entity project = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            project =(T_PROJECT_Entity) session.load(T_PROJECT_Entity.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return project;
    }

    public List<T_PROJECT_Entity> getAllProjects() throws SQLException {
        Session session = null;
        List<T_PROJECT_Entity> projects = new ArrayList<T_PROJECT_Entity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projects = session.createCriteria(T_PROJECT_Entity.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return projects;
    }

    public void deleteProject(T_PROJECT_Entity project) throws SQLException {
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