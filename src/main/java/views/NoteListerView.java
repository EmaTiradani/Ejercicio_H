package main.java.views;

import java.awt.*;

public interface NoteListerView extends BaseView {

    public void selectNone();

    String getNoteFromListInternalModel();

    void updateNoteList(String noteTitleToAddOrUpdate);

    void setNoteOnInternalModel(String name, int index);

    void setJlistIndex(int index);

}
