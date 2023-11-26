package by.bsuir.wt.fourth.dao.impl;

import by.bsuir.wt.fourth.dao.AbstractDao;
import by.bsuir.wt.fourth.dao.Table;
import by.bsuir.wt.fourth.dao.api.UserDao;
import by.bsuir.wt.fourth.dao.mapper.RowMapperFactory;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.exeptions.DaoException;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM " + Table.USER + " WHERE email=? and password=SHA1(?)";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM " + Table.USER + " WHERE email=?";
    private static final String SAVE_USER_QUERY = "INSERT INTO " + Table.USER + " (email, password, role_id, userInformation_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM " + Table.USER + " WHERE id=?";

    public UserDaoImpl() {
        super(RowMapperFactory.getInstance().getUserRowMapper(), Table.USER);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws DaoException {
        return executeQueryForSingleResult(FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY, email, DigestUtils.sha1Hex(password));
    }


    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        return executeQueryForSingleResult(FIND_USER_BY_EMAIL_QUERY, email);
    }



    @Override
    public int save(User user) throws DaoException {
        return executeInsertQuery(SAVE_USER_QUERY, user.getEmail(), user.getPassword(),
                user.getRoleId(), user.getUserInformationId());
    }



    @Override
    public void removeById(int id) throws DaoException {
        executeUpdateQuery(DELETE_USER_QUERY, id);
    }
}
