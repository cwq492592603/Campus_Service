package informationsystem.com.campusservice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import informationsystem.com.campusservice.model.FilmInfomation;

/**
 * Created by Chen on 2017/3/30.
 */
public class FilmFragment extends Fragment {
    private ListView listView;
    private FilmAdapter adapter;
    private List<FilmInfomation> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_frag,container,false);

        adapter = new FilmAdapter(getActivity(),R.layout.film_intro,list);
        listView = (ListView) view.findViewById(R.id.film_listview);
        listView.setAdapter(adapter);
        return view;
    }
}
