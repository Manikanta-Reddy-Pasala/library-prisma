package com.prisma.library.dataloader;

import com.prisma.library.model.csvLoader.BorrowedRecord;
import com.prisma.library.model.csvLoader.UserRecord;
import com.prisma.library.repository.BookRepository;
import com.prisma.library.repository.BorrowRepository;
import com.prisma.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    void testConvertUserCSVRecordToUserDBRecordWithMemberSince() {
        assertTrue(this.dataLoader
                .convertUserCSVRecordToUserDBRecord(new UserRecord("Name", "Jane", "11/26/2008", null, "M"))
                .isPresent());
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecordWithOutMemberSince() {
        assertFalse(this.dataLoader
                .convertUserCSVRecordToUserDBRecord(new UserRecord("Name", "Jane", null, null, "M"))
                .isPresent());
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecordMock() {
        UserRecord userRecord = mock(UserRecord.class);
        when(userRecord.getMemberTill()).thenReturn("Member Till");
        when(userRecord.getMemberSince()).thenReturn("Member Since");

        assertFalse(this.dataLoader.convertUserCSVRecordToUserDBRecord(userRecord).isPresent());
        verify(userRecord).getMemberSince();
        verify(userRecord).getMemberTill();
    }

    @Test
    void testConvertStringToDate() {
        Date result = this.dataLoader.convertStringToDate("11/26/2008");
        assertNotNull(result);
        assertEquals("Wed Nov 26 00:00:00 CET 2008", result.toString());
    }

    @Test
    void testConvertStringToDateInvalid() {
        assertNull(this.dataLoader.convertStringToDate("2020-03-01"));
    }

    @Test
    void testConvertStringToDateWithNull() {
        assertNull(this.dataLoader.convertStringToDate(null));
    }

    @Test
    void testConvertBorrowCSVRecordToBorrowDBRecordFailed() {
        assertFalse(
                this.dataLoader
                        .convertBorrowCSVRecordToBorrowDBRecord(
                                new BorrowedRecord("Borrower", "Dr", "", "09/12/2010"))
                        .isPresent());
    }

    @Test
    void testConvertBorrowCSVRecordToBorrowDBRecordSuccess() {
        BorrowedRecord borrowedRecord = mock(BorrowedRecord.class);
        when(borrowedRecord.getBorrowedFrom()).thenReturn("09/12/1997");
        when(borrowedRecord.getBorrowedTo()).thenReturn("09/12/2010");

        assertTrue(this.dataLoader.convertBorrowCSVRecordToBorrowDBRecord(borrowedRecord).isPresent());
        verify(borrowedRecord).getBorrowedFrom();
        verify(borrowedRecord).getBorrowedTo();
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecordSuccess() {

        UserRecord userRecord = mock(UserRecord.class);
        when(userRecord.getName()).thenReturn("name");
        when(userRecord.getFirstName()).thenReturn("first");
        when(userRecord.getMemberSince()).thenReturn("09/12/2010");

        assertTrue(this.dataLoader.convertUserCSVRecordToUserDBRecord(userRecord).isPresent());
        verify(userRecord).getName();
        verify(userRecord).getFirstName();
        verify(userRecord).getMemberSince();
    }

    @Test
    void testConvertUserCSVRecordToUserDBRecordFailed() {

        assertFalse(
                this.dataLoader
                        .convertUserCSVRecordToUserDBRecord(
                                new UserRecord("name", "first", null, null, "Male"))
                        .isPresent());
    }
}

