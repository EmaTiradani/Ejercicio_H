package main.java.controller;

import main.java.views.NoteEditorView;
import main.java.views.NoteListerView;

public interface NotesController {

  void start();

  void setNoteEditorView(NoteEditorView noteEditorView);

  void setNoteListerView(NoteListerView noteListerView);

  void onEventUpdate(String title, String contentText);

  void onEventSelectedNoteTitle(String title);

  void onEventCreateNewNote();

    boolean isActivellyWorking();

    void editorClosed();


}
