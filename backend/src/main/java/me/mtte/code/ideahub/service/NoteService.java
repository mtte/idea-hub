package me.mtte.code.ideahub.service;

import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.database.ideahub.tables.records.NoteRecord;
import me.mtte.code.ideahub.model.Note;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.mtte.code.ideahub.database.ideahub.tables.Note.NOTE;
import static me.mtte.code.ideahub.database.ideahub.tables.User.USER;

public class NoteService extends AbstractService {

    public NoteService(Database database) {
        super(database);
    }

    public Optional<Note> getNote(int id) {
        var note = getDb().select(NOTE.ID, USER.NAME, NOTE.TITLE, NOTE.CONTENT, NOTE.CREATED, NOTE.SHARED)
                .from(NOTE)
                .join(USER)
                .on(NOTE.AUTHOR.eq(USER.ID))
                .where(NOTE.ID.eq(id))
                .fetchOptional();

        return note.map(r -> new Note(r.value1(), r.value2(), r.value3(), r.value4(), r.value5(), r.value6()));
    }

    public List<Note> getAllNotesForUser(int userId) {
        var result = getDb().select(NOTE.ID, USER.NAME, NOTE.TITLE, NOTE.CONTENT, NOTE.CREATED, NOTE.SHARED)
                .from(NOTE)
                .join(USER)
                .on(NOTE.AUTHOR.eq(USER.ID))
                .where(NOTE.AUTHOR.eq(userId)
                        .or(NOTE.SHARED))
                .fetch();
        return result.stream()
                .map(r -> new Note(r.value1(), r.value2(), r.value3(), r.value4(), r.value5(), r.value6()))
                .collect(Collectors.toList());
    }

    public boolean deleteNote(int id) {
        int deleted = getDb().delete(NOTE)
                .where(NOTE.ID.eq(id))
                .execute();
        return deleted == 1;
    }

    public Note createNote(int authorId, String title, String content, boolean shared) {
        NoteRecord noteRecord = getDb().newRecord(NOTE);
        noteRecord.setAuthor(authorId);
        noteRecord.setTitle(title);
        noteRecord.setContent(content);
        noteRecord.setCreated(Timestamp.from(Instant.now()));
        noteRecord.setShared(shared);

        noteRecord.store();

        return new Note(noteRecord);
    }

    public Note updateNote(int id, String title, String content, Boolean shared)  {
        NoteRecord noteRecord = getDb().fetchOne(NOTE, NOTE.ID.eq(id));

        if (noteRecord == null) {
            return null;
        }

        if (title != null) {
            noteRecord.setTitle(title);
        }
        if (content != null) {
            noteRecord.setContent(content);
        }
        if (shared != null) {
            noteRecord.setShared(shared);
        }

        noteRecord.store();

        return new Note(noteRecord);
    }

}
