package com.prisma.library.model.csvLoader;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "First name")
    private String firstName;
    @CsvBindByName(column = "Member since")
    private String memberSince;
    @CsvBindByName(column = "Member till")
    private String memberTill;
    @CsvBindByName(column = "Gender")
    private String gender;

}
