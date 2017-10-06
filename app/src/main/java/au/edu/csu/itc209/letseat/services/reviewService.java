package au.edu.csu.itc209.letseat.services;

import java.util.ArrayList;

import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.models.Review;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class reviewService {
    public static ArrayList<Review> loadReviewById(String id) {
        //Request req = new Request.Builder().url(Constants.FIREBASE_APP_REST_URL + Constants.FIREBASE_DB_REVIEW_REF + ".json").build();

        return new ArrayList<Review>();
    }
}
