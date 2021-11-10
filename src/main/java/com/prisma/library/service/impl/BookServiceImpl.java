package com.prisma.library.service.impl;

import com.prisma.library.model.Book;
import com.prisma.library.repository.BookRepository;
import com.prisma.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooksBorrowedByUserInGivenDateRange(String user, String fromDate, String toDate) {
        return bookRepository.getAllBooksBorrowedByUserInGivenDateRange(user, fromDate, toDate);
    }

    @Override
    public List<Book> getAllAvailableBooks() {

        // Gets the current date
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Book> books = bookRepository.getAllAvailableBooks(date.format(formatter));

        List<Book> notBorrowedOnce = bookRepository.getAllBooksNotBorrowedOnce();

        books.addAll(notBorrowedOnce);

        return books;
    }

}
