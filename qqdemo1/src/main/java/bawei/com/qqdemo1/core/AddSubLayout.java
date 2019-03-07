package bawei.com.qqdemo1.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bawei.com.qqdemo1.R;

public class AddSubLayout extends LinearLayout implements View.OnClickListener{
    private TextView jian;
    private TextView jia;
    private TextView num;
    private AddSubListener addSubListener;
    // 实例化接口的变量
    public void setAddSubListener(AddSubListener addSubListener) {
        this.addSubListener = addSubListener;
    }

    public AddSubLayout(Context context) {
        super(context);
        initView();
    }

    public AddSubLayout(Context context,   AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddSubLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
//        第三个参数写成this
        View view = View.inflate(getContext(), R.layout.group_item,this);
        jian = view.findViewById(R.id.add_jian);
        jia = view.findViewById(R.id.add_jia);
        num = view.findViewById(R.id.num);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // 得到数量的值
        int number = Integer.parseInt(num.getText().toString());

        switch (v.getId()){
            case R.id.add_jia:
                number++;
                num.setText(number+"");
                break;
            case R.id.add_jian:
                if (number == 0) {
                    Toast.makeText(getContext(), "数量不可小于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                number--;
                num.setText(number+"");
                break;
        }
        if (addSubListener!=null) {
            addSubListener.addSub(number);
        }

    }
    public void setCount(int count) {
        num.setText(count+"");
    }
    // 自定义接口
    public interface AddSubListener{
        void addSub(int number);
    }

}
