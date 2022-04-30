package main.java.model;

import main.java.model.repository.NotesRepository;
import main.java.utils.DateManager;

public interface NotesModel {

  void setDateManager(DateManager dateManager);

  Note getLastUpdatedNote();
  void updateNote(String title, String content);

  void addListener(NotesModelListener listener);

  void setNotesRepository(NotesRepository notesRepository);


  void selectNote(String noteTitle);

  Note getSelectedNote();
}
