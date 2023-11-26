package by.bsuir.wt.fourth.dao.mapper.impl;

import by.bsuir.wt.fourth.dao.mapper.Column;
import by.bsuir.wt.fourth.dao.mapper.RowMapper;
import by.bsuir.wt.fourth.entity.UserInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInformationRowMapper implements RowMapper<UserInformation> {
    @Override
    public UserInformation map(ResultSet resultSet) throws SQLException {
        UserInformation userInformation = new UserInformation();
        userInformation.setId(resultSet.getInt(Column.ID));
        userInformation.setName(resultSet.getString(Column.USER_INFORMATION_NAME));
        userInformation.setSurname(resultSet.getString(Column.USER_INFORMATION_SURNAME));
        userInformation.setPhone(resultSet.getString(Column.USER_INFORMATION_PHONE));
        return userInformation;
    }
}