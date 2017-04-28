import javafx.scene.image.ImageView;

/**
 * Created by hp on 10-04-2017.
 */
public class FileDetails {
    private ImageView icon;
    private String fileName;
    private String size;
    private String Date;
    private FileTreeItem item;

    public FileDetails(ImageView icon, String fileName, String size, String date, FileTreeItem item) {
        this.icon = icon;
        this.fileName = fileName;
        this.size = size;
        Date = date;
        this.item = item;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public FileTreeItem getItem() {
        return item;
    }

    public void setItem(FileTreeItem item) {
        this.item = item;
    }
}
