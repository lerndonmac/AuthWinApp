package dao;

import java.util.List;

public interface DAO<C,T> {
     C read(T t);
     List<C> readAll();
     void delete(C c);
     void create(C c);
     void uptade(C c);
}
