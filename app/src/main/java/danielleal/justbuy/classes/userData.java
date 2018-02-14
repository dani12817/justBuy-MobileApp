package danielleal.justbuy.classes;

/**
 * Created by dleal on 21/11/2017.
 */

public class userData {
    private String nick = "";
    private String name = "Invitado";
    private byte[] avatar = null;
    private String phone = "";
    private String email = "";
    private boolean logIn = false;

    private static final userData holderUD = new userData();

    public static userData getInstance() {
        return holderUD;
    }

    public void logInSet(String newNick,String newName,byte[] newAvatar,String newPhone,String newEmail){
        setNick(newNick);
        setName(newName);
        setAvatar(newAvatar);
        setPhone(newPhone);
        setEmail(newEmail);
    }

    public boolean getLogIn() {return logIn;}

    public void setLogIn(boolean logIn) {this.logIn = logIn;}

    public void setNick(String newNick){nick = newNick;}

    public String getNick(){return nick;}

    public void setName(String newName){name = newName;}

    public String getName(){return name;}

    public byte[] getAvatar() {return avatar;}

    public void setAvatar(byte[] avatar) {this.avatar = avatar;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
