package main.java.views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Random;

import javax.swing.*;

import main.java.presenter.NotesEditorPresenter;
import main.java.model.Note;

public class NoteEditorViewImpl implements NoteEditorView {
  private JTextField noteTitleTF;
  private JButton updateBtn;
  private JLabel lastUpdateLbl;
  protected JPanel content;
  private JPanel updateIconCard;
  private JLabel stoppedLoadingIcon;
  private JLabel runningLoadingIcon;
  private JPanel mainPanel;
  private JTextPane contentTextTP;

  private NotesEditorPresenter notesPresenter;

  public NoteEditorViewImpl(NotesEditorPresenter notesPresenter/*, NotesModel notesModel*/) {
    this.notesPresenter = notesPresenter;
    initListeners();
    lastUpdateLbl.setText("");
  }

  @Override public void startWaitingStatus() {
    ((CardLayout) updateIconCard.getLayout()).show(updateIconCard, "Running");
    for (Component c: mainPanel.getComponents())
      c.setEnabled(false);
  }

  @Override public void stopWaitingStatus() {
    ((CardLayout) updateIconCard.getLayout()).show(updateIconCard, "Stopped");
    for (Component c: mainPanel.getComponents())
      c.setEnabled(true);
  }

  @Override public void cleanFields() {
    noteTitleTF.setText("");
    contentTextTP.setText("");
    lastUpdateLbl.setText("");
  }

  @Override
  public Container getContent() {
    return this.content;
  }

  @Override
  public void showView() {
    JFrame frame = new JFrame("Note Editor");
    frame.setContentPane(content);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setLocation(new Random().nextInt(500),0);
    frame.setVisible(true);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        //notesPresenter.editorClosed();
      }
    });
  }

  private void initListeners() {
    updateBtn.addActionListener(actionEvent -> notesPresenter
            .onEventUpdate());

  }

  public void updateNoteFields(Note note) {
    if(note != null){
      noteTitleTF.setText(note.getTitle());
      contentTextTP.setText(note.getTextContent() );
      lastUpdateLbl.setText(DateFormat.getTimeInstance().format(note.getLastUpdate()));
    }
  }

  @Override
  public String getUpdateText(){
    return lastUpdateLbl.getText();
  }

  @Override
  public String getTextContent() {
    return contentTextTP.getText();
  }

  public String getNoteTitleTF(){
    return noteTitleTF.getText();
  }

  public String getContentTextTP(){
    return contentTextTP.getText();
  }



}
