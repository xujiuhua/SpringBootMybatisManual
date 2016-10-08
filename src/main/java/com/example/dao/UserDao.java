package com.example.dao;

import com.example.domain.User;
import com.example.util.PageView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author xujiuhua[xujiuhuamoney@163.com]
 * @create 2016-09-29-13:47
 */
public interface UserDao<T> {
    List<User> findUsers();

    void updateLoginInfo(User user);

    List<User> findByPage(PageView pageView);

    List<T> findByPasswordPage(@Param("pageView") PageView pageView, @Param("t") T t);
}
