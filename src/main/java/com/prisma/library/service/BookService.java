package com.prisma.library.service;


import com.prisma.library.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooksBorrowedByUserInGivenDateRange(String user, String fromDate, String toDate);

    List<Book> getAllAvailableBooks();
}
