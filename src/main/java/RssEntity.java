public class RssEntity {
    private String author;
    private String category;
    private String id;

    public RssEntity(String author, String category, String id) {
        this.author = author;
        this.category = category;
        this.id = id;
    }

    @Override
    public String toString() {
        return "RssEntity{" +
                "author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
