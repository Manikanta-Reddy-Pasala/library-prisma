package com.prisma.library.repository;

import com.prisma.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT * FROM " +
            //sub query: gets for each book maximum returned date and compare
            // with today's date if it is less than today return it
            " (select distinct book_title, max(borrowed_to) as MaxDate from Borrow GROUP BY book_title) bi " +
            " where bi.MaxDate <= :date) bij " +
            //finally, join on title of the book
            " on b.title =  bij.book_title ",
            nativeQuery = true)
    List<Book> getAllAvailableBooks(String date);


    @Query(value = "select DISTINCT m.title, m.author, m.genre, m.publisher from (SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT * FROM " +
            //sub query: gets for each book maximum returned date and compare
            // with today's date if it is less than today return it
            " (select distinct book_title, max(borrowed_to) as MaxDate from Borrow GROUP BY book_title) bi " +
            " where bi.MaxDate <= :date) bij " +
            //finally, join on title of the book
            " on b.title =  bij.book_title) m " +
            " FULL JOIN " +
            "SELECT DISTINCT b.title, b.author, b.genre, b.publisher FROM book b WHERE b.title NOT IN (SELECT DISTINCT book_title FROM Borrow)" +
            " on m.title = n.title ",
            nativeQuery = true)
    List<Book> getAllAvailableBooksV2(String date);


    @Query(value = "select DISTINCT m.title, m.author, m.genre, m.publisher from (SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT * FROM " +
            //sub query: gets for each book maximum returned date and compare
            // with today's date if it is less than today return it
            " (select distinct book_title, max(borrowed_to) as MaxDate from Borrow GROUP BY book_title) bi " +
            " where bi.MaxDate <= :date) bij " +
            //finally, join on title of the book
            " on b.title =  bij.book_title) m " +
            " UNION " +
            "SELECT DISTINCT b1.title, b1.author, b1.genre, b1.publisher FROM book b1 WHERE b1.title NOT IN (SELECT DISTINCT book_title FROM Borrow)" ,
            nativeQuery = true)
    List<Book> getAllAvailableBooksV3(String date);

    @Query(value = "SELECT * FROM book b WHERE b.title NOT IN (SELECT DISTINCT book_title FROM Borrow)",
            nativeQuery = true)
    List<Book> getAllBooksNotBorrowedOnce();

    @Query(value = "SELECT * FROM book b " +
            "INNER JOIN " +
            "(SELECT distinct br.book_title FROM Borrow br " +
            "where br.borrower = :user and br.borrowed_from >= :fromDate and  br.borrowed_from <= :toDate) j" +
            " on b.title =  j.book_title",
            nativeQuery = true)
    List<Book> getAllBooksBorrowedByUserInGivenDateRange(String user, String fromDate, String toDate);

}
