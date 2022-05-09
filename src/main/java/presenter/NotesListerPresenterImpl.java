package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.NotesModelListener;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;

import java.util.HashMap;

public class NotesListerPresenterImpl implements NotesListerPresenter{

    private NotesModel notesModel;
    private NoteListerView noteListerView;

    private HashMap<String, NotesEditorPresenterImpl> editoresActivos = new HashMap<>();

    public NotesListerPresenterImpl(NotesModel notesModel){ this.notesModel = notesModel;}

    @Override
    public void start() {
        noteListerView = new NoteListerViewImpl(this/*,notesModel*/);
        noteListerView.showView();
        initListeners();
    }

    public void setNoteListerView(NoteListerView noteListerView){
        this.noteListerView=noteListerView;
    }
    @Override
    public void onEventSelectedNoteTitle() {
        String selectedNoteName = noteListerView.getNoteFromListInternalModel();
        if(editorAlreadyOpen(selectedNoteName)){
            notesModel.selectNote(noteListerView.getNoteFromListInternalModel());
            noteListerView.selectNone();
        }
        else{
            NotesEditorPresenterImpl editor = new NotesEditorPresenterImpl(notesModel, selectedNoteName);
            editoresActivos.put(selectedNoteName, editor);
            notesModel.selectNote(noteListerView.getNoteFromListInternalModel());
            noteListerView.selectNone();
        }

    }

    @Override
    public void onEventCreateNewNote() {

        String selectedNoteName = noteListerView.getNoteFromListInternalModel();

        if(editorAlreadyOpen(selectedNoteName)){
            editoresActivos.get(selectedNoteName).start();
        }
        else{
            NotesEditorPresenterImpl editor = new NotesEditorPresenterImpl(notesModel, selectedNoteName);
            editoresActivos.put(selectedNoteName, editor);
            editor.start();
        }
    }

    private boolean editorAlreadyOpen(String noteTitle){
        return editoresActivos.containsKey(noteTitle);
    }

    @Override
    public void editorClosed(NotesEditorPresenter editor){//TODO Pal tester?
        editoresActivos.remove(editor);
    }


    private void initListeners(){
        notesModel.addListener(new NotesModelListener() {
            @Override
            public void didUpdateNote() {
                noteListerView.updateNoteList(notesModel.getLastUpdatedNote().getTitle());
            }

            @Override
            public void didSelectNote() {
            }

            @Override
            public void didCreateNote() {
                noteListerView.updateNoteList(notesModel.getLastUpdatedNote().getTitle());
            }
        });
    }

}
