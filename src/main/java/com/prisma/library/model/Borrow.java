package com.prisma.library.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "borrow")
public class Borrow implements Serializable {
    private static final long serialVersionUID = 325259150509024236L;

    @EmbeddedId
    private BorrowId borrowId;
    @Temporal(TemporalType.DATE)
    private Date borrowedFrom;
    @Temporal(TemporalType.DATE)
    private Date borrowedTo;

    @OneToOne
    @JoinColumn(name = "title", insertable = false)
    private Book book;

    @OneToOne
    @JoinColumn(name = "name", insertable = false)
    private User user;
}
