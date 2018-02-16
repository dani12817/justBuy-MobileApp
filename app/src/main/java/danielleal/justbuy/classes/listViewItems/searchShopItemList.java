package danielleal.justbuy.classes.listViewItems;

/**
 * Created by Dani on 16/02/2018.
 */

public class searchShopItemList {
    int idShop;
    String name,description,phone,ZIP,addressLine1,addressLine2;
    byte[] logo;
    double latitude,longitude;

    public searchShopItemList(int idShop, String name, byte[] logo, String description, String phone, double latitude, double longitude, String addressLine1, String addressLine2, String ZIP) {
        this.idShop = idShop;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.ZIP = ZIP;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.logo = logo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getIdShop() {return idShop;}

    public void setIdShop(int idShop) {this.idShop = idShop;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getZIP() {return ZIP;}

    public void setZIP(String ZIP) {this.ZIP = ZIP;}

    public String getAddressLine1() {return addressLine1;}

    public void setAddressLine1(String addressLine1) {this.addressLine1 = addressLine1;}

    public String getAddressLine2() {return addressLine2;}

    public void setAddressLine2(String addressLine2) {this.addressLine2 = addressLine2;}

    public byte[] getLogo() {return logo;}

    public void setLogo(byte[] logo) {this.logo = logo;}

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}
}