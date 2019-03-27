package in.edu.kssem360.Model;

public class FeedModelClass {
    String caption,image;

    public FeedModelClass() {
    }

    public FeedModelClass(String caption, String image) {
        this.caption = caption;
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
