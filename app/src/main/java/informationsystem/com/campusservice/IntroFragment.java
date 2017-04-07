package informationsystem.com.campusservice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen on 2017/3/18.
 */
public class IntroFragment extends Fragment implements OnItemClickListener{

    private ListView introListView;
    private List<SchoolProfile>list;
    private IntroAdapter adapter;

//    public IntroFragment(){
//        list = getList();
//        adapter = new IntroAdapter(getActivity(),R.layout.intro_frag,R.id.intro_listView,list);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.intro_frag,container,false);
        initList();
//        Toast.makeText(getActivity(),"将显示数据",Toast.LENGTH_SHORT).show();
        adapter = new IntroAdapter(getActivity(),R.layout.introduction,list);
        introListView = (ListView) view.findViewById(R.id.intro_listView);
        introListView.setAdapter(adapter);
        introListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SchoolProfile schoolProfile = list.get(position);
        DetailsContentActivity.actionStart(getActivity(),schoolProfile.getTitle(),schoolProfile.getContent());
    }

    /*
    private void getList(){
        list = new ArrayList<SchoolProfile>();
        SchoolProfile schoolProfile = new SchoolProfile();
        schoolProfile.setTitle("学校简介");
        schoolProfile.setContent("主页");
        list.add(schoolProfile);
    }
    */

    private void initList(){
        list = new ArrayList<>();
        SchoolProfile file1 = new SchoolProfile();
        FileInputStream fis = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = getActivity().openFileInput("whutProfile.txt");
            br = new BufferedReader(new InputStreamReader(fis));
            String result = "";
            while((result = br.readLine()) != null){
                stringBuilder.append(result);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(),"未找到文件",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getActivity(),"读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                    fis.close();
                } catch (IOException e) {
                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        file1.setTitle("学校简介");
        file1.setContent(stringBuilder.toString());
        list.add(file1);
    }
}
