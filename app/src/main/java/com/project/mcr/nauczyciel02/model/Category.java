package com.project.mcr.nauczyciel02.model;

/**
 * Created by MCR on 24.11.2016.
 */
public class Category {

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    private int category_id;
    private String name;

    public static final Category[] categories = {
            new Category(1, "Historia i WOS"), new Category(2, "Przedmioty przyrodnicze")
    };

    public Category(int id, String name) {
        this.category_id= id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
