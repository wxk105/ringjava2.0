package com.jumu.ring.service;

import com.jumu.ring.dao.UserDao;
import com.jumu.ring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User save(User user) {
        return userDao.save(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
