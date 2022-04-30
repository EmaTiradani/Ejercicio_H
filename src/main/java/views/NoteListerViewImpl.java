package main.java.views;

import main.java.controller.NotesController;
import main.java.model.NotesModel;
import main.java.model.NotesModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NoteListerViewImpl implements NoteListerView{

    private JPanel content;
    private JList notesJList;
    private JButton createNewNoteBtn;

    DefaultListModel<String> notesListInternalModel = new DefaultListModel<>();
    NotesController notesController;
    NotesModel notesModel;

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

    public NoteListerViewImpl(NotesController notesController, NotesModel notesModel) {
        this.notesController = notesController;
        this.notesModel = notesModel;
        initListeners();
        notesJList.setModel(notesListInternalModel);

    }

    @Override
    public void selectNone(){
        notesJList.clearSelection();
    }

    private void updateNoteList() {
        String noteTitleToAddOrUpdate = notesModel.getLastUpdatedNote().getTitle();
        notesListInternalModel.removeElement(noteTitleToAddOrUpdate);
        notesListInternalModel.insertElementAt(noteTitleToAddOrUpdate, 0);
    }

    private void initListeners() {
        notesModel.addListener(new NotesModelListener() {
            @Override public void didUpdateNote() {
                updateNoteList();
            }
            @Override public void didSelectNote() { }
        });

        notesJList.addListSelectionListener(listSelectionEvent -> {
            int selectedIndex = notesJList.getSelectedIndex();
            if(selectedIndex >= 0)
                notesController.onEventSelectedNoteTitle(notesListInternalModel.elementAt(selectedIndex));
        });

        createNewNoteBtn.addActionListener(actionEvent -> {
            notesController.onEventCreateNewNote();
        });
    }
}
