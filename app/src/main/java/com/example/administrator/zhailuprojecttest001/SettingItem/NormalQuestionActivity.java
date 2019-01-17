package com.example.administrator.zhailuprojecttest001.SettingItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;

public class NormalQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NormalQuestionActivity";

    private LinearLayout llContent1;
    private LinearLayout llContent2;
    private LinearLayout llContent3;
    private ImageView imageClick1;
    private ImageView imageClick2;
    private ImageView imageClick3;
    private int clickFlag=0;
    private View view;
    private ViewGroup.LayoutParams layoutParams;
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_question);
        density = getResources()
                .getDisplayMetrics()
                .density;
        Log.i(TAG, "onCreate: 屏幕密度: "+density);

        initFirst();

    }

    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("常见问题");
        llContent1=findViewById(R.id.ll_question_content_1);
        llContent2=findViewById(R.id.ll_question_content_2);
        llContent3=findViewById(R.id.ll_question_content_3);
        llContent1.setVisibility(View.GONE);
        llContent2.setVisibility(View.GONE);
        llContent3.setVisibility(View.GONE);
        imageClick1=findViewById(R.id.image_question_click1);
        imageClick2=findViewById(R.id.image_question_click2);
        imageClick3=findViewById(R.id.image_question_click3);
        view=findViewById(R.id.view12);
        layoutParams=view.getLayoutParams();
        layoutParams.height=(180*(int)density);
        initClick();
    }

    public void initClick(){
        LinearLayout llTitleBack=findViewById(R.id.layout_settings_title).findViewById(R.id.ll_settings_back);
        llTitleBack.setOnClickListener(this);
        LinearLayout llClick1=findViewById(R.id.ll_question_click_1);
        LinearLayout llClick2=findViewById(R.id.ll_question_click_2);
        LinearLayout llClick3=findViewById(R.id.ll_question_click_3);
        llClick1.setOnClickListener(this);
        llClick2.setOnClickListener(this);
        llClick3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_settings_back:
                finish();
                break;
            case R.id.ll_question_click_1:
                llClickMethod(1);
                break;
            case R.id.ll_question_click_2:
                llClickMethod(2);
                break;
            case R.id.ll_question_click_3:
                llClickMethod(3);
                break;
        }
    }

    public void llClickMethod(int id){
        if (id==1){
            if (clickFlag==0){
                llContent1.setVisibility(View.VISIBLE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_down);
                layoutParams.height=((180+102)*(int)density);
                clickFlag=1;
            }else if (clickFlag==1){
                llContent1.setVisibility(View.GONE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_right);
                layoutParams.height=(180*(int)density);
                clickFlag=0;
            }else if (clickFlag==2){
                llContent1.setVisibility(View.VISIBLE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent2.setVisibility(View.GONE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=1;
            }else if(clickFlag==3){
                llContent1.setVisibility(View.VISIBLE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent3.setVisibility(View.GONE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=1;
            }
        }else if (id==2){
            if (clickFlag==0){
                llContent2.setVisibility(View.VISIBLE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_down);
                layoutParams.height=((180+102)*(int)density);
                clickFlag=2;
            }else if (clickFlag==1){
                llContent2.setVisibility(View.VISIBLE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent1.setVisibility(View.GONE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=2;
            }else if (clickFlag==2){
                llContent2.setVisibility(View.GONE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_right);
                layoutParams.height=(180*(int)density);
                clickFlag=0;
            }else if(clickFlag==3){
                llContent2.setVisibility(View.VISIBLE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent3.setVisibility(View.GONE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=2;
            }
        }else if (id==3){
            if (clickFlag==0){
                llContent3.setVisibility(View.VISIBLE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_down);
                layoutParams.height=((180+102)*(int)density);
                clickFlag=3;
            }else if (clickFlag==1){
                llContent3.setVisibility(View.VISIBLE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent1.setVisibility(View.GONE);
                imageClick1.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=3;
            }else if (clickFlag==2){
                llContent3.setVisibility(View.VISIBLE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_down);
                llContent2.setVisibility(View.GONE);
                imageClick2.setBackgroundResource(R.drawable.icon_arrow_right);
                clickFlag=3;
            }else if(clickFlag==3){
                llContent3.setVisibility(View.GONE);
                imageClick3.setBackgroundResource(R.drawable.icon_arrow_right);
                layoutParams.height=(180*(int)density);
                clickFlag=0;
            }
        }
    }
}
