package com.prisma.library.controller;

import com.prisma.library.model.Book;
import com.prisma.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Available Books Today", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllAvailableBooks() {
        log.trace("inside getAllAvailableBooks method");
        List<Book> books = bookService.getAllAvailableBooks();

        if (books == null) {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books,
                    HttpStatus.OK);
        }
    }

    @GetMapping("/getAllBooksBorrowed/{user}/{fromDate}/{toDate}")
    @ApiOperation(value = "Get All Books Borrowed By User and Date Range", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Book>> getAllBooksBorrowed(@PathVariable(required = true) @Valid String user,
                                                          @PathVariable(required = true) @Valid String fromDate,
                                                          @PathVariable(required = true) @Valid String toDate) {
        log.trace("inside getAllBooksBorrowed method");
        List<Book> books = bookService.getAllBooksBorrowedByUserInGivenDateRange(user, fromDate, toDate);

        if (books == null) {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books,
                    HttpStatus.OK);
        }
    }
}
