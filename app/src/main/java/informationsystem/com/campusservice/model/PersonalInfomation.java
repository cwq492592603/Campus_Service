package informationsystem.com.campusservice.model;

/**
 * Created by Chen on 2017/3/27.
 */
public class PersonalInfomation {
    private int _id;
    private String userName;
    private String password;
    private String introduction;
    private String sex;
    private String nickname;

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
