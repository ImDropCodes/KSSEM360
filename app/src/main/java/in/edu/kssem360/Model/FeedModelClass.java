package in.edu.kssem360.Model;

public class FeedModelClass {
    String caption, image,uid;

    public FeedModelClass() {
    }

    public FeedModelClass(String caption, String image, String uid) {
        this.caption = caption;
        this.image = image;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}