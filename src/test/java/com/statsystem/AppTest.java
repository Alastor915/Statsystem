package com.statsystem;

import com.statsystem.dbService.execute.DBService;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testALLDATABASENOOOW()
    {
        DBService dbService = DBService.getInstance();
        Project project = new Project("Первый проект");
        Sample sample = new Sample("Супервыборка");
        sample.getData().add(new Unit(new Date(System.currentTimeMillis()),2.345));
        project.getSamples().add(sample);
        List<Project> projects = null;
        try {
            dbService.getProjectDAO().addProject(project);
            projects = dbService.getProjectDAO().getAllProjects();
        } catch (SQLException e) {
            assertTrue( false );
        }
        assertEquals( projects.get(0).getName(), "Первый проект");
        assertEquals( projects.get(0).getSamples().get(0).getName(), "Супервыборка");
    }
}