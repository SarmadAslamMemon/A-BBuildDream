package com.example.abbuilddream.api;

import com.example.abbuilddream.model.AddOrderResponse;
import com.example.abbuilddream.model.Order;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.model.UserRegistered;
import com.example.abbuilddream.model.UserRegistration;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetroInterface {


    @GET("api/Product")
    Call<List<Product>> getProducts();



    // 1. GET request with a path parameter
    @GET("your_endpoint/{id}")
    Call<Product> getData(@Path("id") String id);

//    // 2. GET request with a query parameter
//    @GET("your_endpoint")
//    Call<YourModel> getDataWithQuery(@Query("queryParam") String queryParam);
//
//    // 3. POST request to create new data
    @POST("api/Order")
    Call<AddOrderResponse> createData(@Body Order postData);

    @POST("api/User/Register")
    Call<UserRegistered> userRegister(@Body UserRegistration userRegistration);
//
//    // 4. PUT request to update existing data
//    @PUT("your_endpoint/{id}")
//    Call<YourModel> updateData(@Path("id") String id, @Body UpdateModel updateData);
//
//    // 5. DELETE request to delete data by ID
//    @DELETE("your_endpoint/{id}")
//    Call<DeleteResponse> deleteData(@Path("id") String id);
//}
}