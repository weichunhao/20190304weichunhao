package bawei.com.qqdemo1.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bawei.com.qqdemo1.R;
import bawei.com.qqdemo1.bean.Goods;
import bawei.com.qqdemo1.bean.Result;
import bawei.com.qqdemo1.bean.Shop;
import bawei.com.qqdemo1.core.AddSubLayout;

public class CarAdapter extends BaseExpandableListAdapter {
    private List<Shop> mList = new ArrayList<>();
    private TotalPriceLister priceLister;
    //实例化自定义接口
    public void setTotalPriceLister(TotalPriceLister totalPriceLister) {
        this.priceLister = totalPriceLister;
    }
    // 点击全选 则调用次方法
    public void checkAll(boolean is) {
        for (int i = 0; i < mList.size(); i++) {
            Shop shop = mList.get(i);
            shop.setCheck(is);// 设置
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                goods.setSelected(is?1:0);//设置选中状态
            }
        }
        notifyDataSetChanged();// 刷新数据
        calculatePrice();//计算总价
    }
    // 自定义接口
    public interface TotalPriceLister{
        void totalprice(double totalprice);

        void success(List<Shop> data);

        void fail(Result result);
    }
    public void addAll(List<Shop> list){
        if (list != null) {
            mList.addAll(list);// 添加数据
        }
    }
    @Override
    public int getGroupCount() {
        return mList.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getList().size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getList().get(childPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.child_item, null);
            groupHolder = new GroupHolder();
            groupHolder.checkBox = convertView.findViewById(R.id.g_checkbox);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Shop shop = mList.get(groupPosition);// 得到此商铺
        groupHolder.checkBox.setText(shop.getSellerName());// 设置商品的名字
        groupHolder.checkBox.setChecked(shop.isCheck());// 这是商铺的选中状态
        groupHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 防治点击事件与选中发生冲突
                CheckBox checkBox= (CheckBox) v;
                shop.setCheck(checkBox.isChecked());// 设置商品的选中状态
                List<Goods> goodsList = mList.get(groupPosition).getList();// 得到商品的信息
                for (int i = 0; i < goodsList.size(); i++) {
                    goodsList.get(i).setSelected(checkBox.isChecked() ? 1 : 0);// 设置是否选中
                }
                // 更新适配器
                notifyDataSetChanged();

                // 计算价格
                calculatePrice();
            }
        });
        return convertView;
    }
    /**
     * 计算总价格
     */
    private void calculatePrice() {
        double totalprice=0;
        for (int i = 0; i < mList.size(); i++) {
            Shop shop = mList.get(i);
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                if (goods.getSelected() == 1) {
                    // 是选中状态的话则计算进去
                    totalprice = totalprice +goods.getNum()*goods.getPrice();
                }
            }
        }
        // 判断
        if (priceLister != null) {
            priceLister.totalprice(totalprice);// 将值利用接口回调传回Activity进行展示
        }
    }
    class GroupHolder {
        CheckBox checkBox;// 这是二级列表的第一级
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder=null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(parent.getContext(),R.layout.add_stu,null);
            childHolder.addSub= convertView.findViewById(R.id.add_sub);
            childHolder.check = convertView.findViewById(R.id.child_check);
            childHolder.image = convertView.findViewById(R.id.image_cc);
            childHolder.price = convertView.findViewById(R.id.text_price);
            childHolder.text = convertView.findViewById(R.id.text_ttt);
            convertView.setTag(childHolder);
        }else{
            childHolder= (ChildHolder) convertView.getTag();
        }
        // 得到单个商品
        final Goods goods = mList.get(groupPosition).getList().get(childPosition);
        childHolder.text.setText(goods.getTitle());
        childHolder.price.setText(goods.getPrice()+"");// 单价
        // 点击选中，计算价格
        childHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 防治点击事件与选中发生冲突
                CheckBox checkBox = (CheckBox) v;
                goods.setSelected(checkBox.isChecked()?1:0);//设置此商品是否选中
                // 计算价格
                calculatePrice();
            }
        });
        // 设置商品的复选框
        if (goods.getSelected()==0) {
            childHolder.check.setChecked(false);
        }else{
            childHolder.check.setChecked(true);
        }
        //设置图片
        String images = goods.getImages();
        String[] split = images.split("\\|");
        if (split .length>0) {
            Glide.with(parent.getContext()).load(split[0].replace("https","http")).into(childHolder.image);
        }
        // 设置次商品的数量
        childHolder.addSub.setCount(goods.getNum());
        childHolder.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int number) {
                goods.setNum(number);//设置数量
                calculatePrice();// 计算价格
            }
        });
        return convertView;
    }
    class ChildHolder {
        CheckBox check;
        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
