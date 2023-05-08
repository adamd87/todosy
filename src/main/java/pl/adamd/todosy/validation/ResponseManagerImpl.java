package pl.adamd.todosy.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseManagerImpl implements ResponseManager {

    private static final String TASK_NOT_FOUND_MSG = "Task not found with taskID: ";
    private static final String PROJECT_NOT_FOUND_MSG = "Project not found with projectID: ";
    private static final String PROJECT_RESOLVED_MSG = "Project is already resolved, projectID: ";
    private static final String TASK_RESOLVED_MSG = "Task is already resolved, projectID: ";
    private static final String PROCESS_FAILED = "Process failed, message: ";
    private static final String PROJECT_HAS_OPEN_TASKS = "Project has open tasks, close all task first, tasks ids: ";

    @Override
    public ResponseEntity<String> taskNotFoundResponseEntity(Long id) {
        return new ResponseEntity<>(TASK_NOT_FOUND_MSG + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> projectAlreadyResolvedResponseEntity(Long id) {
        return new ResponseEntity<>(PROJECT_RESOLVED_MSG + id, HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<String> projectNotFoundResponseEntity(Long id) {
        return new ResponseEntity<>(PROJECT_NOT_FOUND_MSG + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> taskAlreadyResolvedResponseEntity(Long id) {
        return new ResponseEntity<>(TASK_RESOLVED_MSG + id, HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<String> processFailedResponseEntity(String message, HttpStatusCode statusCode) {
        return new ResponseEntity<>(PROCESS_FAILED + message, statusCode);
    }

    @Override
    public ResponseEntity<String> projectHasOpenTasksResponseEntity(List<Long> projectId) {
        return new ResponseEntity<>(PROJECT_HAS_OPEN_TASKS + projectId.toString(), HttpStatus.CONFLICT);
    }
}
