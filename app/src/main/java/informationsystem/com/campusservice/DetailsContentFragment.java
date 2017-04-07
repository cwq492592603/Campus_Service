package informationsystem.com.campusservice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chen on 2017/3/18.
 */
public class DetailsContentFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Layout布局文件转换成View对象
        view = inflater.inflate(R.layout.details, container, false);
        return view;
    }

    public void refresh(String title, String content) {
        TextView intro = (TextView) view.findViewById(R.id.title);
        TextView details = (TextView) view.findViewById(R.id.content);
        intro.setText(title);
        details.setText(content);
    }
}
