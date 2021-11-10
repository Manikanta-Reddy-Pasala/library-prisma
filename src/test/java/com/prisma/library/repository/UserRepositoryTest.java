package com.prisma.library.repository;

import com.prisma.library.model.Borrow;
import com.prisma.library.model.BorrowId;
import com.prisma.library.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {UserRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.prisma.library.model"})
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowRepository borrowRepository;

    @Test
    void testGetAllUsersBorrowedBook() throws ParseException {
        User user = new User();
        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        BorrowId id = new BorrowId();
        id.setBorrower("Name");
        id.setBookTitle("Book");
        Borrow borrow = new Borrow();
        borrow.setBorrowId(id);
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.userRepository.save(user);
        this.borrowRepository.save(borrow);
        assertEquals(1, this.userRepository.getAllUsersBorrowedBook().size());
    }

    @Test
    void testGetAllUsersBorrowedBookWithNoMatch() throws ParseException {
        User user = new User();
        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        BorrowId id = new BorrowId();
        id.setBorrower("name");
        id.setBookTitle("Book");
        Borrow borrow = new Borrow();
        borrow.setBorrowId(id);
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.userRepository.save(user);
        this.borrowRepository.save(borrow);
        assertTrue(this.userRepository.getAllUsersBorrowedBook().isEmpty());
    }

    @Test
    void testGetActiveUsersNotBorrowedSingleBook() throws ParseException {
        User user = new User();
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.userRepository.save(user);
        assertEquals(1, this.userRepository.getActiveUsersNotBorrowedSingleBook().size());
    }

    @Test
    void testGetActiveUsersNotBorrowedSingleBookEmptyResults() throws ParseException {
        User user = new User();

        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2010-01-01"));

        this.userRepository.save(user);
        assertTrue(this.userRepository.getActiveUsersNotBorrowedSingleBook().isEmpty());
    }

    @Test
    void testGetAllUsersBorrowedBookOnGivenDate() throws ParseException {
        User user = new User();
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        BorrowId id = new BorrowId();
        id.setBorrower("Name");
        id.setBookTitle("Book");
        Borrow borrow = new Borrow();
        borrow.setBorrowId(id);
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.userRepository.save(user);
        this.borrowRepository.save(borrow);
        assertEquals(1, this.userRepository.getAllUsersBorrowedBookOnGivenDate("2020-01-01").size());
    }

    @Test
    void testGetAllUsersBorrowedBookOnGivenDateEmptyResult() throws ParseException {
        User user = new User();
        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.userRepository.save(user);
        assertTrue(this.userRepository.getAllUsersBorrowedBookOnGivenDate("foo").isEmpty());
    }
}

