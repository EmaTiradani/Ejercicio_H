package main.java.presenter;

import main.java.views.NoteListerView;

public interface NotesListerPresenter {

    void start();

    void onEventSelectedNoteTitle();

    void onEventCreateNewNote();

    //boolean isActivellyWorking();

    void editorClosed(NotesEditorPresenter editor);

    void setNoteListerView(NoteListerView noteListerView);

}
