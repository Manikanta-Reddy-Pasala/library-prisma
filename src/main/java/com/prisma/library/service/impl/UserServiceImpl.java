package com.prisma.library.service.impl;

import com.prisma.library.model.User;
import com.prisma.library.repository.UserRepository;
import com.prisma.library.service.UserService;
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
        return userRepository.getAllUsersBorrowedBook();
    }

    @Override
    public List<User> getAllUsersBorrowedBookOnGivenDate(String date) {
        return userRepository.getAllUsersBorrowedBookOnGivenDate(date);

    }

    @Override
    public List<User> getActiveUsersNotBorrowedSingleBook() {
        return userRepository.getActiveUsersNotBorrowedSingleBook();
    }

}
