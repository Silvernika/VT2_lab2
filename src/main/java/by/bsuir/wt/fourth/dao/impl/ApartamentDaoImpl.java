package by.bsuir.wt.fourth.dao.impl;

import by.bsuir.wt.fourth.dao.AbstractDao;
import by.bsuir.wt.fourth.dao.Table;
import by.bsuir.wt.fourth.dao.api.ApartamentDao;
import by.bsuir.wt.fourth.dao.mapper.RowMapperFactory;
import by.bsuir.wt.fourth.entity.Apartment;
import by.bsuir.wt.fourth.exeptions.DaoException;

import java.util.List;

public class ApartamentDaoImpl extends AbstractDao<Apartment> implements ApartamentDao {
    private static final String FIND_APPARTMENTS_BY_STATUS = "SELECT * FROM " + Table.APARTMENTS + " WHERE status=? ";
    private static final String FIND_APPARTMENTS_BY_PRICE =
            "SELECT * FROM " + Table.APARTMENTS + " WHERE price BETWEEN ? AND ? ";
    private static final String FIND_APPARTMENTS_BY_TYPE = "SELECT * FROM " + Table.APARTMENTS + " WHERE type=? ";
    private static final String FIND_APPARTMENTS_BY_NUMBER_OF_ROOMS =
            "SELECT * FROM " + Table.APARTMENTS + " WHERE number_of_rooms=? ";
    private static final String FIND_APPARTMENTS_BY_NYMBER_OF_BEDS =
            "SELECT * FROM " + Table.APARTMENTS + " WHERE number_of_beds=? ";
    private static final String SAVE_APARTMENT_QUERY = "INSERT INTO " + Table.APARTMENTS +
            " (status, number_of_rooms,  apartment_number, type, number_of_beds, price, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_APARTMENT_BY_ID=
            "UPDATE " + Table.APARTMENTS + " SET status=? number_of_rooms=? apartment_number=? type=? number_of_beds=? price=? photo=? WHERE id=?";
    private static final String UPDATE_APARTMENT_STATUS_BY_ID=
            "UPDATE " + Table.APARTMENTS + " SET status=?  WHERE id=?";

    public ApartamentDaoImpl() {
        super(RowMapperFactory.getInstance().getApartmentRowMapper(), Table.APARTMENTS);
    }

    @Override
    public int save(Apartment item) throws DaoException {
        return executeInsertQuery(SAVE_APARTMENT_QUERY,item.getStatus()
                ,item.getNumberOfRooms(),item.getApartmentNumber(),
                item.getType(),item.getNumberOfBeds(),item.getPrice(),item.getPhoto());
    }

    @Override
    public List<Apartment> findByStatus(String status) throws DaoException {
        return executeQuery(FIND_APPARTMENTS_BY_STATUS,status);
    }

    @Override
    public List<Apartment> findByType(String type) throws DaoException {
        return executeQuery(FIND_APPARTMENTS_BY_TYPE,type);
    }

    @Override
    public List<Apartment> findByPrice(double from, double to) throws DaoException {
        return executeQuery(FIND_APPARTMENTS_BY_PRICE,from,to);
    }

    @Override
    public List<Apartment> findByNumberOfRooms(int number) throws DaoException {
        return executeQuery(FIND_APPARTMENTS_BY_NUMBER_OF_ROOMS,number);
    }

    @Override
    public List<Apartment> findByNumberOfBeds(int number) throws DaoException {
        return executeQuery(FIND_APPARTMENTS_BY_NYMBER_OF_BEDS,number);
    }

    @Override
    public void updateApartmentStatusById(int id,String status) throws DaoException {
        executeUpdateQuery(UPDATE_APARTMENT_STATUS_BY_ID,status,id);
    }

    @Override
    public void updateApartmentById(int id, Apartment apartment) throws DaoException {
        executeUpdateQuery(UPDATE_APARTMENT_BY_ID,apartment.getStatus(),apartment.getNumberOfRooms(),
                apartment.getApartmentNumber(),apartment.getType(),apartment.getPrice(),apartment.getId(),apartment.getPhoto());
    }
}
