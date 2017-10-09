package au.edu.csu.itc209.letseat.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.helpers.BottomNavigationViewHelper;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Bottom_Navigation_Fragment extends Fragment {

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    private OnFragmentInteractionListener mListener;
    OnBottomNavigationSelectedListener mCallback;

    private final String TAG = "Bottom Navigation";

    public Bottom_Navigation_Fragment() {
    }

    public interface OnBottomNavigationSelectedListener {
        void onNavigationItemSelected(MenuItem menuItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate BottomNavigationFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom__navigation, container, false);
        ButterKnife.bind(this, v);
        Log.d(TAG, "onCreateView BottomNavigationFragment");

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mCallback.onNavigationItemSelected(item);
                return true; // if this return false the selection tint wont work
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof OnBottomNavigationSelectedListener) {
            mCallback = (OnBottomNavigationSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBottomNavigationSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
