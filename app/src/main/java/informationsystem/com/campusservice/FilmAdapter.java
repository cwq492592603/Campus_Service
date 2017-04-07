package informationsystem.com.campusservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import informationsystem.com.campusservice.model.FilmInfomation;

/**
 * Created by Chen on 2017/4/2.
 */
public class FilmAdapter extends ArrayAdapter<FilmInfomation> {

    private int resourceId;
    public FilmAdapter(Context context, int resource, List<FilmInfomation> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilmInfomation filmInfo = getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view = convertView;
        }
        return view;
    }
}
