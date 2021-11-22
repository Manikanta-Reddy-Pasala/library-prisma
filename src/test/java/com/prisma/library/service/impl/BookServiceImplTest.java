package com.prisma.library.service.impl;

import com.prisma.library.model.Book;
import com.prisma.library.repository.BookRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    Book book = new Book();

    @Before
    public void init() {
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Book1");
        book.setAuthor("JaneDoe");
    }

    @Test
    void testGetAllBooksBorrowedByUserInGivenDateRange() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(this.bookRepository.getAllBooksBorrowedByUserInGivenDateRange("User", "2020-03-01", "2020-03-01"))
                .thenReturn(bookList);
        List<Book> actualAllBooksBorrowedByUserInGivenDateRange = this.bookServiceImpl
                .getAllBooksBorrowedByUserInGivenDateRange("User", "2020-03-01", "2020-03-01");

        assertSame(bookList, actualAllBooksBorrowedByUserInGivenDateRange);
        assertEquals(1, actualAllBooksBorrowedByUserInGivenDateRange.size());
        verify(this.bookRepository).getAllBooksBorrowedByUserInGivenDateRange("User", "2020-03-01", "2020-03-01");
    }

    @Test
    void testGetAllAvailableBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);

//        when(this.bookRepository.getAllBooksNotBorrowedOnce()).thenReturn(bookList);
        when(this.bookRepository.getAllAvailableBooksV3(any())).thenReturn(bookList);

        List<Book> actualAllAvailableBooks = this.bookServiceImpl.getAllAvailableBooks();

        assertEquals(1, actualAllAvailableBooks.size());
        verify(this.bookRepository).getAllAvailableBooksV3(any());
//        verify(this.bookRepository).getAllBooksNotBorrowedOnce();
    }
}

