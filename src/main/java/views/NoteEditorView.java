package main.java.views;

import main.java.model.Note;

import java.awt.*;

public interface NoteEditorView extends BaseView{

  void startWaitingStatus();

  void stopWaitingStatus();




  String getUpdateText();

  String getTextContent();

    void cleanFields();

  String getNoteTitleTF();

  String getContentTextTP();

  void updateNoteFields(Note note);
}
