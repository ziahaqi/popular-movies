package net.ziahaqi.udacity.movies.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.ziahaqi.udacity.movies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class ApiBuilder {
    public static final String API_KEY = "api_key";

    public static MovieService createMovieService() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        final HttpUrl requestUrl = chain.request()
                                .url()
                                .newBuilder()
                                .addQueryParameter(API_KEY, BuildConfig.MOVIE_API_KEY)
                                .build();
                        final Request request = chain.request().newBuilder().url(requestUrl).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(BuildConfig.MOVIE_BASE_URL)
                .build();
        return retrofit.create(MovieService.class);
    }
}
