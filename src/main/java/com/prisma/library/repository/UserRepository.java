package com.prisma.library.repository;

import com.prisma.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u " +
            "WHERE u.name in (SELECT distinct br.borrowId.borrower FROM Borrow br)")
    List<User> getAllUsersBorrowedBook();

    @Query(value = "SELECT u FROM User u " +
            "WHERE u.memberTill IS NULL and u.name NOT IN (SELECT distinct br.borrowId.borrower FROM Borrow br)")
    List<User> getActiveUsersNotBorrowedSingleBook();

    @Query(value = "SELECT * FROM user u " +
            " INNER JOIN (SELECT distinct br.borrower FROM borrow br where br.borrowed_from = :date) j " +
            " on u.name =  j.borrower", nativeQuery = true)
    List<User> getAllUsersBorrowedBookOnGivenDate(@Param("date") String date);
}
