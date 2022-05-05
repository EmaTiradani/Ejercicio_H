package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.Note;
import main.java.model.NotesModelListener;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;

public class NotesPresenterImpl implements NotesPresenter {

  private NotesModel notesModel;
  private NoteEditorView noteEditorView;
  private NoteListerView noteListerView;
  private Thread taskThread;

  public NotesPresenterImpl(NotesModel notesModel) {
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

  @Override public void onEventUpdate(/*String title, String contentText*/) {
//noteTitleTF.getText(), contentTextTP.getText()
    String title = noteEditorView.getNoteTitleTF();
    String contentText = noteEditorView.getContentTextTP();
    taskThread = new Thread(() -> {
      noteEditorView.startWaitingStatus();
      notesModel.updateNote(title, contentText);
      noteEditorView.stopWaitingStatus();
    });
    taskThread.start();
  }

  @Override
  public void onEventSelectedNoteTitle(/*String title*/) {//TODO tomar los datos necesarios de la vista,
    String title = noteListerView.getNoteFromListInternalModel();
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

  private void initListeners() {

    notesModel.addListener(new NotesModelListener() {
      @Override public void didUpdateNote() {
        //updateFieldsOfStoredNote();TODO aca hay que pedir datos cambiados y darselos formateados a la vista
        noteEditorView.updateNoteFields(notesModel.getLastUpdatedNote());
      }
      @Override public void didSelectNote() {
        //updateFieldsOfSelectedNote();} TODO same
        noteEditorView.updateNoteFields(notesModel.getSelectedNote());
      }

    });

  }
}
