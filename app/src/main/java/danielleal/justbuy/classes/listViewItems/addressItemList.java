package danielleal.justbuy.classes.listViewItems;

/**
 * Created by Dani on 29/01/2018.
 */

public class addressItemList {
    private String addressName;
    private String fullAddress;

    public addressItemList(String addressName, String fullAddress){
        this.addressName = addressName;
        this.fullAddress = fullAddress;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
