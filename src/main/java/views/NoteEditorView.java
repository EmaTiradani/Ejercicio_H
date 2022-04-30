package main.java.views;

import java.awt.*;

public interface NoteEditorView extends BaseView{

  void startWaitingStatus();

  void stopWaitingStatus();




  String getUpdateText();

  String getTextContent();

    void cleanFields();
}
