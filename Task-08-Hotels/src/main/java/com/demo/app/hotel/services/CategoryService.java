package com.demo.app.hotel.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.demo.app.hotel.dao.DAOHotelCategory;
import com.demo.app.hotel.entities.HotelCategory;

public class CategoryService {

	private static CategoryService instance;
	private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());

	private CategoryService() {

	}

	public static CategoryService getInstance() {
		if (instance == null) {
			instance = new CategoryService();
			instance.ensureTestData();
		}
		return instance;
	}

	public synchronized List<HotelCategory> findAll() {
		DAOHotelCategory daoHotelCategory = new DAOHotelCategory();
		List<HotelCategory> list = daoHotelCategory.getList();
		Collections.sort(list, new Comparator<HotelCategory>() {
			@Override
			public int compare(HotelCategory o1, HotelCategory o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});
		return list;
	}

	public synchronized HotelCategory findById(Integer id) {
		if (id != null) {
			DAOHotelCategory daoHotelCategory = new DAOHotelCategory();
			HotelCategory category = daoHotelCategory.read(new HotelCategory(id));
			return category;
		}
		return null;
	}

	public synchronized void delete(HotelCategory category) {
		DAOHotelCategory daoHotelCategory = new DAOHotelCategory();
		daoHotelCategory.delete(category);
	}

	public synchronized void save(HotelCategory category) {
		if (category == null) {
			LOGGER.log(Level.SEVERE, "Category is null.");
			return;
		}
		DAOHotelCategory daoHotelCategory = new DAOHotelCategory();
		if (category.getId() != null) {
			daoHotelCategory.update(category);
			return;
		}
		daoHotelCategory.create(category);
	}

	private void ensureTestData() {
		if (findAll().isEmpty()) {
			save(new HotelCategory("Hotel"));
			save(new HotelCategory("Hostel"));
			save(new HotelCategory("GuestHouse"));
			save(new HotelCategory("Appartments"));
		}
	}

}