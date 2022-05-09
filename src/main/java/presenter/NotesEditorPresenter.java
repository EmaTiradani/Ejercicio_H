package main.java.presenter;

import main.java.views.NoteEditorView;

public interface NotesEditorPresenter {

    void start();

    void setNoteEditorView(NoteEditorView noteEditorView);

    void onEventUpdate();

    void onEventSelectedNoteTitle(String title);

    //void editorClosed();

    boolean isActivellyWorking();


}
