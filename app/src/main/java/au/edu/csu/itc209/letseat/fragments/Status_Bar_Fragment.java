package au.edu.csu.itc209.letseat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Status_Bar_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Status_Bar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Status_Bar_Fragment extends Fragment {

    @BindView(R.id.appToolbar) Toolbar appToolbar;
    @BindView(R.id.btnBack) ImageButton btnBack;
    @BindView(R.id.title) TextView title;

    private OnFragmentInteractionListener mListener;

    public Status_Bar_Fragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static Status_Bar_Fragment newInstance(String title) {
        Status_Bar_Fragment fragment = new Status_Bar_Fragment();
        Bundle args = new Bundle();
        args.putString(Constants.TITLE_KEY, title);
        fragment.setArguments(args);
        return fragment;
    }

    public static Status_Bar_Fragment newInstance(String title, boolean isHideBackArrow) {
        Status_Bar_Fragment fragment = new Status_Bar_Fragment();
        Bundle args = new Bundle();
        args.putString(Constants.TITLE_KEY, title);
        args.putBoolean(Constants.IS_HIDE_BACK_ARROW_KEY, isHideBackArrow);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_bar, container, false);
        ButterKnife.bind(Status_Bar_Fragment.this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(appToolbar);
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setHomeButtonEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowCustomEnabled(true);

        readBundle(getArguments());

        return view;
    }

    private void readBundle(Bundle bundle) {
        if(bundle != null) {
            final String titleText = bundle.getString(Constants.TITLE_KEY);
            final boolean isHideBackBtn = bundle.getBoolean(Constants.IS_HIDE_BACK_ARROW_KEY);
            title.setText(titleText);
            if(isHideBackBtn) {
                btnBack.setVisibility(btnBack.GONE);
            } else {
                btnBack.setVisibility(btnBack.VISIBLE);
            }
        }
    }

    public void changeTitle(String title) {
        this.title.setText(title);
    }


    @OnClick(R.id.btnBack)
    public void goPreviousScreen() {
        getActivity().onBackPressed();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
