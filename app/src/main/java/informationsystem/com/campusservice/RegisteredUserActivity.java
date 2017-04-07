package informationsystem.com.campusservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import informationsystem.com.campusservice.database.CampusServiceDB;
import informationsystem.com.campusservice.model.PersonalInfomation;

public class RegisteredUserActivity extends Activity implements TextWatcher{

    private EditText name;
    private EditText pass;
    private EditText passAgain;
    private Button register;
    private String str_name;
    private String str_pass;
    private String str_passAgain;
    private final int BACK = 1;
    private final int USERNAME = 2;
    private final int PASSWORD_ERROR = 3;
    private CampusServiceDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user);

        register = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        passAgain = (EditText) findViewById(R.id.passAgain);
        db = CampusServiceDB.getInstance(this);

        name.addTextChangedListener(this);
        pass.addTextChangedListener(this);
        passAgain.addTextChangedListener(this);
        register.setEnabled(false);
    }

    private void registerUser() {
        str_name = name.getText().toString();
        str_pass = pass.getText().toString();
        str_passAgain = passAgain.getText().toString();
        PersonalInfomation person;
        if (db.queryUser(str_name)) {
            //用户存在
            showTag(USERNAME);
        } else if ((!db.queryUser(str_name)) && (str_pass.equals(str_passAgain))) {
            //注册成功
            person = new PersonalInfomation();
            person.setUserName(str_name);
            person.setPassword(str_pass);
            db.saveLoginInfo(person);
            Intent intent = new Intent(RegisteredUserActivity.this, RegisteredSuccessActivity.class);
            startActivityForResult(intent, BACK);
        } else {
            //密码设置错误
            showTag(PASSWORD_ERROR);
        }
    }

    private void showTag(int requestCode) {
        AlertDialog.Builder tag = new AlertDialog.Builder(this);
        tag.setTitle("提示");
        switch (requestCode) {
            case USERNAME:
                tag.setMessage("该用户名已存在！");
                break;
            case PASSWORD_ERROR:
                tag.setMessage("两次密码输入不相同！");
        }
        tag.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        tag.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BACK) {
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(name.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || passAgain.getText().toString().isEmpty()){
            register.setEnabled(false);
        }else{
            register.setEnabled(true);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });
        }

    }
}
