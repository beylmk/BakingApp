package udacity.maddie.bakingapp;

/**
 * Created by rfl518 on 10/19/17.
 */

public class RecipeStep {

    private int id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailUrl;

    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {

        if (getId() == 0) {
            return description;
        }

        //remove number at beginning of description
        return description.substring(3);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isSelected() { return this.isSelected; }

    public void setSelected(boolean selected) { this.isSelected = selected; }
}
