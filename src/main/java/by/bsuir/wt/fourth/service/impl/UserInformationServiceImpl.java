package by.bsuir.wt.fourth.service.impl;

import by.bsuir.wt.fourth.dao.DaoFactory;
import by.bsuir.wt.fourth.dao.impl.UserInformationDaoImpl;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.entity.UserInformation;
import by.bsuir.wt.fourth.exeptions.DaoException;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.api.UserInformationService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserInformationServiceImpl implements UserInformationService {


    @Override
    public Optional<UserInformation> retrieveUserInformationById(int userInformationId) throws ServiceException {
        try {
            UserInformationDaoImpl userInformationDao = DaoFactory.getInstance().getUserInformationDao();
            Optional<UserInformation> result;
            result = userInformationDao.findById(userInformationId);
            return result;
        } catch (DaoException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserInformation> getUserInformationFromUsers(List<User> users) throws ServiceException {
        List<UserInformation> userInformation = new LinkedList<>();
        try {
            for (User user : users) {
                Optional<UserInformation> information = retrieveUserInformationById(user.getUserInformationId());
                if (information.isPresent()) {
                    if (!userInformation.contains(information.get())) {
                        userInformation.add(information.get());
                    }
                }
            }
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return userInformation;
    }
}
