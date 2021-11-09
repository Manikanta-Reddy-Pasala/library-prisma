package com.prisma.library.service;


import com.prisma.library.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsersBorrowedBook();

    List<User> getAllUsersBorrowedBookOnGivenDate(String date);

    List<User> getActiveUsersNotBorrowedSingleBook();
}
