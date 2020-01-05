package me.mtte.code.ideahub.model;

import me.mtte.code.ideahub.database.ideahub.tables.records.NoteRecord;

import java.sql.Timestamp;

public class Note {

    private final int id;
    private final String title;
    private final String content;
    private final Timestamp created;
    private final boolean isShared;
    private String author;

    public Note(NoteRecord noteRecord) {
        this(noteRecord.getId(), null, noteRecord.getTitle(), noteRecord.getContent(), noteRecord.getCreated(), noteRecord.getShared());
    }


    public Note(int id, String author, String title, String content, Timestamp created, boolean isShared) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.created = created;
        this.isShared = isShared;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isShared() {
        return isShared;
    }

}
