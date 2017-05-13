package com.demo.app.hotel.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;

import com.demo.app.hotel.converters.DateToWorkedDaysConverter;
import com.demo.app.hotel.converters.IntegerRatingToStringConverter;
import com.demo.app.hotel.entities.Hotel;
import com.demo.app.hotel.entities.HotelCategory;
import com.demo.app.hotel.services.CategoryService;
import com.demo.app.hotel.services.HotelService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class EditHotelForm extends FormLayout {

	private TextField name = new TextField("Name");
	private TextField address = new TextField("Address");
	private TextField rating = new TextField("Rating");
	private DateField operatesFrom = new DateField("Operates from");
	private NativeSelect<Integer> category = new NativeSelect<>("Category");
	private TextField url = new TextField("URL");
	private TextArea description = new TextArea("Description");

	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	private Button delete = new Button("Delete");

	private HotelService hotelService = HotelService.getInstance();
	private Hotel hotel;
	private HotelForm hotelForm;
	private Binder<Hotel> binder = new Binder<>(Hotel.class);

	public EditHotelForm(HotelForm hotelForm) {
		this.hotelForm = hotelForm;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
		addComponents(name, address, rating, operatesFrom, category, url, description, buttons);

		List<Integer> categories = new ArrayList<>();
		for (HotelCategory category : CategoryService.getInstance().findAll()) {
			categories.add(category.getId());
		}
		category.setItemCaptionGenerator(i -> CategoryService.getInstance().findById(i).getName());
		category.setItems(categories);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(e -> save());

		cancel.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
		cancel.addClickListener(e -> hotelForm.swapComponentsVisibility());

		delete.setClickShortcut(ShortcutAction.KeyCode.DELETE);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
		delete.addClickListener(e -> delete());

		setComponentsSize();
		setRequiredIndicators();
		setDescriptions();
		bindFields();
	}

	private void setDescriptions() {
		name.setDescription("Name of hotel");
		address.setDescription("Adress of hotel");
		rating.setDescription("Rating of hotel");
		operatesFrom.setDescription("Foundation date of hotel");
		category.setDescription("Category of hotel");
		url.setDescription("Website address");
		description.setDescription("Description of hotel");
		save.setDescription("Save changes");
		cancel.setDescription("Cancel");
		delete.setDescription("Delete item");
	}

	private void setRequiredIndicators() {
		name.setRequiredIndicatorVisible(true);
		address.setRequiredIndicatorVisible(true);
		rating.setRequiredIndicatorVisible(true);
		operatesFrom.setRequiredIndicatorVisible(true);
		operatesFrom.setRangeEnd(LocalDate.now());
		operatesFrom.setTextFieldEnabled(false);
		category.setRequiredIndicatorVisible(true);
		url.setRequiredIndicatorVisible(true);
	}

	private void setComponentsSize() {
		name.setWidth("600px");
		address.setWidth("600px");
		rating.setWidth("50px");
		operatesFrom.setWidth("200px");
		category.setWidth("200px");
		url.setWidth("600px");
		description.setWidth("600px");
		description.setHeight("70px");
	}

	private void bindFields() {
		binder.forField(name).asRequired("Name is required").bind(Hotel::getName, Hotel::setName);
		binder.forField(address).asRequired("Address is required").bind(Hotel::getAddress, Hotel::setAddress);
		binder.forField(rating).withConverter(new IntegerRatingToStringConverter())
				.withValidator(rating -> rating < 6 && rating > 0,
						"Rating is required and should be a number from 1 to 5")
				.bind(Hotel::getRating, Hotel::setRating);
		binder.forField(operatesFrom).withConverter(new DateToWorkedDaysConverter())
				.withValidator(date -> Duration
						.between(LocalDate.now().atTime(0, 0), operatesFrom.getValue().atTime(0, 0)).toDays() <= 0,
						"Cannot set future date")
				.bind(Hotel::getOperatesDays, Hotel::setOperatesDays);
		binder.forField(category)
				.withValidator(category -> CategoryService.getInstance().findById(category) != null, "Select category")
				.bind(Hotel::getCategoryId, Hotel::setCategoryId);
		binder.forField(url).asRequired("URL is required")
				.withValidator(url -> new UrlValidator().isValid(url), "URL is invalid. Try to write entire URL")
				.bind(Hotel::getUrl, Hotel::setUrl);
		binder.forField(description).bind(Hotel::getDescription, Hotel::setDescription);
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
		binder.setBean(hotel);
		if (CategoryService.getInstance().findById(hotel.getCategoryId()) == null) {
			category.setSelectedItem(null);
		}
		delete.setVisible(hotel.isPersisted());
		name.selectAll();
	}

	private void delete() {
		hotelService.delete(hotel);
		hotelForm.updateList();
		hotelForm.swapComponentsVisibility();
	}

	private void save() {
		if (binder.isValid()) {
			hotelService.save(hotel);
			hotelForm.updateList();
			hotelForm.swapComponentsVisibility();
		} else {
			binder.validate();
		}
	}
}
