package me.mtte.code.ideahub.controller;

import me.mtte.code.ideahub.responses.ResponseFactory;
import me.mtte.code.ideahub.service.NoteService;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static me.mtte.code.ideahub.util.SparkUtil.getRequestId;
import static spark.Spark.*;
import static spark.Spark.delete;

public class NoteController implements RouteGroup {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void addRoutes() {
        // Get note
        get("/:id", this::getNote, json());

        // Get notes
        get("", this::getNotes, json());

        // Create note
        post("", this::createNote, json());

        // Update note
        put("/:id", this::updateNote, json());

        // Delete note
        delete("/:id", this::deleteNote, json());

    }

    private Object getNote(Request request, Response response) {
        var id = getRequestId(request);

        if (id != null) {
            var note = this.noteService.getNote(id);
            if (note.isPresent()) {
                return note.get();
            }
        }

        return ResponseFactory.createInvalidIdError(request, response);
    }

    private Object getNotes(Request request, Response response) {
        return null;
    }

    private Object deleteNote(Request request, Response response) {
        return null;
    }

    private Object createNote(Request request, Response response) {
        return null;
    }


    private Object updateNote(Request request, Response response) {
        return null;
    }

}
