package main.java.model.repository;

import main.java.model.Note;

import java.util.ArrayList;

public class NonPersistentNotesRepository implements NotesRepository {

    ArrayList<Note> storage = new ArrayList<>();

    @Override
    public boolean storeNote(Note note) {
        boolean updated = false;
        updated = storage.removeIf(storedNote -> storedNote.hasSameTitle(note));
        storage.add(0,note);
        return updated;
    }

    @Override
    public Note retreiveNote(String noteTitle) {
        Note emptyNote = null;
        for(Note note: storage)
            if(note.getTitle().equals(noteTitle)) return note;
        return emptyNote;
    }


}
