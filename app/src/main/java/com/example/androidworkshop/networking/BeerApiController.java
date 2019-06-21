package com.example.androidworkshop.networking;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import java.io.IOException;
import java.util.List;

public class BeerApiController {

    private static final String BASE_URL = "https://api.punkapi.com/v2/";

    private BeerApi beerApi;

    private static Retrofit retrofit = null;

    public void start() {
        Timber.d( "initializing Retrofit...");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //The logging-interceptor generates a log string of the entire response that’s returned.
        //Interceptors are a powerful mechanism present in OkHttp that can monitor, rewrite, and retry calls.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Application Interceptors : To register an application interceptor, we need to call addInterceptor() on OkHttpClient.Builder
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(interceptor).build();


        // authorization header
        String authToken = "";
        String username = "";
        String password = "";
        //Basic Auth
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            authToken = Credentials.basic(username, password);
        }

        //Create a new Interceptor.
        final String finalAuthToken = authToken;
        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Headers headers = request.headers().newBuilder().add("Authorization", finalAuthToken).build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };

        //Add the interceptor to the client builder.
        clientBuilder.addInterceptor(headerAuthorizationInterceptor);

        //Retrofit is type-safe REST client for Android and Java which aims to make it easier to consume RESTful web services.
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientBuilder.build())
                .build();

        beerApi = retrofit.create(BeerApi.class);
    }

    // asynchronous call
    public void getListOfBeers(Callback<List<Beer>> callback) {
        Call<List<Beer>> call = beerApi.getBeers();
        call.enqueue(callback);
    }

    //synchronous way
    public List<Beer> getListOfBeers() {
        Call<List<Beer>> call = beerApi.getBeers();
        try {
            return call.execute().body();
        } catch (Exception ex) {
            Timber.e("something went wrong with the request");
        }
        return null;
    }
}
