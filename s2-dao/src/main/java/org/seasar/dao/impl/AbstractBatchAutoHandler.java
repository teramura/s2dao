package org.seasar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.seasar.dao.BeanMetaData;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.PropertyType;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.util.ConnectionUtil;
import org.seasar.framework.util.PreparedStatementUtil;
import org.seasar.framework.util.StatementUtil;

/**
 * @author higa
 *  
 */
public abstract class AbstractBatchAutoHandler extends AbstractAutoHandler {

	public AbstractBatchAutoHandler(DataSource dataSource,
			StatementFactory statementFactory,
			BeanMetaData beanMetaData, PropertyType[] propertyTypes) {

		super(dataSource, statementFactory, beanMetaData, propertyTypes);
	}

	public int execute(Object[] args) throws SQLRuntimeException {
		Connection connection = getConnection();
		try {
			Object[] beans = null;
			if (args[0] instanceof Object[]) {
				beans = (Object[]) args[0];
			} else if (args[0] instanceof List) {
				beans = ((List) args[0]).toArray();
			}
			if (beans == null) {
				throw new IllegalArgumentException("args[0]");
			}
			PreparedStatement ps = prepareStatement(connection);
			try {
				for (int i = 0; i < beans.length; ++i) {
					execute(ps, beans[i]);
				}
				PreparedStatementUtil.executeBatch(ps);
			} finally {
				StatementUtil.close(ps);
			}
			return beans.length;
		} finally {
			ConnectionUtil.close(connection);
		}
	}

	protected void execute(PreparedStatement ps, Object bean) {
		setupBindVariables(bean);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(getCompleteSql(getBindVariables()));
		}
		bindArgs(ps, getBindVariables(), getBindVariableTypes());
		PreparedStatementUtil.addBatch(ps);
	}
}