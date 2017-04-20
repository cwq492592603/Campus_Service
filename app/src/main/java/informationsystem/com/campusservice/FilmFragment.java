package informationsystem.com.campusservice;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import informationsystem.com.campusservice.model.FilmInformation;

/**
 * Created by Chen on 2017/3/30.
 */
public class FilmFragment extends Fragment {
    private ListView listView;
    private FilmAdapter adapter;
    private List<FilmInformation> list;
    private static String city = "武汉";
    private static String key = "f51f79c6a80e180e45af6564e506c6ce";
    private static String url = "http://op.juhe.cn/onebox/movie/pmovie?dtype=&city=%E6%AD%A6%E6%B1%89&key=f51f79c6a80e180e45af6564e506c6ce";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_frag, container, false);

        listView = (ListView) view.findViewById(R.id.film_listview);
        new FilmAsyncTask().execute(url);
        return view;
    }

    class FilmAsyncTask extends AsyncTask<String, Void, List<FilmInformation>> {
        @Override
        protected List<FilmInformation> doInBackground(String... params) {
//            Toast.makeText(getActivity(), "doInBackground", Toast.LENGTH_SHORT).show();
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<FilmInformation> filmInformations) {
            super.onPostExecute(filmInformations);
            adapter = new FilmAdapter(getActivity(), R.layout.film_intro, filmInformations, listView);
            listView.setAdapter(adapter);
        }
    }

    private List<FilmInformation> getJsonData(String url) {
        list = new ArrayList<>();
        FilmInformation film;
        String jsonString = null;
        try {
            jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject cityObject = jsonObject.getJSONObject("result");
            JSONArray cityArray = cityObject.getJSONArray("data");
            for (int i = 0; i < cityArray.length(); i++) {
                jsonObject = cityArray.getJSONObject(i);
                //正在上映或即将上映
                String name = jsonObject.getString("name");
                JSONArray filmArray = jsonObject.getJSONArray("data");
                for (int j = 0; j < filmArray.length(); j++) {
                    film = new FilmInformation();
                    JSONObject filmObject = filmArray.getJSONObject(j);
                    film.setName(filmObject.getString("tvTitle"));
                    film.setPoster(filmObject.getString("iconaddress"));
                    JSONObject object = filmObject.getJSONObject("star");
                    JSONObject object1 = object.getJSONObject("data");
                    for (int s = 0, t = 1; s < object1.length(); s += 2) {
                        JSONObject data = object1.getJSONObject(String.valueOf(t++));
                        if(film.getPlayer() != null) {
                            film.setPlayer(film.getPlayer() + "/" + data.getString("name"));
                        }else{
                            film.setPlayer(data.getString("name"));
                        }
                    }
                    object = filmObject.getJSONObject("type");
                    object1 = object.getJSONObject("data");
                    for (int t = 0; t < object1.length(); t++) {
                        JSONObject data = object1.getJSONObject(String.valueOf(t+1));
                        if(film.getType() != null) {
                            film.setType(film.getType() + "/" + data.getString("name") + " ");
                        }else{
                            film.setType(data.getString("name"));
                        }
                    }
                    object = filmObject.getJSONObject("story");
                    object1 = object.getJSONObject("data");
                    film.setSynopsis(object1.getString("storyBrief"));
                    list.add(film);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

//    private FilmInformation getFilmData(String url){
//        FilmInformation film = new FilmInformation();
//        try {
//            String filmString = readStream(new URL(url).openStream());
//            JSONObject jsonObject = new JSONObject(filmString);
//            JSONObject filmObject = jsonObject.getJSONObject("result");
//            if(filmObject != null) {
//                film.setName(filmObject.getString("title"));
//                film.setType(filmObject.getString("genres"));
//                film.setPlayer(filmObject.getString("actors"));
//                film.setSynopsis(filmObject.getString("plot_simple"));
//                film.setCountry(filmObject.getString("country"));
//                film.setDuration(filmObject.getString("runtime"));
//                film.setPoster(filmObject.getString("poster"));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return film;
//    }

    private String readStream(InputStream is) {
        String result = "";
        try {
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
