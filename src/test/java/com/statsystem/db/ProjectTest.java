package com.statsystem.db;

import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.DBServiceImpl;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ProjectTest extends Assert {

    private static DBService dbService;

    @BeforeClass
    public static void initialize() {
        dbService = new DBServiceImpl(true, "validate");
    }



    @Test
    public void testGetProject() throws DBException {
        Project project =  dbService.getProject(1L);
        assertFalse(project == null);
        assertEquals(project.getName(), "Первый проект");
    }

    @Test
    public void testGetAllProjects() throws DBException {
        List<Project> projects =  dbService.getAllProjects();
        assertFalse(projects.isEmpty());
        assertFalse(projects.get(0) == null);
        assertEquals(projects.get(0).getName(), "Первый проект");
        assertFalse(projects.get(1) == null);
        assertEquals(projects.get(1).getName(), "Пустой проект");
        assertFalse(projects.get(2) == null);
        assertEquals(projects.get(2).getName(), "Пустой проект");
    }

    @Test
    public void testGetProjectWithDetails() throws DBException {
        Project project =  dbService.getProject(1L);
        assertFalse(project == null);
        assertEquals(project.getName(), "Первый проект");

        assertFalse(project.getSamples().isEmpty());
        assertFalse(project.getSamples().get(0) == null);
        Sample sample = project.getSamples().get(0);
        assertEquals(sample.getName(), "Первая выборка");

        assertFalse(sample.getData().isEmpty());
        assertFalse(sample.getData().get(0) == null);
        Unit unit = sample.getData().get(0);
        assertEquals(unit.getDate(), new Double(111));
        assertEquals(unit.getValue(), new Double(222));

        assertFalse(sample.getData().get(1) == null);
        unit = sample.getData().get(1);
        assertEquals(unit.getDate(), new Double(333));
        assertEquals(unit.getValue(), new Double(444));
    }
}
