package main.java.model;

import java.util.Date;

public class Note {

  private String title;
  private String textContent;
  private Date lastUpdate;

  public String getTitle() {
    return title;
  }

  public void setName(String title) {
    this.title = title;
  }

  public String getTextContent() {
    return textContent;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public boolean hasSameTitle(Note otherNote){
    return this.getTitle().equals(otherNote.getTitle());
  }

  public static boolean isValidTitleForNote(String title){
    return !title.isBlank();
  }

}
