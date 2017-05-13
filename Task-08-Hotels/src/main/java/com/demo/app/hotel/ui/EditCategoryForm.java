package com.demo.app.hotel.ui;

import com.demo.app.hotel.entities.HotelCategory;
import com.demo.app.hotel.services.CategoryService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class EditCategoryForm extends FormLayout {
	private TextField name = new TextField("Category");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");

	private CategoryService categoryService = CategoryService.getInstance();
	private HotelCategory category;
	private CategoryForm categoryForm;
	private Binder<HotelCategory> binder = new Binder<>(HotelCategory.class);

	public EditCategoryForm(CategoryForm categoryForm) {
		setSizeUndefined();

		addComponents(name, new HorizontalLayout(save, cancel));
		this.categoryForm = categoryForm;

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(e -> save());
		save.setDescription("Save changes");
		cancel.setStyleName(ValoTheme.BUTTON_DANGER);
		cancel.setDescription("Cancel adding new categorys");
		cancel.addClickListener(e -> categoryForm.swapComponentsVisibility());

		name.setRequiredIndicatorVisible(true);
		name.setDescription("Category name");
		bindFields();
	}

	public void bindFields() {
		binder.forField(name).asRequired("Name is required").bind(HotelCategory::getName, HotelCategory::setName);
	}

	public void setHotelCategory(HotelCategory category) {
		this.category = category;
		binder.setBean(category);
		categoryForm.swapComponentsVisibility();
		setVisible(true);
		name.selectAll();
	}

	private void save() {
		if (binder.isValid()) {
			categoryService.save(category);
			categoryForm.updateList();
			categoryForm.swapComponentsVisibility();
			setVisible(false);
		} else {
			binder.validate();
		}
	}

}
