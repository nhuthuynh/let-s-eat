package au.edu.csu.itc209.letseat.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.IOException;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.fragments.Bottom_Navigation_Fragment;
import au.edu.csu.itc209.letseat.fragments.Home_Fragment;
import au.edu.csu.itc209.letseat.fragments.Profile_Fragment;
import au.edu.csu.itc209.letseat.fragments.Review_Add_Fragment;
import au.edu.csu.itc209.letseat.fragments.Review_Favorite_Fragment;
import au.edu.csu.itc209.letseat.fragments.Status_Bar_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements Home_Fragment.OnFragmentInteractionListener,
        Review_Add_Fragment.OnFragmentInteractionListener,
        Review_Favorite_Fragment.OnFragmentInteractionListener,
        Profile_Fragment.OnFragmentInteractionListener,
        Bottom_Navigation_Fragment.OnFragmentInteractionListener,
        Bottom_Navigation_Fragment.OnBottomNavigationSelectedListener,
        Status_Bar_Fragment.OnFragmentInteractionListener, Review_Add_Fragment.OnDialogMapListener {

    private final String TAG = "MainActivity";
    @BindView(R.id.progressBarWrapper) RelativeLayout progressBarWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        Log.d(TAG, "" + progressBarWrapper.getVisibility());
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void showLoading() {
        if (progressBarWrapper != null && progressBarWrapper.getVisibility() == progressBarWrapper.INVISIBLE) {
            Log.d(TAG, "showLoading");
            progressBarWrapper.setVisibility(progressBarWrapper.VISIBLE);
        }
    }

    protected void hideLoading() {
        if (progressBarWrapper != null && progressBarWrapper.getVisibility() == progressBarWrapper.VISIBLE) {
            Log.d(TAG, "hideLoading");
            progressBarWrapper.setVisibility(progressBarWrapper.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backstack = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "currentBackStack:" +
                (backstack > 0 ? getSupportFragmentManager().getBackStackEntryAt(backstack-1).getName() + ":" + getSupportFragmentManager().getBackStackEntryAt(backstack-1).getId() : 0));
        if(backstack > 0) {

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction " + uri);
    }

    @Override
    public void onNavigationItemSelected(MenuItem menuItem) {
        if(menuItem != null ) {
            Fragment fragment;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.action_add_review:
                    Log.d(TAG, "add review clicked!!");
                    fragment = new Review_Add_Fragment();
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_BACKSTACK_REVIEW_ADD);
                    addStatusBarFragment(getString(R.string.review_add_title_screen));
                    break;
                case R.id.action_favorite:
                    Log.d(TAG, "favorite clicked!!");
                    fragment = new Review_Favorite_Fragment();
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_BACKSTACK_REVIEW_FAVORITE);
                    addStatusBarFragment(getString(R.string.review_favorite_title_screen));
                    break;
                case R.id.action_profile:
                    Log.d(TAG, "profile clicked!!");
                    fragment = new Profile_Fragment();
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_BACKSTACK_PROFILE);
                    addStatusBarFragment(getString(R.string.profile_title_screen));
                    break;
                default:
                    Log.d(TAG, "home!!");
                    fragment = new Home_Fragment();
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_BACKSTACK_HOME);
                    removeStatusBarFragment();
                    break;
            }

            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    public void addStatusBarFragment(final String title) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.status_bar_container);
        if(fragment == null) {
            Status_Bar_Fragment statusBarFragment = new Status_Bar_Fragment().newInstance(title, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.status_bar_container, statusBarFragment).commit();
        } else {
            ((Status_Bar_Fragment) fragment).changeTitle(title);
        }
    }

    public void removeStatusBarFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.status_bar_container);
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onOpenMap() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.REQUEST_LOCATION);
        }

    }
}
