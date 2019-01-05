package com.example.administrator.zhailuprojecttest001.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;

public class GetbackPw2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getback_pw2);
    }

    public void initEdit(){
        EditText editText1=findViewById(R.id.editText1);
        EditText editText2=findViewById(R.id.editText2);
        EditText editText3=findViewById(R.id.editText3);
        EditText editText4=findViewById(R.id.editText4);
    }

    public void initClick() {
        Button button=findViewById(R.id.button_again);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_again:
                break;

        }
    }
}
