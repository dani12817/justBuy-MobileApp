package danielleal.justbuy.classes;

/**
 * Created by Dani on 14/02/2018.
 */

public class ZIPItem {
    int idZIP;
    String ZIPNumber;

    public ZIPItem(int idZIP, String ZIPNumber) {
        this.idZIP = idZIP;
        this.ZIPNumber = ZIPNumber;
    }

    public int getIdZIP() {
        return idZIP;
    }

    public void setIdZIP(int idZIP) {
        this.idZIP = idZIP;
    }

    public String getZIPNumber() {
        return ZIPNumber;
    }

    public void setZIPNumber(String ZIPNumber) {
        this.ZIPNumber = ZIPNumber;
    }
}
