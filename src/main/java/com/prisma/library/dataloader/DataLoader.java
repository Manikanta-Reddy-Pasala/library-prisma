package com.prisma.library.dataloader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.prisma.library.model.Book;
import com.prisma.library.model.Borrow;
import com.prisma.library.model.BorrowId;
import com.prisma.library.model.User;
import com.prisma.library.model.csvLoader.BorrowedRecord;
import com.prisma.library.model.csvLoader.UserRecord;
import com.prisma.library.repository.BookRepository;
import com.prisma.library.repository.BorrowRepository;
import com.prisma.library.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.prisma.library.util.Constants.CSV_DATE_FORMAT;
import static com.prisma.library.util.Util.isNull;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    @Value("classpath:books.csv")
    private Resource bookResource;
    @Value("classpath:user.csv")
    private Resource userResource;
    @Value("classpath:borrowed.csv")
    private Resource borrowResource;

    @Autowired
    public DataLoader(BookRepository bookRepository, UserRepository userRepository,
                      BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;
    }

    /*
    Load all 3 CSV files which are under resources' folder into respective tables
     */
    public void run(ApplicationArguments args) {
        loadBookTable();
        loadUserTable();
        loadBorrowTable();
    }

    public void loadUserTable() {

        try (Reader reader = new BufferedReader(new InputStreamReader(userResource.getInputStream()))) {

            CsvToBean<UserRecord> csvToBean = new CsvToBeanBuilder<UserRecord>(reader)
                    .withType(UserRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(DataLoader::allowLine)
                    .build();

            List<UserRecord> users = csvToBean.parse();

            users.forEach(user -> {
                Optional<User> dbRecord = convertUserCSVRecordToUserDBRecord(user);
                dbRecord.ifPresent(userRepository::save);

            });
        } catch (Exception ex) {
            log.error("Loading User csv file failed with error:" + ex);
        }
    }

    public void loadBookTable() {

        try (Reader reader = new BufferedReader(new InputStreamReader(bookResource.getInputStream()))) {

            CsvToBean<Book> csvToBean = new CsvToBeanBuilder<Book>(reader)
                    .withType(Book.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(DataLoader::allowLine)
                    .build();

            List<Book> books = csvToBean.parse();
            books.forEach(bookRepository::save);

        } catch (Exception ex) {
            log.error("Loading Books csv file failed with error:", ex);
        }
    }

    public void loadBorrowTable() {
        try (Reader reader = new BufferedReader(new InputStreamReader(borrowResource.getInputStream()))) {

            CsvToBean<BorrowedRecord> csvToBean = new CsvToBeanBuilder<BorrowedRecord>(reader)
                    .withType(BorrowedRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(DataLoader::allowLine)
                    .build();

            List<BorrowedRecord> borrowedList = csvToBean.parse();
            borrowedList.forEach(borrowed -> {
                Optional<Borrow> dbRecord = convertBorrowCSVRecordToBorrowDBRecord(borrowed);
                dbRecord.ifPresent(borrowRepository::save);
            });
        } catch (Exception ex) {
            log.error("Loading Borrow csv file failed with error:", ex);
        }
    }

    public Optional<User> convertUserCSVRecordToUserDBRecord(UserRecord userRecord) {

        Date memberSince = convertStringToDate(userRecord.getMemberSince());
        Date memberTill = convertStringToDate(userRecord.getMemberTill());

        User user = null;

        if (memberSince != null) {
            user = User.builder() //
                    // since in borrowed csv file name is represent as (name,firstname)
                    .name(userRecord.getName() + "," + userRecord.getFirstName()) //
                    .gender(userRecord.getGender()) //
                    .memberSince(memberSince) //
                    .memberTill(memberTill) //
                    .build();
        }
        return Optional.ofNullable(user);
    }

    public Date convertStringToDate(String dateString) {
        Date date = null;
        if (dateString != null) {
            try {
                date = new SimpleDateFormat(CSV_DATE_FORMAT).parse(dateString);
            } catch (ParseException e) {
                log.trace("Exception occurred while parsing string to date for the String:" + dateString);
            }
        }
        return date;
    }

    public Optional<Borrow> convertBorrowCSVRecordToBorrowDBRecord(BorrowedRecord borrowed) {

        Date borrowedTo = convertStringToDate(borrowed.getBorrowedTo());
        Date borrowedFrom = convertStringToDate(borrowed.getBorrowedFrom());
        Borrow borrow = null;

        if (!isNull(borrowedTo) && !isNull(borrowedFrom)) {
            BorrowId borrowId = BorrowId.builder() //
                    .borrower(borrowed.getBorrower()) //
                    .bookTitle(borrowed.getBookTitle()) //
                    .build(); //

            borrow = Borrow.builder() //
                    .borrowId(borrowId) //
                    .borrowedTo(borrowedTo) //
                    .borrowedFrom(borrowedFrom)
                    .build(); //
        }
        return Optional.ofNullable(borrow);
    }

    private static boolean allowLine(String[] strings) {
        for (String one : strings) {
            if (one != null && one.length() > 0) {
                return true;
            }
        }
        return false;
    }
}
