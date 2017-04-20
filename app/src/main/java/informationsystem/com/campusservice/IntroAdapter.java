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
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.schoolProTitle = (TextView)convertView.findViewById(R.id.introduction);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.schoolProTitle.setText(schoolProfile.getTitle());
        return convertView;
    }

    class ViewHolder{
        TextView schoolProTitle;
    }
}
