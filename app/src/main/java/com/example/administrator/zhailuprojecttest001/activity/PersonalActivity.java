package com.example.administrator.zhailuprojecttest001.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.GlideApp;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.gsonData6_7.Result6;
import com.example.administrator.zhailuprojecttest001.gsonData6_7.Result7;
import com.example.administrator.zhailuprojecttest001.retrofit.Data8Personal;
import com.example.administrator.zhailuprojecttest001.retrofit.Data9PersonalSave;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PersonalActivity";

    //这个是网络请求返回值
    private String responseString;
    private String responseString2;
    //这个是本地用户登录信息,userId
    private String userId;
    //这个是网络请求返回值的gson解析实体类
    private Result7 result7;
    //这个用于加载View
    private LayoutInflater inflater;

    //这个是用户修改的值
    private String changeSex;//保密0,男1,女2
    private String changeAge;
    private String changeNickName;
    private String changeSchool;
    //用户修改信息后保存的flag
    public static int flagSave=0;

    //这个是屏幕密度
    private float density;

    //这个是拍照和选择相册的返回标志
    private static final int TAKE_PHOTO=1;
    private static final int CHOOSE_PHOTO=2;
    //这个是调用拍照保存到图片文件的Uri
    private Uri imageUri;
    //这个是头像控件
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_d);
        //初始化UI
        initFirst();
        //获取本地userId
        userId=new GetSPData().getSPUserID(PersonalActivity.this);
        //网络请求用户数据
        retrofitV8(userId);
        //初始化加载View的LayoutInflater类
        inflater = LayoutInflater.from(getApplication());
        //获取屏幕密度
        density = getApplication().getResources()
                .getDisplayMetrics()
                .density;
        //打印一下屏幕密度,看下是否正确
        Log.i(TAG, "density: "+density);
        //初始化头像控件
        circleImageView=findViewById(R.id.circle_image_avatar);
    }

    public void initFirst(){
        //改变标题
        TextView title=findViewById(R.id.layout_personal_title).findViewById(R.id.textview_title);
        title.setText("我的资料");
        //保存按钮可见
        LinearLayout llSave=findViewById(R.id.layout_personal_title).findViewById(R.id.ll_settings_save);
        llSave.setVisibility(View.VISIBLE);
        //初始化点击事件
        initFirstClick();
    }

    public void initFirstClick(){
        //返回按钮点击事件
        LinearLayout llTitleBack=findViewById(R.id.layout_personal_title).findViewById(R.id.ll_settings_back);
        llTitleBack.setOnClickListener(this);
        //性别修改点击事件
        LinearLayout llSex=findViewById(R.id.ll_settings_list_2);
        llSex.setOnClickListener(this);
        //年龄修改点击事件
        LinearLayout llAge=findViewById(R.id.ll_settings_list_3);
        llAge.setOnClickListener(this);
        //昵称修改点击事件
        LinearLayout llNickName=findViewById(R.id.ll_settings_list_1);
        llNickName.setOnClickListener(this);
        //学校修改点击事件
        LinearLayout llSchool=findViewById(R.id.ll_settings_list_4);
        llSchool.setOnClickListener(this);
        //头像修改点击事件
        LinearLayout llAvatar=findViewById(R.id.ll_settings_list_0);
        llAvatar.setOnClickListener(this);
        TextView textViewSave=findViewById(R.id.textview_save);
        textViewSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            //返回按钮点击事件具体实现,出栈当前Activity
            case R.id.ll_settings_back:
                finish();
                break;
            //性别修改点击事件具体实现,弹窗选择
            case R.id.ll_settings_list_2:
                View view = inflater.inflate(R.layout.item_dialog_sex, null);
                AlertDialog.Builder builder=new AlertDialog.Builder(PersonalActivity.this);
                builder.setView(view);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
                WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
                params.width = Math.round((float) 280 * density);
                params.height = Math.round((float) 212 * density);
                alertDialog.getWindow().setAttributes(params);
                RadioButton r1=view.findViewById(R.id.radiobutton_sex_1);
                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        changeSex="0";
                        TextView textViewSex=findViewById(R.id.text_sex);
                        textViewSex.setText("保密");
                    }
                });
                RadioButton r2=view.findViewById(R.id.radiobutton_sex_2);
                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        changeSex="1";
                        TextView textViewSex=findViewById(R.id.text_sex);
                        textViewSex.setText("男");
                    }
                });
                RadioButton r3=view.findViewById(R.id.radiobutton_sex_3);
                r3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        changeSex="2";
                        TextView textViewSex=findViewById(R.id.text_sex);
                        textViewSex.setText("女");
                    }
                });
                break;
            //年龄修改点击事件具体实现,弹窗输入,待改进
            case R.id.ll_settings_list_3:
                final View view2 = inflater.inflate(R.layout.item_dialog_age, null);
                AlertDialog.Builder builder2=new AlertDialog.Builder(PersonalActivity.this);
                builder2.setView(view2);
                final AlertDialog alertDialog2=builder2.create();
                alertDialog2.show();
                WindowManager.LayoutParams params2 = alertDialog2.getWindow().getAttributes();
                params2.width = Math.round((float) 280 * density);
                params2.height = Math.round((float) 160 * density);
                alertDialog2.getWindow().setAttributes(params2);
                Button button2=view2.findViewById(R.id.button_age_confirm);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText2=view2.findViewById(R.id.edit_age_personal);
                        String editAge2=editText2.getText().toString();
                        int age=Integer.parseInt(editAge2);
                        if (age>=0&&age<=150){
                            //输入格式正确
                            alertDialog2.dismiss();
                            changeAge=editAge2;
                            TextView textViewAge=findViewById(R.id.text_age_personal);
                            textViewAge.setText(changeAge);
                        }else{
                            //输入格式错误
                        }
                    }
                });
                break;
            //昵称修改点击事件具体实现,弹窗输入,待改进
            case R.id.ll_settings_list_1:
                final View view3 = inflater.inflate(R.layout.item_dialog_nickname, null);
                AlertDialog.Builder builder3=new AlertDialog.Builder(PersonalActivity.this);
                builder3.setView(view3);
                final AlertDialog alertDialog3=builder3.create();
                alertDialog3.show();
                WindowManager.LayoutParams params3 = alertDialog3.getWindow().getAttributes();
                params3.width = Math.round((float) 280 * density);
                params3.height = Math.round((float) 160 * density);
                alertDialog3.getWindow().setAttributes(params3);
                Button button3=view3.findViewById(R.id.button_nickname_confirm);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText3=view3.findViewById(R.id.edit_age_personal);
                        String editNick3=editText3.getText().toString();
                        FormatVf formatVf=new FormatVf();
                        if (formatVf.isNickName(editNick3)){
                            //格式正确
                            alertDialog3.dismiss();
                            changeNickName=editNick3;
                            TextView textViewNickName=findViewById(R.id.text_nickname_personal);
                            textViewNickName.setText(changeNickName);
                        }else {
                            //格式错误
                        }
                    }
                });
                break;
            //学校修改点击事件具体实现,弹窗输入,待改进
            case R.id.ll_settings_list_4:
                final View view4 = inflater.inflate(R.layout.item_dialog_school, null);
                AlertDialog.Builder builder4=new AlertDialog.Builder(PersonalActivity.this);
                builder4.setView(view4);
                final AlertDialog alertDialog4=builder4.create();
                alertDialog4.show();
                WindowManager.LayoutParams params4 = alertDialog4.getWindow().getAttributes();
                params4.width = Math.round((float) 280 * density);
                params4.height = Math.round((float) 160 * density);
                alertDialog4.getWindow().setAttributes(params4);
                Button button4=view4.findViewById(R.id.button_school_confirm);
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText4=view4.findViewById(R.id.edit_age_personal);
                        String editNick4=editText4.getText().toString();
                        FormatVf formatVf=new FormatVf();
                        if (formatVf.isSchoolName(editNick4)){
                            //格式正确
                            alertDialog4.dismiss();
                            changeSchool=editNick4;
                            TextView textViewSchool=findViewById(R.id.text_school_personal);
                            textViewSchool.setText(changeSchool);
                        }else {
                            //格式错误
                        }
                    }
                });
                break;
                //头像修改点击事件实现,弹窗,调用图库图片或者调用相机
            case R.id.ll_settings_list_0:
                View view5 = inflater.inflate(R.layout.item_dialog_avatar, null);
                AlertDialog.Builder builder5=new AlertDialog.Builder(PersonalActivity.this);
                builder5.setView(view5);
                final AlertDialog alertDialog5=builder5.create();
                alertDialog5.show();
                WindowManager.LayoutParams params5 = alertDialog5.getWindow().getAttributes();
                params5.width = Math.round((float) 280 * density);
                params5.height = Math.round((float) 162 * density);
                alertDialog5.getWindow().setAttributes(params5);
                //获取写入权限的请求
                if (ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonalActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                //调用图库
                RadioButton radioButton1=view5.findViewById(R.id.radiobutton_avatar_1);
                radioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,CHOOSE_PHOTO);
                        alertDialog5.dismiss();
                    }
                });
                //调用相机
                RadioButton radioButton2=view5.findViewById(R.id.radiobutton_avatar_2);
                radioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File file=new File(getCacheDir(),"take_photo_avatar1.jpg");
                        try{
                            if (file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT>=24){
                            imageUri=FileProvider.getUriForFile(PersonalActivity.this,"personalFileProvider",file);
                        }else {
                            imageUri=Uri.fromFile(file);
                        }
                        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
                        alertDialog5.dismiss();
                    }
                });
                break;
            case R.id.textview_save:
                //保存用户修改后的个人信息,post数据到服务器
                if (changeAge!=null&&changeNickName!=null&&changeSex!=null&&changeSchool!=null){
                    retrofitV9(userId,changeNickName,changeSex,changeAge,changeSchool);
                }
                break;
        }
    }

    //网络请求用户个人资料数据
    public void retrofitV8(final String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/v1/user/")
                .build();
        Data8Personal data8Personal = retrofit.create(Data8Personal.class);
        Call<ResponseBody> call = data8Personal.getData(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString = response.body().string();
                    Log.i(TAG, "onResponse测试: " + responseString);
                    JSONObject jsonObject=new JSONObject(responseString);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    if ("success".equals(msg)){
                        //获取数据成功,解析数据
                        Gson gson=new Gson();
                        result7=gson.fromJson(data,Result7.class);
                    }else {
                        //获取数据失败
                    }
                    Log.i(TAG, "onResponse: "+result7+" "+(result7==null));
                    if (result7==null){
                        //数据为空
                    }else{
                        //展示数据到UI
                        showResponseResult();
                        //更新用户修改数据,初始化为当前用户信息
                        changeSex=result7.getGender();
                        changeAge=result7.getAge();
                        changeNickName=result7.getNickname();
                        changeSchool=result7.getSchool_name();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: " + t.toString());
            }
        });
    }
    //请求完个人资料之后的更新操作
    public void showResponseResult(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textViewSex=findViewById(R.id.text_sex);
                if (result7.getGender().equals("0")){
                    textViewSex.setText("保密");
                }else if (result7.getGender().equals("1")){
                    textViewSex.setText("男");
                }else if (result7.getGender().equals("2")){
                    textViewSex.setText("女");
                }
                TextView textViewNickName=findViewById(R.id.text_nickname_personal);
                textViewNickName.setText(result7.getNickname());
                TextView textViewAge=findViewById(R.id.text_age_personal);
                textViewAge.setText(result7.getAge());
                TextView textViewSchool=findViewById(R.id.text_school_personal);
                if ("".equals(result7.getSchool_name())|result7.getSchool_name()==null){
                    //学校名称不存在,
                }else {
                    textViewSchool.setText(result7.getSchool_name());
                }
                if ("".equals(result7.getAvatar())|result7.getAvatar()==null){
                    //头像不存在
                }else {
                    String avatarURL=result7.getAvatar();
                    CircleImageView circleImageView=findViewById(R.id.circle_image_avatar);
                    GlideApp.with(PersonalActivity.this).load(avatarURL).into(circleImageView);
                }
            }
        });
    }

    //保存用户修改数据,post传递数据
    public void retrofitV9(final String userId,final String nickName,final String gender,final String age,final String school_name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/v1/user/")
                .build();
        Data9PersonalSave data9PersonalSave = retrofit.create(Data9PersonalSave.class);
        Call<ResponseBody> call = data9PersonalSave.postData(userId,nickName,gender,age,school_name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString2 = response.body().string();
                    Log.i(TAG, "onResponse测试: " + responseString2);
                    JSONObject jsonObject=new JSONObject(responseString2);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    if ("success".equals(msg)){
                        Log.i(TAG, "onResponse: 个人信息保存成功");
                        flagSave=1;
                        Toast toast=Toast.makeText(PersonalActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("个人信息保存成功");
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: " + t.toString());
            }
        });
    }

    //权限的动态获取
    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法正常使用",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //调用相机和图库的回调,相机用的是图片文件的Uri,图库用的是图片文件的封装后的Uri解析得到的文件真实路径
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try{
                        String filePath=new File(getCacheDir(),"take_photo_avatar1.jpg").getPath();
                        ExifInterface exifInterface = new ExifInterface(filePath);
                        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        Log.i(TAG, "onActivityResult: orientation "+orientation);
                        Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        if (orientation==6){
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                            circleImageView.setImageBitmap(bitmap1);
                        }else if (orientation==0||orientation==1){
                            circleImageView.setImageBitmap(bitmap);
                        }else if (orientation==3){
                            Matrix matrix = new Matrix();
                            matrix.postRotate(180);
                            Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                            circleImageView.setImageBitmap(bitmap1);
                        }else if (orientation==8){
                            Matrix matrix = new Matrix();
                            matrix.postRotate(270);
                            Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                            circleImageView.setImageBitmap(bitmap1);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode==RESULT_OK){
                    if (Build.VERSION.SDK_INT>=19){
                        handleImageNow(data);
                    }else {
                        handleImageBefore(data);
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void handleImageNow(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri=ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    public void handleImageBefore(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    public String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void displayImage(String imagePath){
        if (imagePath!=null){
            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                Log.i(TAG, "displayImage: orientation " + orientation);
                Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
                if (orientation == 6) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    circleImageView.setImageBitmap(bitmap1);
                } else if (orientation == 0 || orientation == 1) {
                    circleImageView.setImageBitmap(bitmap);
                } else if (orientation == 3) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(180);
                    Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    circleImageView.setImageBitmap(bitmap1);
                } else if (orientation == 8) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(270);
                    Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    circleImageView.setImageBitmap(bitmap1);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.i(TAG, "displayImage: 图片路径存在问题");
        }
    }

}
