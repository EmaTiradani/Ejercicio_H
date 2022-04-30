package main.java.controller;

import main.java.model.NotesModel;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;

public class NotesControllerImpl implements NotesController {

  private NotesModel notesModel;
  private NoteEditorView noteEditorView;
  private NoteListerView noteListerView;
  private Thread taskThread;

  public NotesControllerImpl(NotesModel notesModel) {
    this.notesModel = notesModel;
  }

  @Override
  public void start() {
      noteListerView = new NoteListerViewImpl(this,notesModel);
      noteListerView.showView();
  }

  @Override public void setNoteEditorView(NoteEditorView noteEditorView) {
    this.noteEditorView = noteEditorView;
  }

  @Override
  public void setNoteListerView(NoteListerView noteListerView) {
    this.noteListerView = noteListerView;
  }

  @Override public void onEventUpdate(String title, String contentText) {

    taskThread = new Thread(() -> {
      noteEditorView.startWaitingStatus();
      notesModel.updateNote(title, contentText);
      noteEditorView.stopWaitingStatus();
    });
    taskThread.start();
  }

  @Override
  public void onEventSelectedNoteTitle(String title) {
    taskThread = new Thread(() -> {
      noteListerView.selectNone();
      createAndShowViewEditorViewWhenNecesary();
      noteEditorView.startWaitingStatus();
      notesModel.selectNote(title);
      noteEditorView.stopWaitingStatus();
    });
    taskThread.start();
  }

  @Override
  public void onEventCreateNewNote() {
      createAndShowViewEditorViewWhenNecesary();
      noteEditorView.cleanFields();
  }

  private void createAndShowViewEditorViewWhenNecesary(){
    if(noteEditorView == null){
      noteEditorView = new NoteEditorViewImpl(this, notesModel);
      noteEditorView.showView();
    }
  }


  @Override
  public void editorClosed() {  noteEditorView = null; }

  @Override
  public boolean isActivellyWorking(){
    return taskThread.isAlive();
  }
}
