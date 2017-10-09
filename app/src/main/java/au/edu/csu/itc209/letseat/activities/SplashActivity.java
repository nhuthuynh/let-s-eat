package au.edu.csu.itc209.letseat.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import au.edu.csu.itc209.letseat.util.Util;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    static final String TAG = "Splash Activity";
    private ArrayList<Category> categoriesMockup = new ArrayList<>();
    private Location location;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivity);
        //initData();
        //isUserExisted();
        initGoogleAPIClient();
    }

    public void askLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_LOCATION);
        } else {
            if (Util.getLocation(SplashActivity.this).getLatitude() == 0) {
                getLocation();
            } else {
                isUserExisted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Constants.REQUEST_LOCATION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION) {
            getLocation();
        } else {
            isUserExisted();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void initGoogleAPIClient() {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    public void getLocation() {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location != null) {
            Log.d(TAG, location.toString());
            if(Util.saveLocation(SplashActivity.this, location) > 0) {
                Log.d(TAG, "Save location successfully!");
                isUserExisted();
            }
        } else {
            isUserExisted();
        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        askLocationPermission();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(googleApiClient!=null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(googleApiClient!=null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(googleApiClient!=null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }
}
