package com.prisma.library.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class BorrowId implements Serializable {

    private static final long serialVersionUID = 325259050509024236L;

    private String borrower;
    private String bookTitle;

}
