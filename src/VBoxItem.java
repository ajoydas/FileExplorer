import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    int pos;
    public static TreeView treeView=null;
    public static TextField tCurrDir=null;
    public static FlowPane flowPane=null;
    public static TableView tableView=null;
    public static int lastClicked=-1;
    VBoxItem(int pos,ImageView imageView,Label label,FilePathTreeItem item)
    {
        super(imageView,label);
        setLayout();
        this.pos=pos;
        this.label=label;
        this.item=item;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1 && MenuSetting.getInstance().view.equals("tiles")) {
                    System.out.println(label.getText()+" Clicked single");
                    VBoxItem vBoxItem = (VBoxItem) event.getSource();
                    /*for (int i = 0; i < Controller.vBox.length; i++) {
                        Controller.vBox[i].setStyle("-fx-background-color: inherit;");
                        Controller.vBox[i].label.setTextFill(Color.web("#000000"));
                    }*/
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
                    System.out.println(s+" Clicked double");
                    if(!item.isDirectory())return;
                    treeView.getSelectionModel().select(item);
                    treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
                    Controller.vBox=new VBoxItem[item.getChildren().size()];
                    item.getChildren();
                    int i=0;
                    if(!((FilePathTreeItem)treeView.getSelectionModel().getSelectedItem()).getAbsolutePath().equals(Controller.hostName)) {

                        for (FilePathTreeItem fileItem : item.childrenArray) {

                            Label label= new Label(fileItem.getFile().getName());
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
                    }
                    else
                    {
                        for (FilePathTreeItem fileItem : Controller.drives)
                        {
                            Label label= new Label(fileItem.getAbsolutePath());
                            label.setPrefWidth(50);label.setMaxWidth(50);label.setMinWidth(50);label.setAlignment(Pos.CENTER);
                            Controller.vBox[i]=new VBoxItem(i,new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.SMALL_ICON)),label,fileItem);
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


                    //Table
                    /*treeView.getSelectionModel().select(item);
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
*/
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
