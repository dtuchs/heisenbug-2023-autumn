package guru.qa.rococo.service;

import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.ErrorJson;
import jakarta.annotation.Nonnull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected @Nonnull ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException ex,
                                                                         @Nonnull HttpHeaders headers,
                                                                         @Nonnull HttpStatusCode status,
                                                                         @Nonnull WebRequest request) {
    return new ResponseEntity<>(new ErrorJson(
        new Date(),
        status.value(),
        ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList())
    ), headers, status);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<ErrorJson> handleSQLIntegrityConstraintViolationException(@Nonnull SQLIntegrityConstraintViolationException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorJson(
            new Date(),
            HttpStatus.BAD_REQUEST.value(),
            List.of("Объект не может быть сохранен по причине дублирования первичного ключа")
        ));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorJson> handleNotFoundException(@Nonnull NotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorJson(
            new Date(),
            HttpStatus.NOT_FOUND.value(),
            List.of("Объект не найден в БД", ex.getMessage())
        ));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorJson> handleIllegalArgumentException(@Nonnull IllegalArgumentException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorJson(
            new Date(),
            HttpStatus.BAD_REQUEST.value(),
            List.of("Невалидные параметры запроса", ex.getMessage())
        ));
  }
}
