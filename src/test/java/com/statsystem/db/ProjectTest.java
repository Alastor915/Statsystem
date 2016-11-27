package com.statsystem.db;

import com.statsystem.controller.MainController;
import com.statsystem.dbservice.execute.DBException;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.DBServiceImpl;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ProjectTest extends Assert {

    private static DBService dbService;
    private static Project project;
    private static Project project2;
    private static Project project3;
    private static Project project4;

    @BeforeClass
    public static void initialize() throws DBException{
        dbService = new DBServiceImpl(true, "create-drop");
        project = new Project("Первый проект");
        Sample sample = new Sample("Первая выборка");
        sample.addUnit(new Unit(111d, 222d));
        sample.addUnit(new Unit(333d, 444d));
        project.addSample(sample);
        dbService.insertProject(project);
        project2 = new Project("Пустой проект");
        dbService.insertProject(project2);
        project3 = new Project("Пустой проект");
        dbService.insertProject(project3);
        project4 = new Project("Пустой проект");
        dbService.insertProject(project4);
    }

    @Test
    public void testGetProject() throws DBException {
        Project projectFromDB =  dbService.getProject(project.getId());
        assertFalse(projectFromDB == null);
        assertEquals(projectFromDB.getName(), "Первый проект");
    }

    @Test
    public void testGetAllProjects() throws DBException {
        List<Project> projects =  dbService.getAllProjects();
        assertFalse(projects.isEmpty());
        assertFalse(projects.isEmpty());
        assertFalse(projects.indexOf(project3) == -1);
        assertEquals(projects.get(projects.indexOf(project3)).getName(), "Пустой проект");
        assertFalse(projects.indexOf(project4) == -1);
        assertEquals(projects.get(projects.indexOf(project4)).getName(), "Пустой проект");
    }

    @Test
    public void testGetProjectWithDetails() throws DBException {
        Project projectFromDB =  dbService.getProject(project.getId());
        assertFalse(projectFromDB == null);
        assertEquals(projectFromDB.getName(), "Первый проект");

        assertFalse(projectFromDB.getSamples().isEmpty());
        Sample sample = projectFromDB.getSamples().get(0);
        assertEquals(sample.getName(), "Первая выборка");

        assertFalse(sample.getData().isEmpty());
        Unit unit = sample.getData().get(0);
        assertEquals(unit.getDate(), new Double(111));
        assertEquals(unit.getValue(), new Double(222));

        assertFalse(sample.getData().size() != 2);
        unit = sample.getData().get(1);
        assertEquals(unit.getDate(), new Double(333));
        assertEquals(unit.getValue(), new Double(444));
    }

    @Test
        public void testInsertProject() throws DBException {
        Project project = new Project("Хардкод");
        Sample sample = MainController.hardcode();
        project.addSample(sample);
        project.addSample(MainController.hardcode());
        long id = dbService.insertProject(project);

        Project projectFromDB =  dbService.getProject(id);
        assertFalse(projectFromDB == null);
        assertEquals(project.getName(), projectFromDB.getName());

        assertFalse(projectFromDB.getSamples().isEmpty());
        assertEquals(project.getSamples().size(), projectFromDB.getSamples().size());
        for (Sample sampleFromDB: projectFromDB.getSamples()) {
            assertEquals(sample.getName(), sampleFromDB.getName());
            assertFalse(sampleFromDB.getData().isEmpty());
            for (int i = 0; i < sampleFromDB.getData().size(); i++){
                assertFalse(sampleFromDB.getData().get(i) == null);
                Unit unit = sampleFromDB.getData().get(i);
                assertEquals(sample.getData().get(i).getDate(), unit.getDate());
                assertEquals(sample.getData().get(i).getValue(), unit.getValue());
            }
        }
    }

    @Test
    public void testDeleteProject() throws DBException {
        dbService.deleteProject(project);
        Project projectFromDB =  dbService.getProject(project.getId());
        assertFalse(projectFromDB != null);
    }

    @Test
    public void testUpdateProject() throws DBException {
        Sample sample = new Sample("Новая выборка");
        sample.addUnit(new Unit(111d, 222d));
        sample.addUnit(new Unit(333d, 444d));
        project2.addSample(sample);
        dbService.insertSample(sample);
        project2.setName("Вот тока заполненный проект");
        dbService.updateProject(project2);
        Project projectFromDB =  dbService.getProject(project2.getId());
        assertFalse(projectFromDB == null);
        assertEquals(projectFromDB.getName(), "Вот тока заполненный проект");

        assertFalse(projectFromDB.getSamples().isEmpty());
        Sample sampleFromDB = projectFromDB.getSamples().get(0);
        assertEquals(sampleFromDB.getName(), "Новая выборка");

        assertFalse(sampleFromDB.getData().isEmpty());
        Unit unit = sampleFromDB.getData().get(0);
        assertEquals(unit.getDate(), new Double(111));
        assertEquals(unit.getValue(), new Double(222));

        assertFalse(sampleFromDB.getData().size() != 2);
        unit = sampleFromDB.getData().get(1);
        assertEquals(unit.getDate(), new Double(333));
        assertEquals(unit.getValue(), new Double(444));
    }
}
