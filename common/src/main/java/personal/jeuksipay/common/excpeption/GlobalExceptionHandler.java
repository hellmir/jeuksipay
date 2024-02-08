package personal.jeuksipay.common.excpeption;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static personal.jeuksipay.common.excpeption.LogMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AbstractSeriousException.class)
    private ResponseEntity<ErrorResponse> handleSeriousExceptions
            (AbstractSeriousException e) {
        log.error(LOG_ERROR_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(AbstractSignificantException.class)
    private ResponseEntity<ErrorResponse> handleSignificantExceptions
            (AbstractSignificantException e) {
        log.warn(LOG_WARN_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(AbstractGeneralException.class)
    private ResponseEntity<ErrorResponse> handleGeneralExceptions(AbstractGeneralException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<ErrorResponse> handleIOException
            (IOException e) {
        log.error(LOG_ERROR_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<ErrorResponse> handleNullPointerException
            (NullPointerException e) {
        log.error(LOG_ERROR_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleEntityNotFoundException
            (EntityNotFoundException e) {
        log.warn(LOG_WARN_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException
            (HttpRequestMethodNotSupportedException e) {
        log.warn(LOG_WARN_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorResponse> handleAccessDeniedException
            (AccessDeniedException e) {
        log.warn(LOG_WARN_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgumentException
            (IllegalArgumentException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException
            (HttpMessageNotReadableException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    private ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException
            (HttpMediaTypeNotSupportedException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(MalformedJwtException.class)
    private ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    private ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException e) {
        log.info(LOG_INFO_MESSAGE, e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getStatusCode()));
    }
}

