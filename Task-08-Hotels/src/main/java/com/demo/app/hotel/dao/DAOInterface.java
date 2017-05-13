package com.demo.app.hotel.dao;

import java.util.List;

public interface DAOInterface<T> {

	public void create(T obj);

	public T read(T obj);

	public void update(T obj);

	public void delete(T obj);

	public List<T> getList();
}
