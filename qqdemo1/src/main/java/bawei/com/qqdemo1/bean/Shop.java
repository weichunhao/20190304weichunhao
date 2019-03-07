package bawei.com.qqdemo1.bean;

import java.util.List;

public class Shop {
    List<Goods> list;
    String sellerName;
    String sellerid;

    // 自己添加的 为了判断复选框是否选中
    boolean check;

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

}
