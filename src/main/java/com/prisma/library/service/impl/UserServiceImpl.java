package com.prisma.library.service.impl;

import com.prisma.library.exception.NoResultsFoundException;
import com.prisma.library.model.User;
import com.prisma.library.repository.UserRepository;
import com.prisma.library.service.UserService;
import com.prisma.library.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsersBorrowedBook() {
        List<User> users = userRepository.getAllUsersBorrowedBook();

        if(Util.isEmptyOrNull(users)) {
            throw new NoResultsFoundException("There are No Users who borrowed at least one book");
        }
        return users;
    }

    @Override
    public List<User> getAllUsersBorrowedBookOnGivenDate(String date) {
        List<User> users =  userRepository.getAllUsersBorrowedBookOnGivenDate(date);

        if(Util.isEmptyOrNull(users)) {
            throw new NoResultsFoundException("There are No Users who borrowed books on given date");
        }
        return users;
    }

    @Override
    public List<User> getActiveUsersNotBorrowedSingleBook() {
        List<User> users =  userRepository.getActiveUsersNotBorrowedSingleBook();

        if(Util.isEmptyOrNull(users)) {
            throw new NoResultsFoundException("There are No Active who not borrowed at least one book");
        }
        return users;
    }
}
