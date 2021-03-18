package com.osynt.keep.keepnote.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String subTitle;
    private String dateTime;
    private String noteText;
    private String color;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Note(String title, String noteText) {
        this.title = title;
        this.noteText = noteText;
    }

    public Note(String title, String subTitle, String dateTime, String noteText, String color, String image) {
        this.title = title;
        this.subTitle = subTitle;
        this.dateTime = dateTime;
        this.noteText = noteText;
        this.color = color;
        this.image = image;
    }

    public Note() {

    }

    @Override
    public String toString() {
        return "Note [color=" + color + ", dateTime=" + dateTime + ", id=" + id + ", image=" + image + ", noteText="
                + noteText + ", subTitle=" + subTitle + ", title=" + title + ", webLink=" + "]";
    }

}