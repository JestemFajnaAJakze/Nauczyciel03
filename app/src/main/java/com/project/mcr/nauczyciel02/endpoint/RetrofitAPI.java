package com.project.mcr.nauczyciel02.endpoint;

/**
 * Created by MCR on 19.12.2016.
 */
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by MCR on 24.11.2016.
 */
public interface RetrofitAPI {

    @GET("/category_get.php")
    void getCategoryList(Callback<List<Category>> cb);

    @GET("/question_get.php")
    //void getQuestionListByCategory(Callback<List<Question>> cb);
   void getQuestionListByCategory(@Query("category_id") int categoryId, Callback<List<Question>> cb);

    @GET("/category_add.php")
        //void getQuestionListByCategory(Callback<List<Question>> cb);
    void addCategory(@Query("name") String name, Callback<List<Category>> cb);
}
