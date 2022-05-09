package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.NotesModelListener;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;

public class NotesEditorPresenterImpl implements NotesEditorPresenter {

  private NotesModel notesModel;
  private NoteEditorView noteEditorView;
  private Thread taskThread;
  private String noteName;


  public NotesEditorPresenterImpl(NotesModel notesModel,  String noteName) {

    this.notesModel = notesModel;
    this.noteName = noteName;
    initListeners();
  }

  @Override
  public void start() {
      noteEditorView = new NoteEditorViewImpl(this);
      noteEditorView.showView();
  }

  @Override
  public void setNoteEditorView(NoteEditorView noteEditorView) {
    this.noteEditorView = noteEditorView;
  }

  @Override public void onEventUpdate() {

    String title = noteEditorView.getNoteTitleTF();
    String contentText = noteEditorView.getContentTextTP();
    taskThread = new Thread(() -> {
      noteEditorView.startWaitingStatus();
      notesModel.updateNote(title, contentText);
      noteEditorView.stopWaitingStatus();
    });
    taskThread.start();

  }

  //@Override
  public void onEventSelectedNoteTitle(String title) {

    noteEditorView = new NoteEditorViewImpl(this);
    noteEditorView.updateNoteFields(notesModel.getSelectedNote());
    noteEditorView.showView();
  }

  @Override
  public boolean isActivellyWorking(){
    return taskThread.isAlive();
  }

  private void initListeners() {

    notesModel.addListener(new NotesModelListener() {
      @Override public void didUpdateNote() {
        noteEditorView.updateNoteFields(notesModel.getLastUpdatedNote());
      }
      @Override public void didSelectNote() {
        String selectedNoteName = notesModel.getSelectedNote().getTitle();
        if(selectedNoteName.equals(noteName)){
          onEventSelectedNoteTitle(selectedNoteName);
        }
      }
      @Override public void didCreateNote() {
        noteEditorView.updateNoteFields(notesModel.getLastUpdatedNote());
      }

    });

  }
}
