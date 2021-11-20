package com.prisma.library.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalControllerExceptionHandlerTest {
    @Test
    void testHandleNotFoundException() {
        GlobalControllerExceptionHandler globalControllerExceptionHandler = new GlobalControllerExceptionHandler();
        NotFoundException ex = new NotFoundException("An error occurred");
        ResponseEntity<Object> actualHandleNotFoundExceptionResult = globalControllerExceptionHandler
                .handleNotFoundException(ex, new MockHttpServletRequest());

        assertTrue(actualHandleNotFoundExceptionResult.hasBody());
        assertTrue(actualHandleNotFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleNotFoundExceptionResult.getStatusCode());
        assertEquals("", ((ExceptionResponse) actualHandleNotFoundExceptionResult.getBody()).getUri());
        assertEquals("An error occurred", ((ExceptionResponse) actualHandleNotFoundExceptionResult.getBody()).getMessage());
    }

    @Test
    void testHandleNoResultsFoundException() {
        GlobalControllerExceptionHandler globalControllerExceptionHandler = new GlobalControllerExceptionHandler();
        NoResultsFoundException ex = new NoResultsFoundException("An error occurred");
        ResponseEntity<Object> actualHandleNoResultsFoundExceptionResult = globalControllerExceptionHandler
                .handleNoResultsFoundException(ex, new MockHttpServletRequest());

        assertTrue(actualHandleNoResultsFoundExceptionResult.hasBody());
        assertTrue(actualHandleNoResultsFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleNoResultsFoundExceptionResult.getStatusCode());
        assertEquals("", ((ExceptionResponse) actualHandleNoResultsFoundExceptionResult.getBody()).getUri());
        assertEquals("An error occurred",
                ((ExceptionResponse) actualHandleNoResultsFoundExceptionResult.getBody()).getMessage());
    }
}

