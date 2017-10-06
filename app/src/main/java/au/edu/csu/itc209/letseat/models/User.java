package au.edu.csu.itc209.letseat.models;

import com.google.firebase.database.Exclude;

public class User {
    private String ID;
    private String name;
    private String email;
    @Exclude
    private String password;
    private String photoUrl;

    public User (String ID, String name, String email, String pass, String photoUrl) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = pass;
        this.photoUrl = photoUrl;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() { return this.ID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
