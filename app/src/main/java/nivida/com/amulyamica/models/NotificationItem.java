package nivida.com.amulyamica.models;

/**
 * Created by Nivida new on 24-May-17.
 */

public class NotificationItem {
    String message="";
    String time="";

    public NotificationItem() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
