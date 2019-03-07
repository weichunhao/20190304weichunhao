package bawei.com.qqdemo1.presenter;

import bawei.com.qqdemo1.bean.Result;
import bawei.com.qqdemo1.core.BasePresenter;
import bawei.com.qqdemo1.core.DataCall;
import bawei.com.qqdemo1.model.CartModel;

public class CartPresenter extends BasePresenter {
    public CartPresenter(DataCall dataCall) {
        super(dataCall);
    }
    @Override
    protected Result getData(Object... args) {
        Result result = CartModel.goodsList();//调用网络请求获取数据
        return result;
    }
}
