package main.java.controller;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.utils.CurrentDateManager;
import main.java.views.*;

import javax.swing.*;
import java.util.Random;

public class Main {

  public static void main(String[] args) {

    NotesModel model = new NotesModelImpl();
    model.setDateManager(new CurrentDateManager());
    model.setNotesRepository(new NonPersistentNotesRepository());

    NotesController controller = new NotesControllerImpl(model);

    controller.start();
  }






}
