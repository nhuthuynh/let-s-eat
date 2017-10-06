package au.edu.csu.itc209.letseat.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.models.ReviewCategoryList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReviewCategoryListAdapter extends RecyclerView.Adapter<ReviewCategoryListAdapter.ReviewCategoryItemViewHolder> {
    private ArrayList<ReviewCategoryList> data;
    private Context context;

    public ReviewCategoryListAdapter(Context ctx, ArrayList<ReviewCategoryList> list) {
        this.context = ctx;
        this.data = list;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ReviewCategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_category_item, null);
        return new ReviewCategoryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewCategoryItemViewHolder holder, int position) {

        final ReviewCategoryList current = data.get(position);
        final String categoryName = current.getCategoryName();

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(context, current.getReviewItems());

        holder.categoryName.setText(categoryName);
        holder.reviewList.setHasFixedSize(true);
        holder.reviewList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.reviewList.setAdapter(reviewListAdapter);

        holder.btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReviewCategory Adapter", current.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

    public class ReviewCategoryItemViewHolder extends RecyclerView.ViewHolder {

        protected @BindView(R.id.categoryName) TextView categoryName;
        protected @BindView(R.id.reviewList) RecyclerView reviewList;
        protected @BindView(R.id.btnViewAll) Button btnViewAll;

        public ReviewCategoryItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}