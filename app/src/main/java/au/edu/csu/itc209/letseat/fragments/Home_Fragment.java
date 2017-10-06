package au.edu.csu.itc209.letseat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Timestamp;
import java.util.ArrayList;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.adapters.ReviewCategoryListAdapter;
import au.edu.csu.itc209.letseat.models.Address;
import au.edu.csu.itc209.letseat.models.Category;
import au.edu.csu.itc209.letseat.models.Review;
import au.edu.csu.itc209.letseat.models.ReviewCategoryList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Home_Fragment extends Fragment {
    @BindView(R.id.reviewCategoryList) RecyclerView reviewCategoryList;
    private ArrayList<ReviewCategoryList> reviewCategoryListData;

    private final String TAG = "homefragment";

    private OnFragmentInteractionListener mListener;

    public Home_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Fragment newInstance(String param1, String param2) {
        Home_Fragment fragment = new Home_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    private ArrayList<ReviewCategoryList> initData() {
        ArrayList<ReviewCategoryList> sample = new ArrayList<>();
        for(int j = 0; j < 5; j++) {
            ReviewCategoryList cateList = new ReviewCategoryList();
            cateList.setCategoryName("Category " + j);
            ArrayList<Review> reviewList = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                reviewList.add(new Review("japaness restaurant " + i, new ArrayList<Integer>(), "this is my favorite sushi restaurant. fresh shasimi ever!", "", 5, new ArrayList<Address>(), new ArrayList<Category>(), new Timestamp(System.currentTimeMillis())));
            }

            cateList.setReviewItems(reviewList);
            sample.add(cateList);
        }
        Log.d(TAG, "sampleData:" + sample.size());
        return sample;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        reviewCategoryListData = initData();
        reviewCategoryList.setHasFixedSize(true);
        ReviewCategoryListAdapter adapter = new ReviewCategoryListAdapter(getActivity(), reviewCategoryListData);
        reviewCategoryList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reviewCategoryList.setAdapter(adapter);

        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
