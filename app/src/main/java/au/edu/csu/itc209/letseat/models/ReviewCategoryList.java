package au.edu.csu.itc209.letseat.models;

import java.util.ArrayList;

public class ReviewCategoryList {
    private String id;

    private String categoryName;
    private ArrayList<Review> reviewItems;

    public ReviewCategoryList(){}

    public ReviewCategoryList(String name, ArrayList<Review> list) {
        this.categoryName = name;
        this.reviewItems = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Review> getReviewItems() {
        return reviewItems;
    }

    public void setReviewItems(ArrayList<Review> reviewItems) {
        this.reviewItems = reviewItems;
    }
}
