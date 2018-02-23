package danielleal.justbuy.classes.listViewItems;

import java.util.Date;

/**
 * Created by Dani on 21/02/2018.
 */

public class ratingShopItemList {
    String text;
    float rating;
    String user;
    Date dateRating;

    public ratingShopItemList(String text, float rating, String user, Date dateRating) {
        this.text = text;
        this.rating = rating;
        this.user = user;
        this.dateRating = dateRating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDateRating() {
        return dateRating;
    }

    public void setDateRating(Date dateRating) {
        this.dateRating = dateRating;
    }
}
