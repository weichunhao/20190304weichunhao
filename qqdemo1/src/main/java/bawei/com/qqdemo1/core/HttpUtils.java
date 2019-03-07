package bawei.com.qqdemo1.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpUtils {
    public static String get(String urlString) {

        // 添加日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)//连接超时
                .readTimeout(20, TimeUnit.SECONDS)//读取超时
                .callTimeout(20, TimeUnit.SECONDS)//呼叫超时
                .addInterceptor(loggingInterceptor)// 日志拦截器
                .build();

        Request request = new Request.Builder().url(urlString).get().build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //okhttp使用单例模式
    private static OkHttpClient singleton;
    //非常有必要，要不此类还是可以被new，但是无法避免反射，好恶心
    private HttpUtils(){

    }
    public static OkHttpClient getInstance() {
        if (singleton == null)
        {
            synchronized (HttpUtils.class)
            {
                if (singleton == null)
                {
                    singleton = new OkHttpClient();
                }
            }
        }
        return singleton;
    }
}
