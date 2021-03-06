package me.mtte.code.ideahub.controller;

import me.mtte.code.ideahub.responses.ErrorResponse;
import me.mtte.code.ideahub.responses.ResponseFactory;
import me.mtte.code.ideahub.responses.SuccessResponse;
import me.mtte.code.ideahub.service.NoteService;
import me.mtte.code.ideahub.util.SparkUtil;
import me.mtte.code.ideahub.validation.Validation;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static me.mtte.code.ideahub.util.SparkUtil.*;
import static me.mtte.code.ideahub.validation.StandardValidators.*;
import static spark.Spark.*;

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

        var userId = getUserId(request, response);

        if (id != null && userId != null) {
            var note = this.noteService.getNote(id, userId);
            if (note.isPresent()) {
                return note.get();
            }
        }

        return ResponseFactory.createInvalidIdError(request, response);
    }

    private Object getNotes(Request request, Response response) {
        var id = getUserId(request, response);

        if (id == null) {
            response.status(500);
            return new ErrorResponse("Could not retrieve user profile");
        }

        return this.noteService.getAllNotesForUser(id);
    }

    private Object deleteNote(Request request, Response response) {
        var id = getRequestId(request);
        var userId = getUserId(request, response);

        if (id == null || userId == null) {
            return ResponseFactory.createInvalidIdError(request, response);
        }

        if (!this.noteService.deleteNote(id, userId)) {
            response.status(500);
            return new ErrorResponse("Error while deleting note with id %d", id);
        }

        return new SuccessResponse();
    }

    private Object createNote(Request request, Response response) {
        // Author id (the logged in user)
        var id = getUserId(request, response);
        if (id == null) {
            response.status(500);
            return new ErrorResponse("Could not retrieve user profile");
        }

        String title = getParameter(request, "title");
        var titleValidation = new Validation<>(title, nonNull().and(minLength(5)).and(maxLength(100)));
        if (titleValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "title", title, titleValidation);
        }

        String content = getParameter(request, "content");
        var contentValidation = new Validation<>(content, nonNull().and(notEmpty()));
        if (contentValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "content", content, contentValidation);
        }

        boolean isShared = false;
        if (hasAuthorPermissions(request, response)) {
            String shared = getParameter(request, "shared");
            var sharedValidation = new Validation<>(shared, nonNull().and(isBoolean()));
            if (sharedValidation.failed()) {
                return ResponseFactory.createInvalidParameterError(response, "shared", shared, sharedValidation);
            }
            isShared = Boolean.parseBoolean(shared);
        }

        return this.noteService.createNote(id, title, content, isShared);
    }


    private Object updateNote(Request request, Response response) {
        String title = getParameter(request, "title");
        String content = getParameter(request, "content");

        if (title != null) {
            var titleValidation = new Validation<>(title, minLength(5).and(maxLength(100)));
            if (titleValidation.failed()) {
                return ResponseFactory.createInvalidParameterError(response, "title", title, titleValidation);
            }
        }

        if (content != null) {
            var contentValidation = new Validation<>(content, notEmpty());
            if (contentValidation.failed()) {
                return ResponseFactory.createInvalidParameterError(response, "content", content, contentValidation);
            }
        }

        Boolean isShared = null;
        if (hasAuthorPermissions(request, response)) {
            String shared = getParameter(request, "shared");
            if (shared != null) {
                var sharedValidation = new Validation<>(shared, isBoolean());
                if (sharedValidation.failed()) {
                    return ResponseFactory.createInvalidParameterError(response, "shared", shared, sharedValidation);
                }
                isShared = Boolean.parseBoolean(shared);;
            }
        }

        var userId = getUserId(request, response);
        var updated = this.noteService.updateNote(getRequestId(request), userId, title, content, isShared);
        if (updated == null) {
            return ResponseFactory.createInvalidIdError(request, response);
        }

        return updated;
    }

}
