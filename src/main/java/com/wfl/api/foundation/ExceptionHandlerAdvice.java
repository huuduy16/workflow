package com.wfl.api.foundation;

import com.wfl.domains.responses.error.ApiError;
import com.wfl.domains.responses.error.ErrorCode;
import com.wfl.domains.responses.error.ErrorResponse;
import com.wfl.domains.responses.error.ValidationFailedResponse;
import com.wfl.exceptions.AuthenticationFailedException;
import com.wfl.exceptions.FieldValidateException;
import com.wfl.exceptions.ResourceAlreadyExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> methodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
            new ApiError(ErrorCode.METHOD_NOT_ALLOWED, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> authenticationExceptionFailed(Exception e) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED,
            new ApiError(ErrorCode.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> requestHandlingNoHandlerFound(NoHandlerFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND,
            new ApiError(ErrorCode.NOT_FOUND, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationMethodArgumentFail(MethodArgumentNotValidException e) {
        List<ApiError> errors = parseObjectError(e.getBindingResult().getAllErrors());
        return new ValidationFailedResponse(errors);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException e) {
        List<ApiError> errors = parseObjectError(e.getBindingResult().getAllErrors());
        return new ValidationFailedResponse(errors);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> passwordNotMatchesHandle(AuthenticationFailedException e) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED,
            new ApiError(ErrorCode.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundHandle(UsernameNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND,
            new ApiError(ErrorCode.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> resourceAlreadyExistException(ResourceAlreadyExistException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, new ApiError(ErrorCode.ALREADY_EXISTS,
            getMessage(e, "Inserted data can not be converted")));
    }

    @ExceptionHandler(FieldValidateException.class)
    public ResponseEntity<?> fieldValidateException(FieldValidateException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST,
            new ApiError(ErrorCode.FIELD_VALIDATE_ERROR, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> internalServerError(Exception e) {
        log.error("Uncaught Exception:  " + e.getClass() + " : " + e.getMessage() + " " + e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
            new ApiError(ErrorCode.INTERNAL_ERROR, "INTERNAL ERROR"));
    }

    private List<ApiError> parseObjectError(List<ObjectError> objectErrors) {
        List<ApiError> errors = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            Object[] objects = objectError.getArguments();
            if ((objects != null) && (objects.length > 0)) {
                DefaultMessageSourceResolvable messageSourceResolvable = (DefaultMessageSourceResolvable) objects[0];
                String code = messageSourceResolvable.getDefaultMessage();
                ApiError payloadError = new ApiError(resolveErrorCode(objectError),
                    code + " " + objectError.getDefaultMessage());
                errors.add(payloadError);
            }
        }
        return errors;
    }

    private ErrorCode resolveErrorCode(ObjectError error) {
        if (Objects.equals(error.getCode(), Size.class.getSimpleName())) {
            return ErrorCode.EXCEED_LIMIT;
        }
        return ErrorCode.INVALID_PARAMETER;
    }

    private String getMessage(Exception e, String defaultMsg) {
        return StringUtils.hasText(e.getMessage()) ? e.getMessage() : defaultMsg;
    }
}
