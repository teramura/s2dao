/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dao.impl;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.seasar.dao.BeanAnnotationReader;
import org.seasar.dao.ColumnNaming;
import org.seasar.dao.Dbms;
import org.seasar.dao.PropertyTypeFactory;
import org.seasar.dao.ValueTypeFactory;
import org.seasar.extension.jdbc.PropertyType;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.util.DatabaseMetaDataUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * {@link PropertyTypeFactory}の実装クラスです。
 * <p>
 * データベースのメタデータ情報を利用して{@link PropertyType}を作成します。
 * </p>
 * 
 * @author taedium
 */
public class PropertyTypeFactoryImpl extends AbstractPropertyTypeFactory {

    private static Logger logger = Logger
            .getLogger(PropertyTypeFactoryImpl.class);

    protected Dbms dbms;

    protected DatabaseMetaData databaseMetaData;

    /**
     * インスタンスを構築します。
     * 
     * @param beanClass Beanのクラス
     * @param beanAnnotationReader Beanのアノテーションリーダ
     * @param valueTypeFactory {@link ValueType}のファクトリ
     * @param columnNaming カラムのネーミング
     * @param databaseMetaData データベースのメタ情報
     * @param dbms DBMS
     */
    public PropertyTypeFactoryImpl(Class beanClass,
            BeanAnnotationReader beanAnnotationReader,
            ValueTypeFactory valueTypeFactory, ColumnNaming columnNaming,
            DatabaseMetaData databaseMetaData, Dbms dbms) {
        super(beanClass, beanAnnotationReader, valueTypeFactory, columnNaming);
        this.dbms = dbms;
        this.databaseMetaData = databaseMetaData;
    }

    public PropertyType[] createBeanPropertyTypes(String tableName) {
        List list = new ArrayList();
        BeanDesc beanDesc = getBeanDesc();
        boolean found = false;
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (isRelation(pd)) {
                continue;
            }
            PropertyType pt = createPropertyType(pd);
            if (isPrimaryKey(pd, dbms)) {
                pt.setPrimaryKey(true);
                found = true;
            }
            list.add(pt);
        }
        PropertyType[] propertyTypes = (PropertyType[]) list
                .toArray(new PropertyType[list.size()]);
        Set columns = getColumns(tableName);
        setupColumnName(propertyTypes, columns);
        setupPersistent(propertyTypes, columns);
        if (!found) {
            setupPrimaryKey(propertyTypes, tableName);
        }
        return propertyTypes;
    }

    /**
     * カラム名のセットを返します。
     * 
     * @param tableName
     * @return カラム名のセット
     */
    protected Set getColumns(String tableName) {
        Set columnSet = DatabaseMetaDataUtil.getColumnMap(databaseMetaData,
                tableName).keySet();
        if (columnSet.isEmpty()) {
            logger.log("WDAO0002", new Object[] { tableName });
        }
        return columnSet;
    }

    /**
     * <code>propertyTypes</code>の各要素にカラム名を設定します。
     * 
     * @param propertyTypes {@link PropertyType}の配列
     * @param columns カラム名のセット
     */
    protected void setupColumnName(PropertyType[] propertyTypes, Set columns) {
        for (Iterator i = columns.iterator(); i.hasNext();) {
            String columnName = (String) i.next();
            String columnName2 = StringUtil.replace(columnName, "_", "");
            for (int j = 0; j < propertyTypes.length; ++j) {
                PropertyType pt = propertyTypes[j];
                if (pt.getColumnName().equalsIgnoreCase(columnName2)) {
                    final PropertyDesc pd = pt.getPropertyDesc();
                    if (beanAnnotationReader.getColumnAnnotation(pd) == null) {
                        pt.setColumnName(columnName);
                    }
                    break;
                }
            }
        }
    }

    /**
     * <code>propertyTypes</code>の各要素に永続化されるかどうかを設定します。
     * 
     * @param propertyTypes {@link PropertyType}の配列
     * @param columns カラム名のセット
     */
    protected void setupPersistent(PropertyType[] propertyTypes, Set columns) {
        for (int j = 0; j < propertyTypes.length; j++) {
            PropertyType pt = propertyTypes[j];
            pt.setPersistent(isPersistent(pt));
            if (!columns.contains(pt.getColumnName())) {
                pt.setPersistent(false);
            }
        }
    }

    /**
     * <code>propertyTypes</code>の各要素に主キーであるかどうかを設定します。
     * 
     * @param propertyTypes {@link PropertyType}の配列
     * @param tableName テーブル名
     */
    protected void setupPrimaryKey(PropertyType[] propertyTypes,
            String tableName) {
        Set primaryKeySet = DatabaseMetaDataUtil.getPrimaryKeySet(
                databaseMetaData, tableName);
        for (int i = 0; i < propertyTypes.length; ++i) {
            PropertyType pt = propertyTypes[i];
            if (primaryKeySet.contains(pt.getColumnName())) {
                pt.setPrimaryKey(true);
            }
        }
    }

}