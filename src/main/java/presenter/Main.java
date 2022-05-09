package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.utils.CurrentDateManager;

public class Main {

  public static void main(String[] args) {

    NotesModel model = new NotesModelImpl();
    model.setDateManager(new CurrentDateManager());
    model.setNotesRepository(new NonPersistentNotesRepository());

    NotesListerPresenterImpl lister = new NotesListerPresenterImpl(model);
    //NotesPresenter editor = new NotesEditorPresenter(model, lister, selectedNoteName);


    //editor.start();//TODO habria que hacer que el lister te abra un editor nuevo para la nota seleccionada cuando vos la seleccionas
    lister.start();
  }






}
