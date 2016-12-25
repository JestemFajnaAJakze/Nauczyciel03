package com.project.mcr.nauczyciel02.endpoint;

/**
 * Created by MCR on 19.12.2016.
 */
import com.project.mcr.nauczyciel02.model.Answer;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.Student;
import com.project.mcr.nauczyciel02.model.Teacher;
import com.project.mcr.nauczyciel02.model.Test;

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
    @GET("/get_all_tests.php")
    void getTestsAll(Callback<List<Test>> cb);

    @GET("/add_test.php")
    void addTest(@Query("category_id") int category_id, @Query("name") String name, Callback<List<Test>> cb);

    //dla ucznia lista testow
    @GET("/test_get_category_and_class.php")
    void getTestListByCategoryAndClass(@Query("category_id") int categoryId, Callback<List<Question>> cb);

    @GET("/get_inserted_test_id.php")
        //void getInsertedQuestionId(@Query("category_id") int categoryId, @Query("name") String name, Callback<List<Question>> cb);
    void getInsertedTestId(Callback<List<Test>> cb);

    /////////////////////answer//////////////////////////////////////////////////
    @GET("/add_answer.php")
    void addAnswer(@Query("question_id") int question_id, @Query("name") String name, @Query("is_correct") int is_correct, Callback<List<Answer>> cb);



    /////////////////////question//////////////////////////////////////////////////

    @GET("/get_inserted_question_id.php")
    //void getInsertedQuestionId(@Query("category_id") int categoryId, @Query("name") String name, Callback<List<Question>> cb);
    void getInsertedQuestionId(Callback<List<Question>> cb);

    @GET("/get_current_question_by_id.php")
        //void getInsertedQuestionId(@Query("category_id") int categoryId, @Query("name") String name, Callback<List<Question>> cb);
    void getQuestionById(@Query("question_id") int question_id, Callback<List<Question>> cb);

    @GET("/get_answer_list_by_question_id.php")
        //void getInsertedQuestionId(@Query("category_id") int categoryId, @Query("name") String name, Callback<List<Question>> cb);
    void getAnswerListQuestionById(@Query("question_id") int question_id, Callback<List<Answer>> cb);


    @GET("/add_question.php")
    void addQuestion(@Query("category_id") int categoryId, @Query("name") String name, Callback<List<Question>> cb);

    @GET("/question_get.php")
    void getQuestionListByCategory(@Query("category_id") int categoryId, Callback<List<Question>> cb);

    @GET("/get_test_details_info.php")
    void getTestDetailsInfo(@Query("category_id") int categoryId, @Query("test_id") int test_id, Callback<List<Question>> cb);

    @GET("/get_test_main_info.php")
    void getTestMainInfo(@Query("test_id") int test_id, Callback<List<Test>> cb);

    /////////////////////category//////////////////////////////////////////////////

    @GET("/category_get.php")
    void getCategoryList(Callback<List<Category>> cb);

    @GET("/category_add.php")
    void addCategory(@Query("name") String name, Callback<List<Category>> cb);

    /////////////////////teacher//////////////////////////////////////////////////

    //czy wolny email
    @GET("/is_teacher_exist.php")
    void isTeacherExist(@Query("email") String email, Callback<List<Teacher>> cb);

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
