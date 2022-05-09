package main.java.presenter;

public interface NotesListerPresenter {

    void start();

    void onEventSelectedNoteTitle();

    void onEventCreateNewNote();

    //boolean isActivellyWorking();

    void editorClosed(NotesEditorPresenter editor);

}
