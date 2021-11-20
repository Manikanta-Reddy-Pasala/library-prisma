package com.prisma.library.controller;

import com.prisma.library.model.Book;
import com.prisma.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<Book> getAllAvailableBooks() {
        log.trace("inside getAllAvailableBooks method");
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/getAllBooksBorrowed/{user}/{fromDate}/{toDate}")
    @ApiOperation(value = "Get All Books Borrowed By User and Date Range", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksBorrowed(@ApiParam(value = "user name", required = true) @PathVariable @Valid String user,
                                          @ApiParam(value = "from date. Ex: 2020-01-20", required = true) @PathVariable @Valid String fromDate,
                                          @ApiParam(value = "to date. Ex: 2021-04-25", required = true) @PathVariable @Valid String toDate) {
        log.trace("inside getAllBooksBorrowed method");
        return bookService.getAllBooksBorrowedByUserInGivenDateRange(user, fromDate, toDate);
    }
}
