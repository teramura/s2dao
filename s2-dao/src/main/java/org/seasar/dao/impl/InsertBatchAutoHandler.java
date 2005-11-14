package org.seasar.dao.impl;

import javax.sql.DataSource;

import org.seasar.dao.BeanMetaData;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.PropertyType;

/**
 * @author higa
 *  
 */
public class InsertBatchAutoHandler extends AbstractBatchAutoHandler {

	public InsertBatchAutoHandler(DataSource dataSource,
			StatementFactory statementFactory,
			BeanMetaData beanMetaData, PropertyType[] propertyTypes) {

		super(dataSource, statementFactory, beanMetaData, propertyTypes);
	}

	protected void setupBindVariables(Object bean) {
		setupInsertBindVariables(bean);
	}
}