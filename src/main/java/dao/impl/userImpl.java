package dao.impl;

import dao.DAO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class userImpl implements DAO<User, Integer> {
    private final SessionFactory factory;

    public userImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User read(Integer integer) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            return session.get(User.class, integer);
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = factory.openSession()){
            String hql = "from User ";
            Query<User> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public void delete(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }

    }

    @Override
    public void create(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void uptade(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }
}
