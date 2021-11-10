package com.prisma.library.service.impl;

import com.prisma.library.model.User;
import com.prisma.library.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    User user = new User();

    @Before
    public void init() throws ParseException {
        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
    }

    @Test
    void testGetAllUsersBorrowedBook() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(this.userRepository.getAllUsersBorrowedBook()).thenReturn(userList);
        List<User> actualAllUsersBorrowedBook = this.userServiceImpl.getAllUsersBorrowedBook();

        verify(this.userRepository).getAllUsersBorrowedBook();
        assertSame(userList, actualAllUsersBorrowedBook);
        assertEquals(1, actualAllUsersBorrowedBook.size());
    }

    @Test
    void testGetAllUsersBorrowedBookOnGivenDate() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(this.userRepository.getAllUsersBorrowedBookOnGivenDate("2020-03-01")).thenReturn(userList);
        List<User> actualAllUsersBorrowedBookOnGivenDate = this.userServiceImpl
                .getAllUsersBorrowedBookOnGivenDate("2020-03-01");

        assertSame(userList, actualAllUsersBorrowedBookOnGivenDate);
        assertEquals(1, actualAllUsersBorrowedBookOnGivenDate.size());
        verify(this.userRepository).getAllUsersBorrowedBookOnGivenDate("2020-03-01");
    }

    @Test
    void testGetActiveUsersNotBorrowedSingleBook() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(this.userRepository.getActiveUsersNotBorrowedSingleBook()).thenReturn(userList);
        List<User> actualActiveUsersNotBorrowedSingleBook = this.userServiceImpl.getActiveUsersNotBorrowedSingleBook();

        assertSame(userList, actualActiveUsersNotBorrowedSingleBook);
        assertEquals(1, actualActiveUsersNotBorrowedSingleBook.size());
        verify(this.userRepository).getActiveUsersNotBorrowedSingleBook();
    }
}

