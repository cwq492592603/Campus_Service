package informationsystem.com.campusservice;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import informationsystem.com.campusservice.database.CampusServiceDB;
import informationsystem.com.campusservice.model.PersonalInfomation;

/**
 * Created by Chen on 2017/4/1.
 */
public class LoginFragment extends Fragment implements OnClickListener {

    private EditText userName;
    private EditText password;
    private Button login;
    private Button registered;
    private CampusServiceDB db;
    public final int NO_USER = 0;
    private final int ERROR_PASSWORD = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_frag, container, false);
        userName = (EditText) view.findViewById(R.id.userName);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.login);
        registered = (Button) view.findViewById(R.id.registered);
        db = CampusServiceDB.getInstance(getActivity());

        login.setOnClickListener(this);
        registered.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                setLogin();
                break;
            case R.id.registered:
                setRegistered();
                break;
        }

    }

    /**
     * 查询个人信息数据库是否存在此用户
     * 如存在，则输入正确密码后登入
     * 否则，提示错误消息
     */
    public void setLogin() {
        String name;
        String pass;
        name = userName.getText().toString();
        pass = password.getText().toString();
        if (!db.queryUser(name)) {
            //不存在用户
            showDialog(NO_USER);
        } else {
            //存在此用户
            PersonalInfomation person = db.queryPersonalInfo(name);
            String result = person.getPassword();
            if (pass.equals(result)) {
                //存在此用户且密码正确
                PersonalFragment personalFragment = new PersonalFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userName", name);
                personalFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, personalFragment,"Personal_Info");
                transaction.commit();
            } else {
                //存在此用户但密码不正确
                showDialog(ERROR_PASSWORD);
            }
        }
    }

    /**
     * 弹出提示框
     *
     * @param request
     */
    public void showDialog(int request) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("错误！");
        switch (request) {
            case NO_USER:
                dialog.setMessage("用户不存在，如需登录请注册");
                break;
            case ERROR_PASSWORD:
                dialog.setMessage("密码错误！请重新输入");
                break;
        }

        dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    /**
     * 注册用户
     */
    public void setRegistered() {
        Intent intent = new Intent(getActivity(), RegisteredUserActivity.class);
        startActivity(intent);
    }
}
