package itg8.com.nowzonedesigndemo.common;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android itg 8 on 8/4/2017.
 */

public class Retro {

    static Retro retrofitObj;

    public static Retro getInstance() {
        if (retrofitObj == null) {
            retrofitObj = new Retro();
        }
        return retrofitObj;

    }

private Interceptor getHeader(String header){
    return new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "bearer "+header); // <-- this is the important line

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };
}



    public RetroController getController(String header) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.addInterceptor(interceptor);
        builder.readTimeout(5, TimeUnit.MINUTES);
        if(header!=null)
            builder.addInterceptor(getHeader(header));

        OkHttpClient client=builder.build();
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit;

        retrofit = new Retrofit.Builder()

                .baseUrl(CommonMethod.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RetroController.class);


    }

}
