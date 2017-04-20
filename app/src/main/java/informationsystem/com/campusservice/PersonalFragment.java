package informationsystem.com.campusservice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import informationsystem.com.campusservice.database.CampusServiceDB;
import informationsystem.com.campusservice.model.PersonalInfomation;

/**
 * Created by Chen on 2017/4/2.
 */
public class PersonalFragment extends Fragment implements OnClickListener, OnItemClickListener{

    private final int SETTING = 1;
    private final int SETTING_OK = 2;
    private LinearLayout setting;
    private Bundle bundle;
    private TextView introduce;
    private TextView nickname;
    private CampusServiceDB db;
    private PersonalInfomation person;
    private GridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> list;
    private int[] icon= new int[]{R.drawable.campus_activity};
    private String[] iconName = new String[]{"校园活动"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_frag,container,false);

        setting = (LinearLayout) view.findViewById(R.id.setting);
        introduce = (TextView) view.findViewById(R.id.introduce);
        nickname = (TextView) view.findViewById(R.id.Name);
        gridView = (GridView) view.findViewById(R.id.gridView);
        db = CampusServiceDB.getInstance(getActivity());
        bundle = getArguments();

        list = new ArrayList<Map<String,Object>>();
        adapter = new SimpleAdapter(getActivity(),getData(),R.layout.layout_gridview,
                new String[]{"image","title"},
                new int[]{R.id.image,R.id.activity_title});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        //从数据库中读取用户信息并初始化昵称和个人简介
        person = db.queryPersonalInfo(bundle.getString("userName"));
        nickname.setText(person.getNickname());
        introduce.setText(person.getIntroduction());

        //为设置操作设置监听事件
        setting.setOnClickListener(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        person = db.queryPersonalInfo(bundle.getString("userName"));
        nickname.setText(person.getNickname());
        introduce.setText(person.getIntroduction());
        Log.i("PersonalFragment","onStart()");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),SettingsActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,SETTING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTING) {
            //设置成功
            if (resultCode == SETTING_OK) {
                PersonalInfomation person = db.queryPersonalInfo(bundle.getString("userName"));
                nickname.setText(person.getNickname());
                introduce.setText(person.getIntroduction());
            }
            //退出登录
            if(resultCode == getActivity().RESULT_OK){
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment fragment5 = getFragmentManager().findFragmentByTag("Personal_Info");
                transaction.remove(fragment5);
                Fragment fragment4 = getFragmentManager().findFragmentByTag("Login");
                if (fragment4 == null){
                    transaction.replace(R.id.frame,loginFragment,"Login");
                }else{
                    transaction.show(fragment4);
                }
                transaction.commit();
            }
        }
    }

    public List<Map<String,Object>> getData(){
        for(int i = 0;i < icon.length;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("title", iconName[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
