package au.edu.csu.itc209.letseat.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.models.Review;
import au.edu.csu.itc209.letseat.models.ReviewCategoryList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewItemViewHolder> {

    private Context context;
    private ArrayList<Review> data;
    private final String TAG = "ReviewListAdapter";

    public ReviewListAdapter(Context ctx, ArrayList<Review> list) {
        this.context = ctx;
        this.data = list;
    }

    @Override
    public ReviewListAdapter.ReviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, null);
        return new ReviewItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewItemViewHolder holder, int position) {
        Review review = data.get(position);
        Log.d(TAG, "review:" + review.toString());
        Glide.with(holder.itemView.getContext()).load("https://greenweddingshoes.com/wp-content/uploads/2015/01/bridalbreakfast-styled-01.jpg").into(holder.reviewImage);
        holder.reviewRestaurantName.setText(review.getName());
        holder.reviewRestaurantDesc.setText(review.getDesc());
        holder.reviewRating.setNumStars(review.getRating());
    }

    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        protected @BindView(R.id.reviewImage) ImageView reviewImage;
        protected @BindView(R.id.reviewRestaurantName) TextView reviewRestaurantName;
        protected @BindView(R.id.reviewRestaurantDesc) TextView reviewRestaurantDesc;
        protected @BindView(R.id.reviewRating) RatingBar reviewRating;
        protected @BindView(R.id.reviewNumberOfRating) TextView reviewNumberOfRating;

        public ReviewItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
