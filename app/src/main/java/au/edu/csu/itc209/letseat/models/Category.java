package au.edu.csu.itc209.letseat.models;

public class Category {
    private String id;
    private String name;
    private String iconUrl;
    private String imageUrl;

    public Category() {}

    public Category(String id, String name, String iconUrl, String imageUrl) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
