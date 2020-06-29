package com.zontik.groshiky.dao;

import com.zontik.groshiky.model.Role;
import com.zontik.groshiky.model.Roles;
import com.zontik.groshiky.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Collections;

@Repository
@Transactional
public class UserDao implements IUserDao {

    private final SessionFactory sessionFactory;

    private final IRoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDao(SessionFactory sessionFactory, IRoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.sessionFactory = sessionFactory;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleDao.findRoleUser(Roles.ADMIN);
        user.setRoles(Collections.singletonList(role));
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> findById = session.createQuery("from User where id = :id");
        findById.setParameter("id", id);
        return findById.uniqueResult();

    }

    @Override
    public User findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> findByUsername = session.createQuery("from User where login = :login");
        findByUsername.setParameter("login", login);
        return findByUsername.uniqueResult();
    }
}