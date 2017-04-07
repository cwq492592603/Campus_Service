package informationsystem.com.campusservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import informationsystem.com.campusservice.database.CampusServiceDB;
import informationsystem.com.campusservice.model.PersonalInfomation;

public class SettingsActivity extends Activity implements View.OnClickListener{

    private Bundle bundle;
    private TextView login_name;
    private TextView nickname;
    private TextView sex;
    private TextView introduction;
    private ImageView back;
    private Button backTologin;
    private final int SELF_INTRODUCTION = 1;
    private final int SETTING_OK = 2;
    private CampusServiceDB db;
    private PersonalInfomation person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        login_name = (TextView) findViewById(R.id.loginName);
        nickname = (TextView) findViewById(R.id.nickname);
        sex = (TextView) findViewById(R.id.sex);
        introduction = (TextView) findViewById(R.id.self_introduction);
        back = (ImageView) findViewById(R.id.returnback);
        backTologin = (Button) findViewById(R.id.backTologin);
        db = CampusServiceDB.getInstance(this);

        nickname.setOnClickListener(this);
        sex.setOnClickListener(this);
        introduction.setOnClickListener(this);
        back.setOnClickListener(this);
        backTologin.setOnClickListener(this);

        //从数据库读取个人信息初始化相应资料
        bundle = getIntent().getExtras();
        person = db.queryPersonalInfo(bundle.getString("userName"));
        login_name.setText(person.getUserName());
        nickname.setText(person.getNickname());
        sex.setText(person.getSex());
        if(!person.getIntroduction().isEmpty() || person.getIntroduction() != null) {
            introduction.setText(person.getIntroduction());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nickname:
                setNickname();
                break;
            case R.id.sex:
                setSex();
                break;
            case R.id.self_introduction:
                setSelfIntroduction();
                break;
            case R.id.returnback:
                setResult(SETTING_OK);
                finish();
                break;
            case R.id.backTologin:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        person.setUserName();
        person.setIntroduction(introduction.getText().toString());
        person.setSex(sex.getText().toString());
        person.setNickname(nickname.getText().toString());
        db.savePersonalInfo(person);
        Log.i("SettingsActivity","onDestroy()");
    }

    /**
     * 设置昵称
     */
    public void setNickname(){
        final View layout = getLayoutInflater().inflate(R.layout.edittext,null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("编辑昵称");
        alertDialog.setView(layout);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText dt=(EditText) layout.findViewById(R.id.edittext);
                String str = dt.getText().toString();
                nickname.setText(str);
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog ad = alertDialog.create();
        ad.show();
    }

    /**
     * 设置性别
     */
    public void setSex(){
        final String[] s = new String[1];
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("性别修改");
        final String[] item = new String[]{"男","女","保密"};
        dialog.setSingleChoiceItems(item, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                s[0] = item[which];
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex.setText(s[0]);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 设置个人简介
     */
    public void setSelfIntroduction(){
        Intent intent = new Intent(this,SetSelfIntroductionActivity.class);
        intent.putExtra("self_introduction",introduction.getText().toString());
        startActivityForResult(intent,SELF_INTRODUCTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELF_INTRODUCTION && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String str = bundle.getString("self_introduction");
            introduction.setText(str);
        }
    }

}
