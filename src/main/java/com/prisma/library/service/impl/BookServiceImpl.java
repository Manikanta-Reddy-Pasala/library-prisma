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

        /*
        implemented in 3 ways
        1) getAllAvailableBooks
        a) get all available books which are borrowed at least once
        b) get all available books which are not borrowed at least once

        2) getAllAvailableBooksV2
        Since full joins are not supported in H2 database written two queries separately

        3) getAllAvailableBooksV3
        used UNION in the place of full joins
         */
        List<Book> books = bookRepository.getAllAvailableBooksV3(Util.getTodayDate());
//        List<Book> notBorrowedOnce = bookRepository.getAllBooksNotBorrowedOnce();

//        if(!Util.isEmptyOrNull(books)) {
//            books.addAll(notBorrowedOnce);
//        } else {
//            books = notBorrowedOnce;
//        }

        if(Util.isEmptyOrNull(books)) {
            throw new NoResultsFoundException("There are No Available books found");
        }
        return books;
    }
}
