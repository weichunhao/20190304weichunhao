package bawei.com.qqdemo1;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.bumptech.glide.Glide;
import com.umeng.commonsdk.proguard.g;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

import static com.umeng.commonsdk.proguard.g.s;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.qqlogin).setOnClickListener(this);
        findViewById(R.id.qqshare).setOnClickListener(this);


        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        // aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        // aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图
        aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//白昼地图（即普通地图）
        aMap.setTrafficEnabled(true);//显示实时路况图层，aMap是地图控制器对象。



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * qq登录
             */
            case R.id.qqlogin:
                final UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                /**
                 * 登录授权监听
                 */
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {


                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);

                    }
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        /**
                         * 登录信息集合
                         * Log.i("Tag",map+"");
                         */
                        String ion = map.get("profile_image_url");
                        String name = map.get("screen_name");

                        Toast.makeText(MainActivity.this, "昵称:" + name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "头像:" + ion, Toast.LENGTH_SHORT).show();
                        /**
                         *ion_qq是图片控件，只是为了验证登录成功后获取到你的QQ头像
                         */
                      //  Glide.with(MainActivity.this).load(s).into(umShareAPI);
                    }
                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }
                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
            /**
             * qq分享
             * 分享监听
             */
            case R.id.qqshare:

                UMShareListener umShareListener = new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Log.d("plat", "platform" + platform);
                        Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                        Log.i("xxx", "onError: " + t);
                    }
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * 友盟图片
                 */
                UMImage umImage = new UMImage(MainActivity.this, R.mipmap.ic_launcher);
                new ShareAction(MainActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(umImage)//分享图片
                        .setCallback(umShareListener)//回调监听器
                        .share();
                break;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode, resultCode, data);
    }

}
