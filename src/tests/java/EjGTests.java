package tests.java;

import main.java.model.Note;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.presenter.NotesEditorPresenter;
import main.java.presenter.NotesEditorPresenterImpl;
import main.java.presenter.NotesListerPresenter;
import main.java.model.NotesModel;
import main.java.presenter.NotesListerPresenterImpl;
import main.java.utils.CurrentDateManager;
import main.java.utils.DateManager;
import main.java.utils.WaitSimulator;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;


import static org.junit.Assert.assertEquals;

public class EjGTests {

    NotesModel model;
    NotesEditorPresenter notesEditorPresenter;
    NotesListerPresenter notesListerPresenter;
    NoteEditorView noteEditorView;
    NoteListerView noteListerView;
    Date fixedDate;
    DateManager date;
    @Before
    public void setUp() throws Exception {
        WaitSimulator.timeBase = 0;

        model = new NotesModelImpl();
        fixedDate = new Date();
        model.setDateManager(new StubbedDateManager(fixedDate));
        model.setNotesRepository(new NonPersistentNotesRepository());

        notesEditorPresenter = new NotesEditorPresenterImpl(model, null);
        noteEditorView = new NoteEditorViewImpl(notesEditorPresenter);
        notesEditorPresenter.setNoteEditorView(noteEditorView);
        notesListerPresenter = new NotesListerPresenterImpl(model);
        noteListerView = new NoteListerViewImpl(notesListerPresenter);
        notesListerPresenter.setNoteListerView(noteListerView);
        date = new CurrentDateManager();
    }

    @Test(timeout = 500)
    public void testSimpleStorage() throws InterruptedException {
        Note note = new Note();
        note.setName("Notin");
        note.setTextContent("ouch!");
        note.setLastUpdate(date.getDate());
        noteEditorView.updateNoteFields(note);
        notesEditorPresenter.onEventUpdate();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
    }

    @Test(timeout = 500)
    public void testSimpleUpdate() throws InterruptedException {
        Note note = new Note();
        note.setName("Nota 1");
        note.setTextContent("da text");
        note.setLastUpdate(date.getDate());
        noteEditorView.updateNoteFields(note);
        notesEditorPresenter.onEventUpdate();
        waitForControllerTask();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        note.setTextContent("da text");
        notesEditorPresenter.onEventUpdate();
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
    }


        @Test(timeout = 500)
        public void testShowSelection() throws InterruptedException {
            Note note = new Note();
            note.setName("No me elijas!");
            note.setTextContent("lo hiciste :(");
            note.setLastUpdate(date.getDate());
            noteEditorView.updateNoteFields(note);
            notesEditorPresenter.onEventUpdate();
            waitForControllerTask();
            notesEditorPresenter.onEventSelectedNoteTitle("No me elijas!");
            waitForControllerTask();
            assertEquals(DateFormat.getTimeInstance().format(fixedDate),
                    noteEditorView.getUpdateText());
        }

        @Test(timeout = 500)
        public void testShowUpdateAndSelectOld() throws InterruptedException {
            Note note = new Note();
            note.setName("No me elijas!");
            note.setTextContent("lo hiciste :(");
            note.setLastUpdate(date.getDate());
            noteEditorView.updateNoteFields(note);
            notesEditorPresenter.onEventUpdate();
            waitForControllerTask();
            note.setName("Elegime!");
            note.setTextContent("por favor :D");
            notesEditorPresenter.onEventUpdate();
            waitForControllerTask();
            notesEditorPresenter.onEventSelectedNoteTitle("No me elijas!");
            waitForControllerTask();
            assertEquals("lo hiciste :(", noteEditorView.getTextContent());
        }

            @Test(timeout = 500)
            public void testShowSelectUpdatedNoteAfterAdditions() throws InterruptedException {
                Date oldDate = fixedDate;
                Note note = new Note();
                note.setName("No me cambies");
                note.setTextContent("porfis");
                note.setLastUpdate(date.getDate());
                noteEditorView.updateNoteFields(note);
                notesEditorPresenter.onEventUpdate();
                waitForControllerTask();
                Note note2 = new Note();
                note2.setName("Nota del Medio");
                note2.setTextContent("Gutierrez");
                note2.setLastUpdate(date.getDate());
                noteEditorView.updateNoteFields(note);
                notesEditorPresenter.onEventUpdate();
                waitForControllerTask();
                fixedDate = new Date(1);
                model.setDateManager(new StubbedDateManager(fixedDate));
                Note note3 = new Note();
                note3.setName("No me cambies");
                note3.setTextContent("lo hiciste nomas...");
                note3.setLastUpdate(date.getDate());
                noteEditorView.updateNoteFields(note);
                notesEditorPresenter.onEventUpdate();
                waitForControllerTask();
                Note note4 = new Note();
                note4.setName("Nota del Final");
                note4.setTextContent("Gutierrez");
                note4.setLastUpdate(date.getDate());
                waitForControllerTask();
                notesListerPresenter.setNoteListerView(noteListerView);
                noteListerView.setNoteOnInternalModel("No me cambies",0);
                noteListerView.setJlistIndex(0);//??
                waitForControllerTask();
                assertEquals(DateFormat.getTimeInstance().format(fixedDate), noteEditorView.getUpdateText());
            }

    private void waitForControllerTask() throws InterruptedException{
        while(notesEditorPresenter.isActivellyWorking()) Thread.sleep(1);
    }
}
