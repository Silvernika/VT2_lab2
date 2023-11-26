package by.bsuir.wt.fourth.service.api;

import by.bsuir.wt.fourth.entity.Role;
import by.bsuir.wt.fourth.exeptions.ServiceException;

import java.util.Optional;

public interface RoleService {

    Optional<Role> retrieveRoleById(int roleId) throws ServiceException;
    Optional<Role> retrieveRoleByRoleName(String roleName) throws ServiceException;

}
