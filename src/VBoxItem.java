import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * Created by hp on 11-04-2017.
 */
public class VBoxItem extends VBox {
    public File file;
    public FilePathTreeItem item;
    String s;
    Label label;
    Image fileTile = new Image(
            getClass().getResourceAsStream("file-tile.png"));
    Image folderTile = new Image(
            getClass().getResourceAsStream("folder-tile.png"));
    VBoxItem(FilePathTreeItem item)
    {
        super(new Label(item.getAbsolutePath()));
        this.item=item;
    }
    VBoxItem(ImageView imageView,Label label)
    {

        super(imageView,label);
        this.label=label;
        this.s=s;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1) {
                    System.out.println(label.getText()+" Clicked single");
                    VBoxItem vBoxItem = (VBoxItem) event.getSource();
                    for (int i = 0; i < Controller.vBox.length; i++) {
                        Controller.vBox[i].setStyle("-fx-background-color: inherit;");
                        Controller.vBox[i].label.setTextFill(Color.web("#000000"));
                    }

                    vBoxItem.setStyle("-fx-background-color: #2679a5;");
                    label.setTextFill(Color.web("#ffffff"));
                    event.consume();
                }
                else
                {
                    System.out.println(s+" Clicked double");
                }
            }
        });
    }
}
