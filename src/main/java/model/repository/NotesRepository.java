package main.java.model.repository;

import main.java.model.Note;

import java.util.ArrayList;

public interface NotesRepository {
    public boolean storeNote(Note note);

    public Note retreiveNote(String noteTitle);



}
