package notify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swethakolalapudi on 7/1/16.
 */
public class PMNotification extends Notification{

    NotificationType type = NotificationType.PRIVATE_MESSAGE;

    PMNotification(String fromUser,String user, String pmText)
             {
                 this.columns.add(new Column("attributes", "type", this.type.toString()));
                 this.columns.add(new Column("attributes", "from_user", fromUser));
                 this.columns.add(new Column("attributes", "for_user", user));
                 this.columns.add(new Column("attributes", "text", pmText));
             }


    public String toString(){
        return "You have a private message from "+this.columns.get(1).value
                +"'"+this.columns.get(3).value +"'";
    }

}
