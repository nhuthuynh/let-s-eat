package au.edu.csu.itc209.letseat.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import au.edu.csu.itc209.letseat.Manifest;
import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.models.Category;

public class SplashActivity extends AppCompatActivity {
    static final String TAG = "Splash Activity";
    private ArrayList<Category> categoriesMockup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivity);
        //initData();
        isUserExisted();

    }

    public void isUserExisted() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            Log.d(TAG, user.toString());
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            Log.d(TAG, "go to log in");
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

    public void initData() {
        if (categoriesMockup != null) {
            categoriesMockup.add(new Category("", "Desserts", "", ""));
            categoriesMockup.add(new Category("", "Asian", "", ""));
            categoriesMockup.add(new Category("", "Vietnamese", "", ""));
            categoriesMockup.add(new Category("", "Bakery", "", ""));
            categoriesMockup.add(new Category("", "Italian", "", " "));
            categoriesMockup.add(new Category("", "Pizza", "", " "));
            categoriesMockup.add(new Category("", "Indonesian", "", " "));
            categoriesMockup.add(new Category("", "Asian Fusion", "", " "));
            categoriesMockup.add(new Category("", "Cafe", " ", " "));
            categoriesMockup.add(new Category("", "American", "", " "));
            Log.d(TAG, "mockupdata:" + categoriesMockup.size());
            DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_CATEGORY_REF);
            categoryRef.push().setValue(categoriesMockup.get(0));
            for (int i =0; i < categoriesMockup.size(); i++) {
                DatabaseReference newRef = categoryRef.push();
                categoriesMockup.get(i).setId(newRef.getKey());
                newRef.setValue(categoriesMockup.get(i));
            }
        }
    }

}
