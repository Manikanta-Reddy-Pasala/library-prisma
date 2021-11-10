package com.prisma.library.dataLoader;

import com.prisma.library.model.Book;
import com.prisma.library.model.Borrow;
import com.prisma.library.model.BorrowId;
import com.prisma.library.model.User;
import com.prisma.library.model.csvLoader.BorrowedModel;
import com.prisma.library.model.csvLoader.UserModel;
import com.prisma.library.repository.BookRepository;
import com.prisma.library.repository.BorrowRepository;
import com.prisma.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DataLoader.class})
@ExtendWith(SpringExtension.class)
class DataLoaderTest {
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BorrowRepository borrowRepository;

    @Autowired
    private DataLoader dataLoader;

    @MockBean
    private Resource resource;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testRun() throws ParseException {
        User user = new User();
        user.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        user.setGender("Gender");
        user.setName("Name");
        user.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        when(this.userRepository.save(any())).thenReturn(user);

        BorrowId borrowId = new BorrowId();
        borrowId.setBorrower("Borrower");
        borrowId.setBookTitle("Dr");

        Book book = new Book();
        book.setPublisher("Publisher");
        book.setGenre("Genre");
        book.setTitle("Dr");
        book.setAuthor("JaneDoe");

        User user1 = new User();
        user1.setMemberTill(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        user1.setGender("Gender");
        user1.setName("Name");
        user1.setMemberSince(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));

        Borrow borrow = new Borrow();
        borrow.setBorrowId(borrowId);
        borrow.setBorrowedFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBorrowedTo(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
        borrow.setBook(book);
        borrow.setUser(user1);
        when(this.borrowRepository.save(any())).thenReturn(borrow);

        Book book1 = new Book();
        book1.setPublisher("Publisher");
        book1.setGenre("Genre");
        book1.setTitle("Dr");
        book1.setAuthor("JaneDoe");
        when(this.bookRepository.save(any())).thenReturn(book1);
        this.dataLoader.run(new DefaultApplicationArguments("foo", "foo", "foo"));

        verify(this.userRepository, atLeast(1)).save(any());
        verify(this.borrowRepository, atLeast(1)).save(any());
        verify(this.bookRepository, atLeast(1)).save(any());
    }


    @Test
    void testConvertUserCSVRecordToUserDBRecordEmptyCheck() {
        assertFalse(this.dataLoader
                .convertUserCSVRecordToUserDBRecord(
                        new UserModel("Name", "Jane", null,
                                "03/03/2024", "Gender"))
                .isPresent());
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecord() {
        Optional<User> actual = this.dataLoader
                .convertUserCSVRecordToUserDBRecord(new UserModel("Name", "Jane", "03/03/2020", "03/03/2024", "Gender"));

        assertTrue(actual.isPresent());
        assertEquals("Name,Jane", actual.get().getName());
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecord2() {
        UserModel userModel = mock(UserModel.class);
        when(userModel.getMemberTill()).thenReturn("03/03/2024");
        when(userModel.getMemberSince()).thenReturn("03/03/2020");

        assertTrue(this.dataLoader.convertUserCSVRecordToUserDBRecord(userModel).isPresent());
        verify(userModel).getMemberSince();
        verify(userModel).getMemberTill();
    }

    @Test
    void testConvertStringToDateInvalidDateFormat() {
        assertNull(this.dataLoader.convertStringToDate("2020-03-01"));
    }

    @Test
    void testConvertStringToDateNullCheck() {
        assertNull(this.dataLoader.convertStringToDate(null));
    }

    @Test
    void testConvertStringToDate2() {
        Date actualDate = this.dataLoader.convertStringToDate("03/03/2020");
        assertEquals("2020-03-03", (new SimpleDateFormat("yyyy-MM-dd")).format(actualDate));
    }

    @Test
    void testConvertBorrowCSVRecordToBorrowDBRecordEmptyCheck() {
        BorrowedModel borrowedModel = mock(BorrowedModel.class);
        when(borrowedModel.getBorrowedFrom()).thenReturn("03/03/2020");

        assertFalse(this.dataLoader.convertBorrowCSVRecordToBorrowDBRecord(borrowedModel).isPresent());
        verify(borrowedModel).getBorrowedFrom();
        verify(borrowedModel).getBorrowedTo();
    }

    @Test
    void testConvertBorrowCSVRecordToBorrowDBRecord() {
        BorrowedModel borrowedModel = mock(BorrowedModel.class);
        when(borrowedModel.getBorrowedFrom()).thenReturn("03/03/2020");
        when(borrowedModel.getBorrowedTo()).thenReturn("03/04/2020");

        assertTrue(this.dataLoader.convertBorrowCSVRecordToBorrowDBRecord(borrowedModel).isPresent());
        verify(borrowedModel).getBorrowedFrom();
        verify(borrowedModel).getBorrowedTo();
    }
}

