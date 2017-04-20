package informationsystem.com.campusservice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.main);
        radioGroup.setOnCheckedChangeListener(this);

        IntroFragment fragment1 = new IntroFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.frame,fragment1,"School_Intro");
        beginTransaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        Fragment fragment1 = fragmentManager.findFragmentByTag("School_Intro");
        Fragment fragment2 = fragmentManager.findFragmentByTag("Attraction");
        Fragment fragment3 = fragmentManager.findFragmentByTag("Film_Info");
        Fragment fragment4 = fragmentManager.findFragmentByTag("Login");
        Fragment fragment5 = fragmentManager.findFragmentByTag("Personal_Info");
        if(fragment1 != null){
            beginTransaction.hide(fragment1);
        }
        if(fragment2 != null){
            beginTransaction.hide(fragment2);
        }
        if(fragment3 != null){
            beginTransaction.hide(fragment3);
        }
        if(fragment4 != null){
            beginTransaction.hide(fragment4);
        }
        if(fragment5 != null){
            beginTransaction.hide(fragment5);
        }
        switch (checkedId){
            case R.id.main:
                if(fragment1 == null) {
                    IntroFragment introFragment = new IntroFragment();
                    beginTransaction.add(R.id.frame, introFragment, "School_Intro");
                }else {
                    beginTransaction.show(fragment1);
                }
                break;
            case R.id.main1:{
                if(fragment2 == null) {
                    AttractionFragment attractionFragment = new AttractionFragment();
                    beginTransaction.add(R.id.frame, attractionFragment, "Attraction");
                }else {
                    beginTransaction.show(fragment2);
                }
                break;
            }
            case R.id.main2:{
                if(fragment3 == null) {
                    FilmFragment filmFragment = new FilmFragment();
                    beginTransaction.add(R.id.frame, filmFragment, "Film_Info");
                }else {
                    beginTransaction.show(fragment3);
                }
                break;
            }
            case R.id.main3:{
                if(fragment5 == null) {
                    if (fragment4 == null) {
                        LoginFragment loginFragment = new LoginFragment();
                        beginTransaction.add(R.id.frame, loginFragment, "Login");
                    }else {
                        beginTransaction.show(fragment4);
                    }
                }else{
                    beginTransaction.show(fragment5);
                }
                break;
            }
        }
        beginTransaction.commit();
    }
}
