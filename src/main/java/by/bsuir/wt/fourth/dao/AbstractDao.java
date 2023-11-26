package by.bsuir.wt.fourth.dao;

import by.bsuir.wt.fourth.dao.mapper.RowMapper;
import by.bsuir.wt.fourth.entity.Identifiable;
import by.bsuir.wt.fourth.exeptions.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> extends AbstractQueryExecutor<T> implements Dao<T> {
    private final String tableName;

    public AbstractDao(RowMapper<T> rowMapper, String tableName) {
        super(rowMapper);
        this.tableName = tableName;
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = "SELECT * FROM " + tableName;
        return executeQuery(query);
    }

    @Override
    public Optional<T> findById(int id) throws DaoException {
        String query = "SELECT * FROM " + tableName + " WHERE id=?";
        return executeQueryForSingleResult(query, id);
    }

    @Override
    public void removeById(int id) throws DaoException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE id=?";
        executeUpdateQuery(deleteQuery, id);
    }
}
