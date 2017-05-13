package com.demo.app.hotel.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataProvider {
	private final static EntityManagerFactory entityManagerFactory;
	private final static DataProvider instance;

	static {
		String providerName = null;
		try {
			InputStream propsIS = DataProvider.class.getClassLoader()
					.getResourceAsStream("META-INF/properties.properties");
			Properties props = new Properties();
			props.load(propsIS);
			providerName = props.getProperty("persistence_unit_name");
			propsIS.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		entityManagerFactory = Persistence.createEntityManagerFactory(providerName);
		instance = new DataProvider();
	}

	private DataProvider() {

	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public static DataProvider getInstatnce() {
		return instance;
	}

	@Override
	protected void finalize() throws Throwable {
		entityManagerFactory.close();
	}
}
