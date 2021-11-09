package com.prisma.library.dataLoader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.prisma.library.model.Book;
import com.prisma.library.model.Borrow;
import com.prisma.library.model.BorrowId;
import com.prisma.library.model.User;
import com.prisma.library.model.csvLoader.BorrowedModel;
import com.prisma.library.model.csvLoader.UserModel;
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

import static java.lang.String.format;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowRepository borrowRepository;

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
    Load all 3 CSV files which are under resources folder into respective tables
     */
    public void run(ApplicationArguments args) {
        loadBookTable();
        loadUserTable();
        loadBorrowTable();
    }

    private void loadUserTable() {

        try (Reader reader = new BufferedReader(new InputStreamReader(userResource.getInputStream()))) {

            CsvToBean<UserModel> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(UserModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(strings -> {
                                for (String one : strings) {
                                    if (one != null && one.length() > 0) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                    )
                    .build();

            List<UserModel> users = csvToBean.parse();

            users.stream().forEach(record -> {
                Optional<User> dbRecord = convertUserCSVRecordToUserDBRecord(record);
                if (dbRecord.isPresent()) {
                    userRepository.save(dbRecord.get());
                }

            });
        } catch (Exception ex) {
            log.error(format("Loading User csv file failed with error:", ex));
        }
    }

    private void loadBookTable() {

        try (Reader reader = new BufferedReader(new InputStreamReader(bookResource.getInputStream()))) {

            CsvToBean<Book> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Book.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(strings -> {
                                for (String one : strings) {
                                    if (one != null && one.length() > 0) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                    )
                    .build();

            List<Book> books = csvToBean.parse();
            books.stream().forEach(record -> {
                bookRepository.save(record);
            });

        } catch (Exception ex) {
            log.error(format("Loading Books csv file failed with error:", ex));
        }
    }

    private void loadBorrowTable() {
        try (Reader reader = new BufferedReader(new InputStreamReader(borrowResource.getInputStream()))) {

            CsvToBean<BorrowedModel> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(BorrowedModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withFilter(strings -> {
                                for (String one : strings) {
                                    if (one != null && one.length() > 0) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                    )
                    .build();

            List<BorrowedModel> borrowedList = csvToBean.parse();
            borrowedList.forEach(record -> {
                Optional<Borrow> dbRecord = convertBorrowCSVRecordToBorrowDBRecord(record);
                if (dbRecord.isPresent()) {
                    borrowRepository.save(dbRecord.get());
                }
            });
        } catch (Exception ex) {
            log.error(format("Loading Borrow csv file failed with error:", ex));
        }
    }

    private Optional<User> convertUserCSVRecordToUserDBRecord(UserModel record) {

        Date memberSince = convertStringToDate(record.getMemberSince());
        Date memberTill = convertStringToDate(record.getMemberTill());

        User user = null;

        if (memberSince != null) {
            user = User.builder() //
                    // since in borrowed csv file name is represent as (name,firstname)
                    .name(record.getName() + "," + record.getFirstName()) //
                    .gender(record.getGender()) //
                    .memberSince(memberSince) //
                    .memberTill(memberTill) //
                    .build();
        }
        return Optional.ofNullable(user);
    }

    private Date convertStringToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        } catch (ParseException e) {
            log.trace(format("Exception occurred while parsing string to date for the String:", dateString));
        }
        return date;
    }

    private Optional<Borrow> convertBorrowCSVRecordToBorrowDBRecord(BorrowedModel record) {

        Date borrowedTo = convertStringToDate(record.getBorrowedTo());
        Date borrowedFrom = convertStringToDate(record.getBorrowedFrom());
        Borrow borrow = null;

        if (borrowedTo != null && borrowedFrom != null) {
            BorrowId borrowId = BorrowId.builder() //
                    .borrower(record.getBorrower()) //
                    .bookTitle(record.getBookTitle()) //
                    .build(); //

            borrow = Borrow.builder() //
                    .borrowId(borrowId) //
                    .borrowedTo(borrowedTo) //
                    .borrowedFrom(borrowedFrom)
                    .build(); //
        }
        return Optional.ofNullable(borrow);
    }
}
