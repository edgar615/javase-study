package com.edgar.core.repository;

import com.edgar.core.repository.transaction.Transaction;
import com.edgar.core.repository.transaction.TransactionConfig;
import com.edgar.core.repository.transaction.TransactionFactory;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.Path;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DAO的父类
 *
 * @param <PK>
 * @param <T>
 * @author Edgar Zhang
 * @version 1.0
 */
public abstract class AbstractDaoTemplate<PK, T> implements
        BaseDao<PK, T> {

    private final Class<T> entityBeanType;

    private final Class<T> primaryKeyType;

    @SuppressWarnings("unchecked")
    public AbstractDaoTemplate() {
        this.primaryKeyType = (Class<T>) (((ParameterizedType) (getClass()
                .getGenericSuperclass())).getActualTypeArguments()[0]);
        this.entityBeanType = (Class<T>) (((ParameterizedType) (getClass()
                .getGenericSuperclass())).getActualTypeArguments()[1]);
    }

    /**
     * 返回实体类的QueryDSL查询类
     *
     * @return RelationalPathBase
     */
    public abstract RelationalPathBase<?> getPathBase();

    public String getDataSourceKey() {
        return Constants.DEFAULT;
    }

    public String getConfigurationKey() {
        return Constants.DEFAULT;
    }

    public TransactionConfig config() {
        return new TransactionConfig(DataSourceFactory.createDataSource(getDataSourceKey()), ConfigurationFactory.createConfiguration(getConfigurationKey()), getPathBase());
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    public Class<T> getPrimaryKeyType() {
        return primaryKeyType;
    }

    protected RowMapper<T> getRowMapper() {
        return BeanPropertyRowMapper.newInstance(entityBeanType);
    }

    @Override
    public List<T> query(final QueryExample example) {
        return query(example, getRowMapper());
    }

    @Override
    public <E> List<E> query(final QueryExample example, RowMapper<E> rowMapper) {
        Validate.notNull(example);
        Validate.notNull(rowMapper);
        Transaction transaction = TransactionFactory.createQueryTransaction(config(), example, rowMapper);
        return transaction.execute();
    }

    @Override
    public <E> List<E> querySingleColumn(QueryExample example,
                                         Class<E> elementType) {
        Validate.notNull(example);
        Validate.notEmpty(example.getFields(), "fields cannot be null");
        Transaction transaction = TransactionFactory.createQueryTransaction(config(), example, new SingleColumnRowMapper<E>(elementType));
        return transaction.execute();
    }

    @Override
    public Pagination<T> pagination(QueryExample example, int page, int pageSize) {
        return pagination(example, page, pageSize, getRowMapper());
    }

    @Override
    public <E> Pagination<E> pagination(QueryExample example, int page, int pageSize, RowMapper<E> rowMapper) {
        Validate.notNull(example);
        Validate.notNull(rowMapper);
        Transaction transaction = TransactionFactory.createPageTransaction(config(), example, page, pageSize, rowMapper);
        return transaction.execute();
    }

    @Override
    public T get(PK pk) {
        Validate.notNull(pk, "primaryKey cannot be null");
        QueryExample example = QueryExampleHelper.createExampleByPk(getPathBase(), pk);
        return uniqueResult(example);
    }

    @Override
    public T get(PK pk, List<String> fields) {
        Validate.notNull(pk, "primaryKey cannot be null");
        QueryExample example = QueryExampleHelper.createExampleByPk(getPathBase(), pk);
        example.addFields(fields);
        return uniqueResult(example);
    }

    @Override
    public T uniqueResult(QueryExample example) {
        List<T> records = query(example);
        if (records.isEmpty()) {
            return null;
        }
        Validate.isTrue(records.size() == 1);
        return records.get(0);
    }

    @Override
    public Long insert(List<T> domains) {
        Validate.notEmpty(domains, "domains cannot be empty");
        Transaction transaction = TransactionFactory.createDefaultBatchInsertTransaction(config(), domains);
        return transaction.execute();
    }

    @Override
    public Long insert(T domain) {
        Transaction transaction = TransactionFactory.createDefaultInsertTransaction(config(), domain);
        return transaction.execute();
    }

    @Override
    public <K> K insertWithKey(T domain, Class<K> keyClass) {
        Transaction transaction = TransactionFactory.createDefaultInsertWithKeyTransaction(config(), domain, keyClass);
        return transaction.execute();
    }

    @Override
    public Long deleteByPk(PK pk) {
        Validate.notNull(pk, "primaryKey cannot be null");
        QueryExample example = QueryExampleHelper.createExampleByPk(getPathBase(), pk);
        return delete(example);
    }

    @Override
    public Long deleteByPkAndVersion(PK pk, long updatedTime) {
        Validate.notNull(pk, "primaryKey cannot be null");
        QueryExample example = QueryExampleHelper.createExampleByPk(getPathBase(), pk);
        example.equalsTo(Constants.UPDATED_TIME, new Timestamp(updatedTime));
        long result = delete(example);
        if (result < 1) {
            throw new StaleObjectStateException();
        }
        return result;
    }

    @Override
    public Long delete(final QueryExample example) {
        Transaction transaction = TransactionFactory.createDeleteTransaction(config(), example);
        return transaction.execute();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long update(final T domain, QueryExample example) {
        Validate.notNull(domain, "domain cannot be null");
        if (example == null) {
            QueryExample.newInstance();
        }
        Transaction transaction = TransactionFactory.createDefaultUpdateTransaction(config(), domain, example);
        return transaction.execute();
    }

    @Override
    public Long update(final T domain) {
        Validate.notNull(domain, "domain cannot be null");
        QueryExample example = QueryExampleHelper.createUpdateExample(getPathBase(), domain);
        return update(domain, example);
    }

    @Override
    public Long updateWithVersion(final T domain) {
        Validate.notNull(domain, "domain cannot be null");
        QueryExample example = createUpdateExampleWithVersion(domain);
        Long result = update(domain, example);
        if (result < 1) {
            throw new StaleObjectStateException();
        }
        return result;
    }

    private QueryExample createUpdateExampleWithVersion(T domain) {
        Set<String> pks = new HashSet<String>();
        for (Path<?> path : getPathBase().getPrimaryKey().getLocalColumns()) {
            pks.add(path.getMetadata().getName());
        }
        pks.add(Constants.UPDATED_TIME);

        return QueryExampleHelper.createUpdateExample(getPathBase(), domain, pks);
    }

}
