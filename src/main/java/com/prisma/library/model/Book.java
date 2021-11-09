package com.prisma.library.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "book")
public class Book implements Serializable {
    private static final long serialVersionUID = 5560221391479816650L;

    @Id
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Author")
    private String author;
    @CsvBindByName(column = "Genre")
    private String genre;
    @CsvBindByName(column = "Publisher")
    private String publisher;

}
