package pl.adamd.todosy.validation;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResponseManager {

    ResponseEntity<String> taskNotFoundResponseEntity(Long id);

    ResponseEntity<String> projectAlreadyResolvedResponseEntity(Long id);

    ResponseEntity<String> projectNotFoundResponseEntity(Long id);

    ResponseEntity<String> taskAlreadyResolvedResponseEntity(Long id);

    ResponseEntity<String> processFailedResponseEntity(String message, HttpStatusCode statusCode);

    ResponseEntity<String> projectHasOpenTasksResponseEntity(List<Long> projectId);
}
