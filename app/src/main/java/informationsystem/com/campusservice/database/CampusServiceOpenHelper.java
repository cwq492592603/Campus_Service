package informationsystem.com.campusservice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Chen on 2017/3/27.
 */
public class CampusServiceOpenHelper extends SQLiteOpenHelper {

    /**
     * PersonalInformation建表语句
     */
    public static final String CREATE_PERSONALINFO = "create table PersonalInfo ("
            + "_id integer primary key autoincrement, "
            + "userName text not null, "
            + "password text not null, "
            + "nickname text, "
            + "sex text, "
            + "introduction text)";

    /**
     * FilmInfo建表语句
     */
    public static final String CREATE_FILMINFO = "create table FilmInfo ("
            + "_id integer primary key autoincrement, "
            + "film_name text not null, "
            + "film_type text not null, "
            + "film_country text not null, "
            + "film_duration integer not null, "
            + "film_player text not null, "
            + "film_synopsis text not null)";

    public CampusServiceOpenHelper(Context context, String name, CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSONALINFO);
        db.execSQL(CREATE_FILMINFO);
        Log.i("DB","数据库建立成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
