package main.java.model;

import main.java.model.repository.NotesRepository;
import main.java.utils.DateManager;
import main.java.utils.WaitSimulator;

import java.util.ArrayList;

public class NotesModelImpl implements NotesModel {

  private DateManager dateManager;
  private NotesRepository notesRepository;
  private Note selectedNote;
  private Note lastStoredNote;
  private ArrayList<NotesModelListener> listeners = new ArrayList<>();

  @Override public void addListener(NotesModelListener listener) { this.listeners.add(listener); }
  @Override public void setNotesRepository(NotesRepository notesRepository) { this.notesRepository = notesRepository; }
  @Override public void setDateManager(DateManager dateManager) { this.dateManager = dateManager;   }

  @Override public Note getLastUpdatedNote() { return lastStoredNote;  }
  @Override public Note getSelectedNote() { return selectedNote; }

  @Override public  void updateNote(String title, String content) {
    if(Note.isValidTitleForNote(title)){
      WaitSimulator.simulateShortWait();
      updateNoteNow(title, content);
    }
  }
  private void updateNoteNow(String title, String content) {
    Note noteToUpdate = new Note();
    noteToUpdate.setName(title);
    noteToUpdate.setTextContent(content);
    noteToUpdate.setLastUpdate(dateManager.getDate());
    if(notesRepository.storeNote(noteToUpdate)) {
      lastStoredNote = noteToUpdate;
      notifyUpdateListener();
    }
    else{
      lastStoredNote = noteToUpdate;
      notifyCreationListener();
    }
  }

  @Override
  public void selectNote(String noteTitle) {
    WaitSimulator.simulateShortWait();
    selectedNote = notesRepository.retreiveNote(noteTitle);
    notifySelectionListener();
  }


  private void notifyUpdateListener() {
    for (NotesModelListener listener: listeners) {
      listener.didUpdateNote();
    }
  }
  private void notifyCreationListener(){
    for (NotesModelListener listener: listeners) {
      listener.didCreateNote();
    }
  }
  private void notifySelectionListener() {
    for (NotesModelListener listener: listeners) {
      listener.didSelectNote();
    }
  }
}
