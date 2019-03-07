package bawei.com.qqdemo1.core;

import bawei.com.qqdemo1.bean.Result;

public interface DataCall<T> {
    // 成功
    void success(T data);
    // 失败
    void fail(Result result);
}
