package informationsystem.com.campusservice.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import informationsystem.com.campusservice.model.FilmInfomation;
import informationsystem.com.campusservice.model.PersonalInfomation;

/**
 * Created by Chen on 2017/3/27.
 */
public class CampusServiceDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "campus_service.db";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static CampusServiceDB campusServiceDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CampusServiceDB(Context context) {
        CampusServiceOpenHelper dbHelper = new CampusServiceOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CampusServiceDB的实例
     */
    public synchronized static CampusServiceDB getInstance(Context context) {
        if (campusServiceDB == null) {
            campusServiceDB = new CampusServiceDB(context);
        }
        return campusServiceDB;
    }

    /**
     * 将用户注册的账号保存到数据库中
     */
    public void saveLoginInfo(PersonalInfomation personalInfomation) {
        if (personalInfomation != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userName", personalInfomation.getUserName());
            contentValues.put("password", personalInfomation.getPassword());
            db.insert("PersonalInfo", null, contentValues);
        }
    }

    /**
     * 保存用户个人信息
     */
    public void savePersonalInfo(PersonalInfomation personalInfomation){
        if (personalInfomation != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("sex", personalInfomation.getSex());
            contentValues.put("introduction", personalInfomation.getIntroduction());
            contentValues.put("nickname", personalInfomation.getNickname());
            db.update("PersonalInfo", contentValues, "userName=?",new String[]{personalInfomation.getUserName()});
        }
    }

    /**
     * 查询是否存在此用户
     */
    public boolean queryUser(String name) {
        Cursor cursor = db.query("PersonalInfo", null, "userName=?", new String[]{name}, null, null, null);
        boolean result = cursor.moveToNext();
        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    /**
     * 查询用户名所对应的个人信息
     */
//    public String user_password(String username){
//        Cursor cursor = db.query("PersonalInfo", null, "userName=?", new String[]{username}, null, null, null);
//        if(cursor.moveToNext()){
//            cursor.getString(cursor.getColumnIndex("password"));
//        }
//        if(cursor != null){
//            cursor.close();
//        }
//        return password;
//    }

    /**
     * 从数据库读取登陆账号所对应个人信息
     */
    public PersonalInfomation queryPersonalInfo(String userName) {
//        List<PersonalInfomation> list = new ArrayList<>();
        PersonalInfomation personalInfo = new PersonalInfomation();
        Cursor cursor = db.query("PersonalInfo", null, "userName=?", new String[]{userName}, null, null, null);
        if (cursor.moveToFirst()) {
//            do {
            personalInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            personalInfo.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            personalInfo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            personalInfo.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            personalInfo.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
            personalInfo.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
//                list.add(personalInfo);
//            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return personalInfo;
    }

    /**
     * 将FilmInfo实例存储到数据库
     */
    public void saveFilmInfo(FilmInfomation filmInfomation) {
        if (filmInfomation != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("film_name", filmInfomation.getName());
            contentValues.put("film_type", filmInfomation.getType());
            contentValues.put("film_country", filmInfomation.getCountry());
            contentValues.put("film_duration", filmInfomation.getDuration());
            contentValues.put("film_player", filmInfomation.getPlayer());
            contentValues.put("film_synopsis", filmInfomation.getSynopsis());
            db.insert("FilmInfo", null, contentValues);
        }
    }

    /**
     * 从数据库读取电影信息
     */
    public List<FilmInfomation> loadFilmInfo() {
        List<FilmInfomation> list = new ArrayList<>();
        Cursor cursor = db.query("FilmInfo", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                FilmInfomation filmInfo = new FilmInfomation();
                filmInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                filmInfo.setName(cursor.getString(cursor.getColumnIndex("film_name")));
                filmInfo.setType(cursor.getString(cursor.getColumnIndex("film_type")));
                filmInfo.setCountry(cursor.getString(cursor.getColumnIndex("film_country")));
                filmInfo.setDuration(cursor.getInt(cursor.getColumnIndex("film_duration")));
                filmInfo.setPlayer(cursor.getString(cursor.getColumnIndex("film_player")));
                filmInfo.setSynopsis(cursor.getString(cursor.getColumnIndex("film_synopsis")));
                list.add(filmInfo);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


}
