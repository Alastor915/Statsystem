/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.DAO;

import DataBase.DAO.Impl.*;

/**
 *
 * @author sereg
 */
public class Data {
    
    private static ProjectDAO projectDAO = null;
    private static AnalysisTypeDAO typeDAO = null;
    private static DataDAO dataDAO = null;
    private static ProjectParameterDAO parameterDAO = null;
    private static SelectionDAO selectionDAO = null;
    private static Data instance = null;

    public static synchronized Data getInstance(){
        if (instance == null)
            instance = new Data();
        return instance;
    }
    
    public ProjectDAO getProjectDAO(){
        if (projectDAO == null)
            projectDAO = new ProjectDAOImpl();
        return projectDAO;
    }
    
    private AnalysisTypeDAO getAnalysisTypeDAO(){
        if (typeDAO == null)
            typeDAO = new AnalysisTypeDAOImpl();
        return typeDAO;
    }
    
    private DataDAO getDataDAO(){
        if (dataDAO == null)
            dataDAO = new DataDAOImpl();
        return dataDAO;
    }
    
    private ProjectParameterDAO getProjectParameterDAO(){
        if (parameterDAO == null)
            parameterDAO = new ProjectParameterDAOImpl();
        return parameterDAO;
    }
    
    private SelectionDAO getSelectionDAO(){
        if (selectionDAO == null)
            selectionDAO = new SelectionDAOImpl();
        return selectionDAO;
    }

}