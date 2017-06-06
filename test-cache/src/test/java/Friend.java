import java.io.Serializable;
import java.util.Date;

/**
 * Created by qinshucai on 2016/4/7.
 */
public class Friend implements Serializable{
    String friendName;
    Date birthDay;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendName='" + friendName + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
