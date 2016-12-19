package com.project.mcr.nauczyciel02.endpoint;

/**
 * Created by MCR on 19.12.2016.
 */
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.Student;
import com.project.mcr.nauczyciel02.model.Teacher;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by MCR on 19.12.2016.
 */
public interface RetrofitAPI {

    /////////////////////test//////////////////////////////////////////////////

    //dla nauczyciela lista testow
    @GET("/test_get.php")
    void getTestListByCategory(@Query("category_id") int categoryId, Callback<List<Question>> cb);

    //dla ucznia lista testow
    @GET("/test_get_category_and_class.php")
    void getTestListByCategoryAndClass(@Query("category_id") int categoryId, Callback<List<Question>> cb);



    /////////////////////question//////////////////////////////////////////////////

    @GET("/question_get.php")
    void getQuestionListByCategory(@Query("category_id") int categoryId, Callback<List<Question>> cb);

    /////////////////////category//////////////////////////////////////////////////

    @GET("/category_get.php")
    void getCategoryList(Callback<List<Category>> cb);

    @GET("/category_add.php")
    void addCategory(@Query("name") String name, Callback<List<Category>> cb);

    /////////////////////teacher//////////////////////////////////////////////////

    //czy wolny email
    @GET("/is_teacher_exist.php")
    void isTeacherExist(@Query("email") String email, Callback<List<Student>> cb);

    //dodaj jak mozna
    @GET("/add_teacher.php")
    void addTeacher(@Query("email") String email, @Query("password") String password, @Query("name") String name, Callback<List<Teacher>> cb);

    //czy dane do logowania poprawne
    @GET("/check_teacher.php")
    void checkTeacher(@Query("email") String email,@Query("password") String password, Callback<List<Teacher>> cb);

    /////////////////////student//////////////////////////////////////////////////

    //czy wolny email
    @GET("/is_student_exist.php")
    void isStudentExist(@Query("email") String email, Callback<List<Student>> cb);

    //dodaj jak mozna
    @GET("/add_student.php")
    void addStudent(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("class_id") int classId, Callback<List<Student>> cb);

    //czy dane do logowania poprawne
    @GET("/check_student.php")
    void checkStudent(@Query("email") String email,@Query("password") String password, Callback<List<Student>> cb);



}
