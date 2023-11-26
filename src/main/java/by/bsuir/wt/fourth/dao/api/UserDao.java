package by.bsuir.wt.fourth.dao.api;

import by.bsuir.wt.fourth.dao.Dao;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.exeptions.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User> {


    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;


    Optional<User> findByEmail(String email) throws DaoException;
}
