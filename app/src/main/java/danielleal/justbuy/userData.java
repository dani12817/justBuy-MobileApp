package danielleal.justbuy;

/**
 * Created by dleal on 21/11/2017.
 */

public class userData {
    private String nick = "";
    private String name = "";

    private static final userData holderUD = new userData();

    public static userData getInstance() {
        return holderUD;
    }

    public void logInSet(String newNick, String newName){
        setNick(newNick);
        setName(newName);
    }

    public void setNick(String newNick){
        nick = newNick;
    }

    public String getNick(){
        return nick;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }
}
