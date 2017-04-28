import javafx.collections.ObservableList;

/**
 * Created by hp on 13-04-2017.
 */
public class ChildArrayHelper {
    public static ObservableList<FileTreeItem> getChildren(FileTreeItem item)
    {
        if(item.getAbsolutePath().equals(Controller.hostName))return Controller.drives;
        return item.childrenArray;
    }
}
