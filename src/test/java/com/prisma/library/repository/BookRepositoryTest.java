package com.prisma.library.repository;

import com.prisma.library.model.Book;
import com.prisma.library.model.Borrow;
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

@ContextConfiguration(classes = {BookRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.prisma.library.model"})
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowRepository borrowRepository;

    @Test
    void testGetAllAvailableBooks() throws ParseException {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Name");
        book.setAuthor("JaneDoe");

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Book");
        book1.setAuthor("JaneDoe");

        Borrow borrow = new Borrow();
        borrow.setBorrower("Name");
        borrow.setBookTitle("Book");
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.bookRepository.save(book);
        this.bookRepository.save(book1);
        this.borrowRepository.save(borrow);

        assertEquals(1, this.bookRepository.getAllAvailableBooks("2021-01-01").size());
    }

    @Test
    void testGetAllAvailableBooksEmptyResult() throws ParseException {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Name");
        book.setAuthor("JaneDoe");

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Book");
        book1.setAuthor("JaneDoe");

        Borrow borrow = new Borrow();
        borrow.setBorrower("Name");
        borrow.setBookTitle("Book");
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"));

        this.bookRepository.save(book);
        this.bookRepository.save(book1);
        this.borrowRepository.save(borrow);

        assertTrue(this.bookRepository.getAllAvailableBooks("2021-01-01").isEmpty());
    }

    @Test
    void testGetAllBooksNotBorrowedOnce() throws ParseException {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Book1");
        book.setAuthor("JaneDoe");

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Book2");
        book1.setAuthor("JaneDoe");

        Borrow borrow = new Borrow();
        borrow.setBorrower("Name");
        borrow.setBookTitle("Book");
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"));

        this.bookRepository.save(book);
        this.bookRepository.save(book1);
        this.borrowRepository.save(borrow);

        assertEquals(2, this.bookRepository.getAllBooksNotBorrowedOnce().size());
    }

    @Test
    void testGetAllBooksNotBorrowedOnceEmptyResult() throws ParseException {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Book1");
        book.setAuthor("JaneDoe");

        Borrow borrow = new Borrow();
        borrow.setBorrower("Name");
        borrow.setBookTitle("Book1");
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"));

        this.bookRepository.save(book);
        this.borrowRepository.save(borrow);

        assertTrue(this.bookRepository.getAllBooksNotBorrowedOnce().isEmpty());
    }

    @Test
    void testGetAllBooksBorrowedByUserInGivenDateRange() throws ParseException {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Dr");
        book.setAuthor("JaneDoe");

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Book");
        book1.setAuthor("JaneDoe");

        Borrow borrow = new Borrow();
        borrow.setBorrower("Name");
        borrow.setBookTitle("Book");

        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        this.bookRepository.save(book);
        this.bookRepository.save(book1);
        this.borrowRepository.save(borrow);

        assertEquals(1, this.bookRepository.getAllBooksBorrowedByUserInGivenDateRange("Name", "2020-01-01", "2020-01-01").size());
    }

    @Test
    void testGetAllBooksBorrowedByUserInGivenDateRangeEmptyResult() {
        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Dr");
        book.setAuthor("JaneDoe");

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Dr");
        book1.setAuthor("JaneDoe");
        this.bookRepository.save(book);
        this.bookRepository.save(book1);
        assertTrue(this.bookRepository.getAllBooksBorrowedByUserInGivenDateRange("foo", "foo", "foo").isEmpty());
    }
}

