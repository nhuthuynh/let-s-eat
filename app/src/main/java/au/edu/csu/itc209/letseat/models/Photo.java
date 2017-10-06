package au.edu.csu.itc209.letseat.models;

import android.net.Uri;

import java.sql.Timestamp;

/**
 * Created by NhutHuynh on 9/4/17.
 */

public class Photo {
    private int id;
    private Uri imageUrl;
    private Timestamp dateCreated;

    public Photo(Uri imageUrl, Timestamp dateCreated) {
        this.imageUrl = imageUrl;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
