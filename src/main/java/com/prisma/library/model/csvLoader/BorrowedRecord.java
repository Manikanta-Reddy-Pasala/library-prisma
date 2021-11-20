package com.prisma.library.model.csvLoader;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BorrowedRecord {
    @CsvBindByName(column = "Borrower")
    private String borrower;
    @CsvBindByName(column = "Book")
    private String bookTitle;
    @CsvBindByName(column = "borrowed from")
    private String borrowedFrom;
    @CsvBindByName(column = "borrowed to")
    private String borrowedTo;

}
