package com.prisma.library.model;

import com.opencsv.bean.CsvBindByName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "The Book Details")
public class Book implements Serializable {
    private static final long serialVersionUID = 5560221391479816650L;

    @Id
    @CsvBindByName(column = "Title")
    @ApiModelProperty(value = "Title of the Book", required = true)
    private String title;
    @CsvBindByName(column = "Author")
    @ApiModelProperty(value = "Author Name")
    private String author;
    @CsvBindByName(column = "Genre")
    @ApiModelProperty(value = "Genre Name")
    private String genre;
    @CsvBindByName(column = "Publisher")
    @ApiModelProperty(value = "Publisher name")
    private String publisher;

}
