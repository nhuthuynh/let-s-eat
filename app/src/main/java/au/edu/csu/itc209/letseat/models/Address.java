package au.edu.csu.itc209.letseat.models;

public class Address {
    private int ID;
    private String address1;
    private String address2;
    private String suburb;
    private String state;
    private double longitude;
    private double latitude;

    public Address(String address1, String address2, String suburb, String state, double longitude, double latitude) {
        this.address1 = address1;
        this.address2 = address2;
        this.suburb = suburb;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
