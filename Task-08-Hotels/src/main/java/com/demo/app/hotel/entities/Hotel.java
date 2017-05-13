package com.demo.app.hotel.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "hotel")
public class Hotel implements Serializable, Cloneable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
	private String name = "";
	
	@Column(name = "ADDRESS")
	private String address = "";
	
	@Column(name = "RATING")
	private Integer rating;
	
	@Column(name = "OPERATES_FROM")
	private Long operatesDays = 0L;
	
	@Column(name = "CATEGORY")
	private Integer categoryId;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "DESCRIPTION", columnDefinition = "text")
	private String description = "";

	public Hotel() {

	}

	public Hotel(Long id, String name, String address, Integer rating, Long operatesDays, Integer categoryId, String url) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.operatesDays = operatesDays;
		this.categoryId = categoryId;
		this.url = url;
	}

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public String toString() {
		return name + " " + rating + "stars " + address;
	}

	@Override
	public Hotel clone() throws CloneNotSupportedException {
		return (Hotel) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getOperatesDays() {
		return operatesDays;
	}

	public void setOperatesDays(Long operatesDays) {
		this.operatesDays = operatesDays;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}