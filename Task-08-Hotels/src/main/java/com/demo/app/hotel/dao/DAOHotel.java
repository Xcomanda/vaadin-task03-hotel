package com.demo.app.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.demo.app.hotel.database.DataProvider;
import com.demo.app.hotel.entities.Hotel;

public class DAOHotel implements DAOInterface<Hotel> {

	@Override
	public void create(Hotel hotel) {
		EntityManager em = DataProvider.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(hotel);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel create Exception", e);
		} finally {
			em.close();
		}
	}

	@Override
	public Hotel read(Hotel hotel) {
		EntityManager em = DataProvider.getEntityManager();
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			em.getTransaction().commit();
			return h;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel read Exception", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Hotel hotel) {
		EntityManager em = DataProvider.getEntityManager();
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			h.setName(hotel.getName());
			h.setAddress(hotel.getAddress());
			h.setRating(hotel.getRating());
			h.setOperatesDays(hotel.getOperatesDays());
			h.setCategoryId(hotel.getCategoryId());
			h.setUrl(hotel.getUrl());
			h.setDescription(hotel.getDescription());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel update Exception", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(Hotel hotel) {
		EntityManager em = DataProvider.getEntityManager();
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			em.remove(h);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel delete Exception", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Hotel> getList() {
		// DataProvider.getEntityManager().flush();
		EntityManager em = DataProvider.getEntityManager();
		try {
			em.getTransaction().begin();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Hotel> criteria = cb.createQuery(Hotel.class);
			Root<Hotel> root = criteria.from(Hotel.class);
			criteria.select(root);
			List<Hotel> list = em.createQuery(criteria).getResultList();
			em.getTransaction().commit();
			return list;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel getList Exception", e);
		} finally {
			em.close();
		}
	}

}
