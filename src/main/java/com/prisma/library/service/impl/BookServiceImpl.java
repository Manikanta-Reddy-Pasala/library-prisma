package com.prisma.library.service.impl;

import com.prisma.library.exception.NoResultsFoundException;
import com.prisma.library.model.Book;
import com.prisma.library.repository.BookRepository;
import com.prisma.library.service.BookService;
import com.prisma.library.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooksBorrowedByUserInGivenDateRange(String userName, String fromDate, String toDate) {
        List<Book> books = bookRepository.getAllBooksBorrowedByUserInGivenDateRange(userName, fromDate, toDate);

        if(Util.isEmptyOrNull(books)) {
            throw new NoResultsFoundException("No Results Found for the Given Date Range");
        }
        return books;
    }

    @Override
    public List<Book> getAllAvailableBooks() {

        List<Book> books = bookRepository.getAllAvailableBooks(Util.getTodayDate());
        List<Book> notBorrowedOnce = bookRepository.getAllBooksNotBorrowedOnce();

        if(!Util.isEmptyOrNull(books)) {
            books.addAll(notBorrowedOnce);
        } else {
            books = notBorrowedOnce;
        }

        if(Util.isEmptyOrNull(books)) {
            throw new NoResultsFoundException("There are No Available books found");
        }
        return books;
    }
}
