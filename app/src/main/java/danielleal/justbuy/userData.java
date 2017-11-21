package danielleal.justbuy;

/**
 * Created by dleal on 21/11/2017.
 */

public class userData {
    private static String nick = "";
    private static String name = "";

    protected userData(){

    }

    public void logInSet(String newNick, String newName){
        setNick(newNick);
        setName(newName);
    }

    public static void setNick(String newNick){
        nick = newNick;
    }

    public static String getNick(){
        return nick;
    }

    public static void setName(String newName){
        name = newName;
    }

    public static String getName(){
        return name;
    }
}
