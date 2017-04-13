import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
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
    public static TreeView treeView=null;
    public static TextField tCurrDir=null;
    public static FlowPane flowPane=null;
    public static TableView tableView=null;
    VBoxItem(ImageView imageView,Label label,FilePathTreeItem item)
    {
        super(imageView,label);
        this.label=label;
        this.item=item;
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
                    treeView.getSelectionModel().select(item);
                    treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

                    Controller.vBox=new VBoxItem[item.getChildren().size()];
                    item.getChildren();
                    int i=0;
                    if(!((FilePathTreeItem)treeView.getSelectionModel().getSelectedItem()).getAbsolutePath().equals(Controller.hostName)) {

                        for (FilePathTreeItem fileItem : item.childrenArray) {

                            Controller.vBox[i++]=new VBoxItem(new ImageView(Controller.getIconBig(fileItem)),new Label(fileItem.getFile().getName()),fileItem);

                        }
                    }
                    else
                    {
                        for (FilePathTreeItem fileItem : Controller.drives)
                        {
                            try {
                                Controller.vBox[i++]=new VBoxItem(new ImageView(Controller.getIconBig(fileItem)),new Label(fileItem.getAbsolutePath()),fileItem);
                            } catch (Exception e) {
                                i--;
                                Controller.vBox[i++]=new VBoxItem(new ImageView(Controller.getIcon(fileItem)),new Label(fileItem.getAbsolutePath()),fileItem);
                                e.printStackTrace();
                            }
                        }
                    }
                    flowPane.getChildren().clear();
                    flowPane.getChildren().addAll(Controller.vBox);
                    tCurrDir.setText(item.getAbsolutePath());
                    //Controller.backlist.push(item);
                    System.out.println("Stack Pushed -> "+item.getAbsolutePath());


                    //Table
                    treeView.getSelectionModel().select(item);
                    treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

                    ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
                    item.getChildren();
                    if(!((FilePathTreeItem)treeView.getSelectionModel().getSelectedItem()).getAbsolutePath().equals(Controller.hostName)) {

                        for (FilePathTreeItem fileItem : item.childrenArray) {
                            //System.out.println("Loading files "+fileItem.getFile().toString());
                            FileDetails fileDetails = new FileDetails(new ImageView(Controller.getIcon(fileItem)), fileItem.getFile().getName(),
                                    String.valueOf(fileItem.getFile().length()),
                                    String.valueOf(Controller.sdf.format(fileItem.getFile().lastModified())), fileItem);
                            //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                            imgList.add(fileDetails);
                        }
                    }
                    else
                    {
                        for (FilePathTreeItem fileItem : Controller.drives)
                        {
                            //System.out.println("Loading files "+fileItem.getFile().toString());
                            FileDetails fileDetails = new FileDetails(new ImageView(Controller.getIcon(fileItem)),fileItem.getFile().toString(),
                                    String.valueOf(fileItem.getFile().length()),
                                    String.valueOf(Controller.sdf.format(fileItem.getFile().lastModified())),fileItem);
                            //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                            imgList.add(fileDetails);
                        }
                    }
                    tableView.getItems().clear();
                    tableView.setItems(imgList);
                    tCurrDir.setText(item.getAbsolutePath());
                    Controller.backlist.push(item);
                    System.out.println("Stack Pushed -> "+item.getAbsolutePath());

                }
            }
        });
    }
}
