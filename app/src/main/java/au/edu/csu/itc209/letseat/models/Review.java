package au.edu.csu.itc209.letseat.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Review {
    private String id;
    private String name;
    private ArrayList<Integer> phones = new ArrayList<>();
    private String website;
    private int rating;
    private String desc;
    private ArrayList<Address> addresses = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Photo> photos = new ArrayList<>();
    private Timestamp dateCreated;

    public Review(String name, ArrayList<Integer> phones, String desc, String website, int rating, ArrayList<Address> addresses, ArrayList<Category> categories, Timestamp date) {
        this.name = name;
        this.phones = phones;
        this.website = website;
        this.desc = desc;
        this.rating = rating;
        this.addresses = addresses;
        this.categories = categories;
        this.dateCreated = date;
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Integer> phones) {
        this.phones = phones;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp date) {
        this.dateCreated = date;
    }
}
