/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO.Impl;

import DataBase.DAO.AnalysisTypeDAO;
import DataBase.Entities.T_ANALYSIS_TYPE_Entity;
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
public class AnalysisTypeDAOImpl implements AnalysisTypeDAO{

    public void addAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(type);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }    

    public void updateAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(type);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public T_ANALYSIS_TYPE_Entity getAnalysisTypeById(Integer id) throws SQLException {
        Session session = null;
        T_ANALYSIS_TYPE_Entity type = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            type =(T_ANALYSIS_TYPE_Entity) session.load(T_ANALYSIS_TYPE_Entity.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return type;
    }

    public List getAllAnalysisTypes() throws SQLException {
        Session session = null;
        List<T_ANALYSIS_TYPE_Entity> types = new ArrayList<T_ANALYSIS_TYPE_Entity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            types = session.createCriteria(T_ANALYSIS_TYPE_Entity.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return types;
    }

    public void deleteAnalysisType(T_ANALYSIS_TYPE_Entity type) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(type);
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
