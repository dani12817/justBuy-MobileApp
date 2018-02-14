package danielleal.justbuy.classes;

/**
 * Created by Dani on 14/02/2018.
 */

public class addressItem {
    int idAddress;
    int idCodPostal;
    String addressLine1;
    String addressLine2;
    String name;

    public addressItem(int idAddress, int idCodPostal, String addressLine1, String addressLine2, String name) {
        this.idAddress = idAddress;
        this.idCodPostal = idCodPostal;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.name = name;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(int idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
