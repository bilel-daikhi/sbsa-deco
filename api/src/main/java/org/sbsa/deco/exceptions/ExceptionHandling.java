package org.sbsa.deco.exceptions;


import org.sbsa.deco.exceptions.domaine.*;
import org.sbsa.deco.utils.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    public static final String NO_MAPPING_FOR_THIS_URL = "There is no mapping for this URL";
    public static final String ERROR_PATH = "/error";
    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    private static final String TOKEN_EXPIRED = "Authentication is required. Please login!";
    private static final String DELETE_RELATED_OBJECTS = "You can't delete this object! you need to delete related objects first!";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<HttpResponse> accountDisabledException() {
//        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
//
//    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpResponse> dataIntegrityViolationException() {
        return createHttpResponse(BAD_REQUEST, DELETE_RELATED_OBJECTS);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<HttpResponse> badCredentialsException() {
//        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
//    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<HttpResponse> accessDeniedException() {
//        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
//    }
//
//
//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<HttpResponse> lockedException() {
//        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
//    }
//
//
//    @ExceptionHandler(EmailExistException.class)
//    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(UserCouponsNotFoundException.class)
//    public ResponseEntity<HttpResponse> userCouponsNotFoundException(UserCouponsNotFoundException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(UsernameExistException.class)
//    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<HttpResponse> uknownLinkException() {
//
//        return createHttpResponse(BAD_REQUEST, NO_MAPPING_FOR_THIS_URL);
//    }


    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<HttpResponse> imageNotFoundException(ImageNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(ParseException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ParseException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<HttpResponse> productCategoryNotFoundException(CategoryNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<HttpResponse> customerNotFoundException(ItemNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<HttpResponse> commentNotFoundException(SubCategoryNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
//
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
//        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error("error message: " + exception.getMessage());
        exception.printStackTrace();
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }


    @ExceptionHandler(InvalideItemException.class)
    public ResponseEntity<HttpResponse> invalideAuctionException(InvalideItemException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

//    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
//    public ResponseEntity<HttpResponse> notFoundException(AuthenticationCredentialsNotFoundException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(NOT_FOUND, exception.getMessage());
//    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }


    @RequestMapping(ERROR_PATH)
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
