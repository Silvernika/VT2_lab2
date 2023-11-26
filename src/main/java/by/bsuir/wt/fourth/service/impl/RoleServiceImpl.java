package by.bsuir.wt.fourth.service.impl;

import by.bsuir.wt.fourth.dao.DaoFactory;
import by.bsuir.wt.fourth.dao.api.RoleDao;
import by.bsuir.wt.fourth.entity.Role;
import by.bsuir.wt.fourth.exeptions.DaoException;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.api.RoleService;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {




    @Override
    public Optional<Role> retrieveRoleById(int roleId) throws ServiceException {
        try {
            RoleDao roleDao = DaoFactory.getInstance().getRoleDao();
            Optional<Role> result;
            result = roleDao.findById(roleId);
            return result;
        } catch (DaoException e) {

            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public Optional<Role> retrieveRoleByRoleName(String roleName) throws ServiceException {
        try {
            RoleDao roleDao = DaoFactory.getInstance().getRoleDao();
            Optional<Role> result;
            result = roleDao.findByName(roleName);
            return result;
        } catch (DaoException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }
}