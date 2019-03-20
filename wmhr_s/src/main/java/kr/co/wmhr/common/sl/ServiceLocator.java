package kr.co.wmhr.common.sl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
	private Map<String,DataSource> cache;
	private Context envCtx;
	private static ServiceLocator instance;

	static {
		try {
			instance = new ServiceLocator();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}

	private ServiceLocator() {
		try {
			envCtx = new InitialContext();
			cache = Collections.synchronizedMap(new HashMap<String,DataSource>());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}

	public static ServiceLocator getInstance() {
		return instance;
	}

	public DataSource getDataSource(String jndiName) throws ServiceLocatorException {
		DataSource dataSource;
		try {
			if (cache.containsKey(jndiName)) {
				dataSource=cache.get(jndiName);
			} else {
				dataSource = (DataSource)envCtx.lookup("java:comp/env/"+jndiName);
				cache.put(jndiName, dataSource);
			}
		} catch (NamingException e) {
			throw new ServiceLocatorException(e.getMessage());
		}
		return dataSource;
	}
}