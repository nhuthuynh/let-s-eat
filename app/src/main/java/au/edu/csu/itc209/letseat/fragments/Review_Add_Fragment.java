package au.edu.csu.itc209.letseat.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import au.edu.csu.itc209.letseat.Manifest;
import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


public class Review_Add_Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtDesc) EditText edtDesc;
    @BindView(R.id.btnOpenPopupMap) Button btnOpenPopupMap;
    @BindView(R.id.edtPhone) EditText edtPhone;
    @BindView(R.id.ddlTimeOpen) Spinner ddlTimeOpen;
    @BindView(R.id.ddlTimeClose) Spinner ddlTimeClose;
    @BindView(R.id.ddlPriceFrom) Spinner ddlPriceFrom;
    @BindView(R.id.ddlPriceTo) Spinner ddlPriceTo;

    private String [] prices;
    private String [] hours;
    final String TAG = "Review Add";

    private MaterialDialog.Builder dialogBuilder;
    private MaterialDialog dialog;

    private OnDialogMapListener openMapCallback;
    private FusedLocationProviderClient mFusedLocationClient;

    public Review_Add_Fragment() {

    }

    public interface OnDialogMapListener {
        void onOpenMap();
    }

    public static Review_Add_Fragment newInstance(String param1, String param2) {
        Review_Add_Fragment fragment = new Review_Add_Fragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private void initBuilder() {
        if(dialogBuilder==null){
            dialogBuilder = new MaterialDialog.Builder(getContext())
                    .title(R.string.dialog_add_address_map_title)
                    .customView(R.layout.dialog_map, false)
                    .positiveText(R.string.add);
        }
    }

    private void initDialog(){
        initBuilder();
        if(dialog==null) {
            dialog = dialogBuilder.build();
        }

        View v = dialog.getCustomView();
    }

    @OnClick(R.id.btnOpenPopupMap)
    public void openDialogMap() {
        openMapCallback.onOpenMap();
        dialog.show();

        GoogleMap googleMap;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        MapsInitializer.initialize(getActivity());
        MapView mMapView = getActivity().findViewById(R.id.mapView);
        mMapView.onCreate(new Bundle());
        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if(mFusedLocationClient != null){

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_review__add, container, false);
        ButterKnife.bind(this, v);
        ArrayAdapter<CharSequence> adapterPriceFrom = ArrayAdapter.createFromResource(getContext(), R.array.price, android.R.layout.simple_spinner_item);
        adapterPriceFrom.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ddlPriceFrom.setAdapter(adapterPriceFrom);
        prices = getResources().getStringArray(R.array.price);
        hours = getResources().getStringArray(R.array.time);
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(getContext(), R.array.time, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ddlTimeOpen.setAdapter(adapterTime);
        ddlTimeClose.setAdapter(adapterTime);
        initDialog();

        return v;
    }

    @OnItemSelected(R.id.ddlPriceFrom)
    public void onPriceFromItemSelected(View v) {
        Log.d(TAG, "onItemselected" + ddlPriceFrom.getSelectedItemPosition());
        initToSpinner(ddlPriceFrom, ddlPriceTo, prices);
    }

    public void initToSpinner(final Spinner ddlFromElement, Spinner ddlToElement, String [] source) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, source) {
            @Override
            public boolean isEnabled(int position) {
                return position >= (ddlFromElement != null ? ddlFromElement.getSelectedItemPosition() : 0);
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position <= (ddlFromElement != null ? ddlFromElement.getSelectedItemPosition() : 0)) {
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ddlToElement.setAdapter(adapter);
        ddlToElement.setSelection(ddlFromElement.getSelectedItemPosition());
    }

    @OnItemSelected(R.id.ddlTimeOpen)
    public void onTimeOpenItemFromSelected(View v) {
        Log.d(TAG, "onItemselected" + ddlTimeOpen.getSelectedItemPosition());
        initToSpinner(ddlTimeOpen, ddlTimeClose, hours);
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
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof OnDialogMapListener) {
            openMapCallback = (OnDialogMapListener) context;
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
