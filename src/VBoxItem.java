import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by hp on 11-04-2017.
 */
public class VBoxItem extends VBox {
    public FileTreeItem item;
    String s;
    Label label;
    int pos;
    public static TreeView treeView=null;
    public static TextField tCurrDir=null;
    public static FlowPane flowPane=null;
    public static TableView tableView=null;
    public static int lastClicked=-1;
    VBoxItem(int pos, ImageView imageView, Label label, FileTreeItem item)
    {
        super(imageView,label);
        setLayout();
        this.pos=pos;
        this.label=label;
        this.item=item;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                VBoxItem vBoxItem = (VBoxItem) event.getSource();
                if (event.getClickCount()==1 && MenuSetting.getInstance().view.equals("tiles")) {
                    System.out.println(label.getText()+" Clicked single");


                    if(lastClicked!=-1) {
                        Controller.vBox[lastClicked].setStyle("-fx-background-color: inherit;");
                        Controller.vBox[lastClicked].label.setTextFill(Color.web("#000000"));
                    }
                    lastClicked=pos;
                    vBoxItem.setStyle("-fx-background-color: #2679a5;");
                    label.setTextFill(Color.web("#ffffff"));

                }
                else if(event.getClickCount()!=1 && MenuSetting.getInstance().view.equals("tiles"))
                {
                    System.out.println("Clicked double");
                    if(!item.isDirectory())
                    {
                        /*try {
                            Desktop.getDesktop().open(vBoxItem.item.getFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                try {
                                    Desktop.getDesktop().open(vBoxItem.item.getFile());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }).start();
                        }
                        return;
                    }
                    treeView.getSelectionModel().select(item);
                    treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
                    Controller.vBox=new VBoxItem[item.getChildren().size()];
                    item.getChildren();
                    int i=0;
                    ObservableList<FileTreeItem> list = ChildArrayHelper.getChildren(item);
                    Label label;
                    for (FileTreeItem fileItem : item.childrenArray) {

                        if(list==Controller.drives)label= new Label(fileItem.getAbsolutePath());
                        else label= new Label(fileItem.getFile().getName());
                        label.setPrefWidth(50);label.setMaxWidth(50);label.setMinWidth(50);label.setAlignment(Pos.CENTER);
                        try {
                            Controller.vBox[i] = new VBoxItem(i, new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.BIG_ICON)), label, fileItem);
                            i++;
                        }
                        catch (Exception e)
                        {
                            Controller.vBox[i] = new VBoxItem(i, new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.SMALL_ICON)), label, fileItem);
                            i++;
                        }
                    }

                    lastClicked=-1;
                    flowPane.getChildren().clear();
                    flowPane.getChildren().addAll(Controller.vBox);
                    tCurrDir.setText(item.getAbsolutePath());
                    Controller.backlist.push(item);
                    MenuSetting.getInstance().currItem=item;
                    System.out.println("Stack Pushed -> "+item.getAbsolutePath());

                }
                event.consume();
            }
        });
    }

    private void setLayout() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.setPadding(new Insets(20));
        this.setWidth(100);
    }
}
