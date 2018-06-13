package com.zliteams.hot.core.feature.orm.mybatis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * MyBatis工具集
 */
public class MyBatisUtils {
	@Resource
	private static org.mybatis.spring.SqlSessionFactoryBean sqlSessionFactory;

	/**
	 * 日期格式化
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 获取SQL
	 * 
	 * @param mybatisSessionFactoryBean
	 * @param namespace
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getSql(String namespace, Object params)
			throws Exception {
		Configuration configuration = ((SqlSessionFactory) sqlSessionFactory).getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (params == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(params.getClass())) {
						value = params;
					} else {
						MetaObject metaObject = configuration.newMetaObject(params);
						value = metaObject.getValue(propertyName);
					}
					JdbcType jdbcType = parameterMapping.getJdbcType();
					if (value == null && jdbcType == null) {
						jdbcType = configuration.getJdbcTypeForNull();
					}
					Class javaType = parameterMapping.getJavaType();
					if (value != null) {
						javaType = value.getClass();
					}
					sql = replaceParameter(sql, value, jdbcType, javaType);
				}
			}
		}
		return sql;
	}

	/**
	 * 根据类型替换参数 仅作为数字和字符串两种类型进行处理，需要特殊处理的可以继续完善这里
	 * 
	 * @param sql @param value @param jdbcType @param javaType @return
	 */
	@SuppressWarnings("rawtypes")
	private static String replaceParameter(String sql, Object value, JdbcType jdbcType, Class javaType) {
		String strValue = String.valueOf(value);
		if (jdbcType != null) {
			switch (jdbcType) {
			// 数字
				case BIT:
				case TINYINT:
				case SMALLINT:
				case INTEGER:
				case BIGINT:
				case FLOAT:
				case REAL:
				case DOUBLE:
				case NUMERIC:
				case DECIMAL:
					break;
				// 日期
				case DATE:
				case TIME:
				case TIMESTAMP:
					// 其他，包含字符串和其他特殊类型
				default:
					strValue = "'" + strValue + "'";
			}
		} else if (Number.class.isAssignableFrom(javaType)) {
			// 不加单引号
		} else if (Date.class.isAssignableFrom(javaType)) {
			// 日期
			strValue = "'" + sdf.format(value) + "'";
		} else {
			strValue = "'" + strValue + "'";
		}
		return sql.replaceFirst("\\?", strValue);
	}
}