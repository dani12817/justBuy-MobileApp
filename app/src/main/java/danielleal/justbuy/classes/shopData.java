package danielleal.justbuy.classes;

import android.os.Parcel;
import android.os.Parcelable;

import danielleal.justbuy.classes.listViewItems.searchShopItemList;

/**
 * Created by Dani on 18/02/2018.
 */

public class shopData implements Parcelable {
    int idShop;
    String name,description,phone,ZIP,addressLine1,addressLine2;
    byte[] logo;
    double latitude,longitude;

    String categories; int ratingNum;
    float ratingAVG;

    public int describeContents() {
        return 0;
    }

    public shopData(searchShopItemList shopSelected){
        this.idShop = shopSelected.getIdShop();
        this.name = shopSelected.getName();
        this.description = shopSelected.getDescription();
        this.phone = shopSelected.getPhone();
        this.ZIP = shopSelected.getZIP();
        this.addressLine1 = shopSelected.getAddressLine1();
        this.addressLine2 = shopSelected.getAddressLine2();
        //this.logo = shopSelected.getLogo();
        this.latitude = shopSelected.getLatitude();
        this.longitude = shopSelected.getLongitude();
        this.categories = shopSelected.getCategories();
        this.ratingNum = shopSelected.getRatingNum();
        this.ratingAVG = shopSelected.getRatingAverage();
    }

    public int getIdShop() {
        return idShop;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getZIP() {
        return ZIP;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getCategories() {
        return categories;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public float getRatingAVG() {
        return ratingAVG;
    }

    public shopData(Parcel in){
        this.idShop = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.phone = in.readString();
        this.ZIP = in.readString();
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
        //in.readByteArray(this.logo);
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.categories = in.readString();
        this.ratingNum = in.readInt();
        this.ratingAVG = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idShop);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(phone);
        dest.writeString(ZIP);
        dest.writeString(addressLine1);
        dest.writeString(addressLine2);
        //dest.writeByteArray(logo);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(categories);
        dest.writeInt(ratingNum);
        dest.writeFloat(ratingAVG);
    }

    public static final Parcelable.Creator<shopData> CREATOR = new Parcelable.Creator<shopData>(){
        public shopData createFromParcel(Parcel in) {
            return new shopData(in);
        }

        public shopData[] newArray(int size) {
            return new shopData[size];
        }
    };
}
