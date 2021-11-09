package com.prisma.library.controller;

import com.prisma.library.model.User;
import com.prisma.library.service.UserService;
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
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getBookBorrowedUsers")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Users list Borrowed at least one book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsersBorrowedBook() {
        log.trace("inside getAllUsersBorrowedBook method");

        List<User> users = userService.getAllUsersBorrowedBook();

        if (users == null) {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users,
                    HttpStatus.OK);
        }
    }

    @GetMapping("/getBookBorrowedUsers/{date}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All users list who have borrowed a book on a given date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsersBorrowedBookOnGivenDate(@PathVariable(required = true) @Valid String date) {
        log.trace("inside getAllUsersBorrowedBookOnGivenDate method");
        List<User> users = userService.getAllUsersBorrowedBookOnGivenDate(date);

        if (users == null) {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users,
                    HttpStatus.OK);
        }
    }

    @GetMapping("/getActiveNonBookBorrowedUsers")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Active users list who have not currently borrowed Book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getActiveUsersNotBorrowedSingleBook() {
        log.trace("inside getActiveUsersNotBorrowedSingleBook method");
        List<User> users = userService.getActiveUsersNotBorrowedSingleBook();

        if (users == null) {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users,
                    HttpStatus.OK);
        }
    }
}
