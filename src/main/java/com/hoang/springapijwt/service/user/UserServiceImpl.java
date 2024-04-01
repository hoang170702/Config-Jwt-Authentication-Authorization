package com.hoang.springapijwt.service.user;

import com.hoang.springapijwt.models.User;
import com.hoang.springapijwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
            return userRepository.findAll();
    }
}
