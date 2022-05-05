package tests.java;

import main.java.presenter.NotesPresenter;
import main.java.presenter.NotesPresenterImpl;
import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.utils.WaitSimulator;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;
import org.junit.Before;

import java.util.Date;


import static org.junit.Assert.assertEquals;

public class EjGTests {

    NotesModel model;
    NotesPresenter controller;
    NoteEditorView noteEditorView;
    NoteListerView noteListerView;
    Date fixedDate;

    @Before
    public void setUp() throws Exception {
        WaitSimulator.timeBase = 0;

        model = new NotesModelImpl();
        fixedDate = new Date();
        model.setDateManager(new StubbedDateManager(fixedDate));
        model.setNotesRepository(new NonPersistentNotesRepository());

        controller = new NotesPresenterImpl(model);
        noteEditorView = new NoteEditorViewImpl(controller, model);
        noteListerView = new NoteListerViewImpl(controller, model);
        controller.setNoteEditorView(noteEditorView);
        controller.setNoteListerView(noteListerView);
    }
/*
    @Test(timeout = 500)
    public void testSimpleStorage() throws InterruptedException {
        controller.onEventUpdate("Notin", "ouch!");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
    }

    @Test(timeout = 500)
    public void testSimpleUpdate() throws InterruptedException {
        controller.onEventUpdate("Nota 1", "da text");
        waitForControllerTask();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        controller.onEventUpdate("Nota 1", "da text was changed");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
    }


    @Test(timeout = 500)
    public void testShowSelection() throws InterruptedException {
        controller.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForControllerTask();
        controller.onEventSelectedNoteTitle("No me elijas!");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate),
                noteEditorView.getUpdateText());
    }

    @Test(timeout = 500)
    public void testShowUpdateAndSelectOld() throws InterruptedException {
        controller.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForControllerTask();
        controller.onEventUpdate("Elegime!", "por favor :D");
        waitForControllerTask();
        controller.onEventSelectedNoteTitle("No me elijas!");
        waitForControllerTask();
        assertEquals("lo hiciste :(", noteEditorView.getTextContent());
    }

    @Test(timeout = 500)
    public void testShowSelectUpdatedNoteAfterAdditions() throws InterruptedException {
        Date oldDate = fixedDate;
        controller.onEventUpdate("No me cambies", "porfis");
        waitForControllerTask();
        controller.onEventUpdate("Nota del Medio", "Gutierrez");
        waitForControllerTask();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        controller.onEventUpdate("No me cambies", "lo hiciste nomas...");
        waitForControllerTask();
        controller.onEventUpdate("Nota del Final", "Gutierrez");
        waitForControllerTask();
        controller.onEventSelectedNoteTitle("No me cambies");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
    }

    private void waitForControllerTask() throws InterruptedException{
        while(controller.isActivellyWorking()) Thread.sleep(1);
    }
*/
}
