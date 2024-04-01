package com.hoang.springapijwt.service.user;

import com.hoang.springapijwt.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
}
