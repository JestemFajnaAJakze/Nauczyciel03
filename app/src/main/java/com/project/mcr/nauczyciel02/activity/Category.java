package com.project.mcr.nauczyciel02.activity;

/**
 * Created by MCR on 24.11.2016.
 */
public class Category {

    private int _id;
    private String name;

    public static final Category[] categories = {
            new Category(1, "Historia i WOS"), new Category(2, "Przedmioty przyrodnicze")
    };

    public Category(int id, String name) {
        this._id= id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
