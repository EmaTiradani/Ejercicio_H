package main.java.views;

import main.java.presenter.NotesListerPresenter;

import javax.swing.*;
import java.awt.*;

public class NoteListerViewImpl implements NoteListerView{

    private JPanel content;
    private JList notesJList;
    private JButton createNewNoteBtn;

    DefaultListModel<String> notesListInternalModel = new DefaultListModel<>();
    NotesListerPresenter notesListerPresenter;
    //NotesModel notesModel;//TODO sacar el modelo de aca

    @Override
    public Container getContent() {
        return content;
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Notes");
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public NoteListerViewImpl(NotesListerPresenter notesPresenter/*, NotesModel notesModel*/) {//TODO sacar el modelo de aca
        this.notesListerPresenter = notesPresenter;
        //this.notesModel = notesModel;//TODO sacar el modelo de aca
        initListeners();
        notesJList.setModel(notesListInternalModel);

    }

    @Override
    public void selectNone(){
        notesJList.clearSelection();
    }

    /*private void updateNoteList() {//TODO este metodo pedia las cosas al modelo
        String noteTitleToAddOrUpdate = notesModel.getLastUpdatedNote().getTitle();
        notesListInternalModel.removeElement(noteTitleToAddOrUpdate);
        notesListInternalModel.insertElementAt(noteTitleToAddOrUpdate, 0);
    }*/
    public void updateNoteList(String noteTitleToAddOrUpdate){
        notesListInternalModel.removeElement(noteTitleToAddOrUpdate);
        notesListInternalModel.insertElementAt(noteTitleToAddOrUpdate, 0);
    }

    private void initListeners() {

        notesJList.addListSelectionListener(listSelectionEvent -> {
            int selectedIndex = notesJList.getSelectedIndex();
            if(selectedIndex >= 0)
                notesListerPresenter.onEventSelectedNoteTitle();
        });

        createNewNoteBtn.addActionListener(actionEvent -> {
            notesListerPresenter.onEventCreateNewNote();
        });
    }

    public String getNoteFromListInternalModel(){
        String note=null;
        int selectedIndex = notesJList.getSelectedIndex();
        if(selectedIndex >= 0)
            note = notesListInternalModel.elementAt(selectedIndex);
        return note;
    }
}
