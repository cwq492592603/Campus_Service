package informationsystem.com.campusservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chen on 2017/3/18.
 */
public class IntroAdapter extends ArrayAdapter<SchoolProfile> {
    private int resourceId;

    public IntroAdapter(Context context, int resource, List<SchoolProfile> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SchoolProfile schoolProfile = getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view = convertView;
        }
        TextView schoolProfileTitle = (TextView)view.findViewById(R.id.introduction);
        schoolProfileTitle.setText(schoolProfile.getTitle());
        return view;
    }
}
