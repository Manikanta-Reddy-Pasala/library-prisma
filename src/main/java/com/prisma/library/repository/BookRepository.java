package com.prisma.library.repository;

import com.prisma.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT * FROM " +
            //sub query gets for each book maximum returned date entry
            "(SELECT br.book_title, br.borrowed_to, bi.MaxDate FROM Borrow br " +
            "INNER JOIN " +
            " (select book_title, max(borrowed_to) as MaxDate from Borrow GROUP BY book_title) bi " +
            " on br.book_title = bi.book_title and br.borrowed_to = bi.MaxDate) bij " +
            //filter with today's date to find out whether book is available or not
             " where bij.MaxDate <= :date) j" +
            //finally, join on title of the book
            " on b.title =  j.book_title", nativeQuery = true)
    List<Book> getAllAvailableBooks(String date);

    @Query(value = "SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT distinct br.book_title FROM Borrow br " +
            "where br.borrower = :user and br.borrowed_from >= :fromDate and  br.borrowed_from <= :toDate) j" +
            " on b.title =  j.book_title",
            nativeQuery = true)
    List<Book> getAllBooksBorrowedByUserInGivenDateRange(String user, String fromDate, String toDate);
}
