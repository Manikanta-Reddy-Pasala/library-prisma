package com.prisma.library.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 3252591505029724236L;

    @Id
    private String name;
    @Temporal(TemporalType.DATE)
    private Date memberSince;
    @Temporal(TemporalType.DATE)
    private Date memberTill;
    private String gender;

}
