package com.project.mcr.nauczyciel02.endpoint;

/**
 * Created by MCR on 19.12.2016.
 */
import com.project.mcr.nauczyciel02.model.Category;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by MCR on 24.11.2016.
 */
public interface CurrentQuestionGET {

    @GET("/category_get.php")
    void getBooks(Callback<List<Category>> cb);
}