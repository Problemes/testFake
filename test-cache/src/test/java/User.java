import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qinshucai on 2016/4/7.
 */
public class User implements Serializable {
    String userName;
    String password;
    int height;
    Date time;
    List<Friend> list = new ArrayList<Friend>();
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void addFriend(Friend friend)
    {
        this.list.add(friend);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", height=" + height +
                ", time=" + time +
                ", list=" + list +
                '}';
    }
}
