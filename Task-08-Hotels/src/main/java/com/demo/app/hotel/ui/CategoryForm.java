package com.demo.app.hotel.ui;

import java.util.List;

import com.demo.app.hotel.entities.HotelCategory;
import com.demo.app.hotel.services.CategoryService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class CategoryForm extends FormLayout implements View  {
	private CategoryService categoryService = CategoryService.getInstance();

	private Button addCategory;
	private Button editCategory;
	private Button deleteCategory;
	private Grid<HotelCategory> gridCategory;

	private HorizontalLayout toolbar;

	private EditCategoryForm editCategoryForm;

	public CategoryForm() {
		initComponents();
		initLayouts();

		toolbar.addComponents(addCategory, editCategory, deleteCategory);
		addComponents(toolbar, gridCategory, editCategoryForm);

		updateList();
		selectionCheck();
	}

	private void initLayouts() {
		toolbar = new HorizontalLayout();
	}

	private void initComponents() {
		addCategory = new Button("New");
		addCategory.setDescription("Create new category");
		addCategory.setIcon(VaadinIcons.FILE_ADD);
		addCategory.addClickListener(e -> {
			editCategoryForm.setHotelCategory(new HotelCategory());
		});

		editCategory = new Button("Edit");
		editCategory.setDescription("Edit category");
		editCategory.setIcon(VaadinIcons.EDIT);
		editCategory.addClickListener(e -> {
			for (HotelCategory hotelCategory : gridCategory.getSelectedItems()) {
				editCategoryForm.setHotelCategory(hotelCategory);
			}
		});

		deleteCategory = new Button("Delete");
		deleteCategory.setDescription("Delete selected categories");
		deleteCategory.setIcon(VaadinIcons.CLOSE);
		deleteCategory.addClickListener(e -> deleteSelected());

		gridCategory = new Grid<>(HotelCategory.class);
		gridCategory.setColumns();
		gridCategory.addColumn(HotelCategory::getName).setCaption("Category");
		gridCategory.setSelectionMode(SelectionMode.MULTI);
		gridCategory.addSelectionListener(e -> {
			selectionCheck();
		});

		editCategoryForm = new EditCategoryForm(this);
		editCategoryForm.setVisible(false);
	}

	private void deleteSelected() {
		for (HotelCategory category : gridCategory.getSelectedItems()) {
			categoryService.delete(category);
		}
		updateList();
	}

	private void selectionCheck() {
		editCategory.setEnabled(gridCategory.getSelectedItems().size() == 1);
		deleteCategory.setEnabled(gridCategory.getSelectedItems().size() > 0);
	}

	public void updateList() {
		List<HotelCategory> categories = categoryService.findAll();
		gridCategory.setItems(categories);
	}

	public void swapComponentsVisibility() {
		gridCategory.setVisible(!gridCategory.isVisible());
		addCategory.setVisible(!addCategory.isVisible());
		editCategory.setVisible(!editCategory.isVisible());
		deleteCategory.setVisible(!deleteCategory.isVisible());
		editCategoryForm.setVisible(!editCategoryForm.isVisible());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
