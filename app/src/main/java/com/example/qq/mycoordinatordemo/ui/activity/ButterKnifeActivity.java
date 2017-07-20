package com.example.qq.mycoordinatordemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.view.inter_face.AutoFitTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public class ButterKnifeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.text_user)
    AutoFitTextView textUser;
    @BindView(R.id.text_set)
    AutoFitTextView textSet;
    @BindView(R.id.text_age)
    AutoFitTextView textAge;
    @BindView(R.id.text_email)
    AutoFitTextView textEmail;
    @BindView(R.id.text_favorite)
    AutoFitTextView textFavorite;
    @BindView(R.id.text_address)
    AutoFitTextView textAddress;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_kinfe);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        textUser.setText("崔琦");
        textSet.setText("男");
        textAge.setText("23");
        textEmail.setText("597582117@qq.com");
        textFavorite.setText("篮球 台球 唱歌 游戏");
        textAddress.setText("山东省青岛市市南区宁夏路大润发");
    }
    @OnClick({R.id.btn_ok, R.id.btn_cancel})
    @Override
    public void onClick(View view) {
        String userName = "我爱你崔小荻";
        String passWord = "1314521";
        String mUserName = username.getText().toString();
        String mPassword = password.getText().toString();
        switch (view.getId()){
            case R.id.btn_ok:
                if (mUserName.isEmpty()&&mPassword.isEmpty()){
                    Toast.makeText(ButterKnifeActivity.this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
                }else if (mUserName.isEmpty() || mPassword.isEmpty()){
                    Toast.makeText(ButterKnifeActivity.this, "输入不完整", Toast.LENGTH_SHORT).show();
                }else if (mUserName.equals(userName)&&mPassword.equals(passWord)){
                    Toast.makeText(ButterKnifeActivity.this, "恭喜您进入hiphop世界！！！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ButterKnifeActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
