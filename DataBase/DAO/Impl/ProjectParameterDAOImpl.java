/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO.Impl;

import DataBase.DAO.ProjectParameterDAO;
import DataBase.Entities.T_PROJECT_PARAMETER_Entity;
import DataBase.Hiber.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author sereg
 */
public class ProjectParameterDAOImpl implements ProjectParameterDAO{

    public void addParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException {
       Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(parameter);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(parameter);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public T_PROJECT_PARAMETER_Entity getParameterById(Integer id) throws SQLException {
        Session session = null;
        T_PROJECT_PARAMETER_Entity parameter = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            parameter =(T_PROJECT_PARAMETER_Entity) session.load(T_PROJECT_PARAMETER_Entity.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return parameter;
    }

    public List getAllParameters() throws SQLException {
        Session session = null;
        List<T_PROJECT_PARAMETER_Entity> parameters = new ArrayList<T_PROJECT_PARAMETER_Entity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            parameters = session.createCriteria(T_PROJECT_PARAMETER_Entity.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return parameters;
    }

    public void deleteParameter(T_PROJECT_PARAMETER_Entity parameter) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(parameter);
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
