/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO.Impl;

import DataBase.DAO.DataDAO;
import DataBase.Entities.T_DATA_Entity;
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
public class DataDAOImpl implements DataDAO{

    public void addData(T_DATA_Entity data) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(data);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateData(T_DATA_Entity data) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(data);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public T_DATA_Entity getDataById(Integer id) throws SQLException {
        Session session = null;
        T_DATA_Entity data = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            data =(T_DATA_Entity) session.load(T_DATA_Entity.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return data;
    }

    public List getAllData() throws SQLException {
        Session session = null;
        List<T_DATA_Entity> datas = new ArrayList<T_DATA_Entity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            datas = session.createCriteria(T_DATA_Entity.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return datas;
    }

    public void deleteProject(T_DATA_Entity data) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(data);
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
