package id.my.avmmartin.mynewsapp.data.model;

public class News {
    private int id;
    private String title;
    private String imageUrl;
    private String imageFilepath;
    private String description;
    private String content;
    private String url;

    private boolean isSaved;

    public boolean isOffline() {
        return isSaved;
    }

    public void setOffline(String filepath) {
        setImageFilepath(filepath);
        isSaved = true;
    }

    public void unsetOffline() {
        isSaved = false;
    }

    // copy

    public News copy() {
        return new News(
            getId(),
            getTitle(),
            getImageUrl(),
            getDescription(),
            getContent(),
            getUrl()
        );
    }

    // constructor

    public News(int id, String title, String imageUrl, String description, String content, String url) {
        setId(id);
        setTitle(title);
        setImageUrl(imageUrl);
        setDescription(description);
        setContent(content);
        setUrl(url);
        isSaved = false;
    }

    // getter

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageFilepath() {
        return imageFilepath;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    // setter

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void setImageFilepath(String imageFilepath) {
        this.imageFilepath = imageFilepath;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setUrl(String url) {
        this.url = url;
    }
}
