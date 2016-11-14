/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO.Impl;

import DataBase.DAO.SelectionDAO;
import DataBase.Entities.T_SELECTION_Entity;
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
public class SelectionDAOImpl implements SelectionDAO{

    public void addSelection(T_SELECTION_Entity selection) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(selection);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateSelection(T_SELECTION_Entity selection) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(selection);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public T_SELECTION_Entity getSelectionById(Integer id) throws SQLException {
        Session session = null;
        T_SELECTION_Entity selection = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            selection =(T_SELECTION_Entity) session.load(T_SELECTION_Entity.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return selection;
    }

    public List getAllSelections() throws SQLException {
        Session session = null;
        List<T_SELECTION_Entity> selections = new ArrayList<T_SELECTION_Entity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            selections = session.createCriteria(T_SELECTION_Entity.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return selections;
    }

    public void deleteSelection(T_SELECTION_Entity selection) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(selection);
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
