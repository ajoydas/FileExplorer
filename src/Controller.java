import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;

public class Controller implements Initializable {
    public TreeView treeView;
    public TableView tableView;
    public TextField tCurrDir;
    public Button bGo;
    public MenuBar mMenu;
    public Button bBack;
    public Button bUp;
    public FlowPane flowPane;
    public GridPane gridPane;
    public ScrollPane scroll1;
    public ScrollPane scroll2;
    public MenuItem menuDetails;
    public MenuItem menuTiles;

    private TableColumn tableIcon;
    private TableColumn tableName;
    private TableColumn tableSize;
    private TableColumn tableDate;
    private FileTreeItem rootNode;
    public static String hostName="computer";
    public  static ObservableList<FileTreeItem> drives=null;
    public static VBoxItem[] vBox;
    public  static  Stack<FileTreeItem> backlist;
    private static  SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        flowPane.setVisible(false);
        scroll1.setVisible(false);
        gridPane.setVisible(false);
        scroll2.setVisible(false);

        VBoxItem.treeView=treeView;
        VBoxItem.tCurrDir=tCurrDir;
        VBoxItem.flowPane=flowPane;
        VBoxItem.tableView=tableView;

        FileTreeItem.textField=tCurrDir;
        backlist=new Stack<>();

        System.out.println("Working Directory = " +System.getProperty("user.dir"));
        try{hostName= InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){
            x.printStackTrace();
        }
        tCurrDir.setText(System.getProperty("user.dir"));
        drives = FXCollections.observableArrayList();
        /*if(System.getProperty("os.name").toLowerCase().contains("windows")) {

            Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
            for (Path name : rootDirectories) {
                FileTreeItem treeNode = new FileTreeItem(name.toFile());
                drives.add(treeNode);
            }
        }*/

        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++)
        {
            FileTreeItem treeNode = new FileTreeItem(f[i]);
            drives.add(treeNode);
        }


        rootNode=new FileTreeItem(hostName);
        treeView.setRoot(rootNode);
        FileTreeItem currItem= getItemFromAddress(System.getProperty("user.dir"));
        //FileTreeItem currItem= getItemFromAddress("/home");

        treeView.getSelectionModel().select(currItem);
        showItems(currItem);

        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        tableIcon=new TableColumn<FileDetails, ImageView>("Icon");
        tableIcon.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("icon"));
        tableIcon.setResizable(false);
        tableIcon.setPrefWidth(50);
        tableIcon.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(tableIcon);
        tableName=new TableColumn<FileDetails, ImageView>("Name");
        tableName.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("fileName"));
        tableName.setMinWidth(150);
        tableView.getColumns().add(tableName);

        tableSize=new TableColumn<FileDetails, ImageView>("Size");
        tableSize.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("size"));
        tableSize.setStyle("-fx-alignment: CENTER-RIGHT;");
        tableView.getColumns().add(tableSize);
        tableDate=new TableColumn<FileDetails, ImageView>("Date Of Modification");
        tableDate.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("date"));
        tableDate.setStyle("-fx-alignment: CENTER-RIGHT;");
        tableDate.setMinWidth(120);
        tableView.getColumns().add(tableDate);

        menuDetails.setOnAction(e->
        {
            tableView.setVisible(true);
            flowPane.setVisible(false);
            scroll1.setVisible(false);
            MenuSetting.getInstance().view="details";
            showItems(MenuSetting.getInstance().currItem);
        });
        menuTiles.setOnAction(e->
        {
            tableView.setVisible(false);
            flowPane.setVisible(true);
            scroll1.setVisible(true);
            MenuSetting.getInstance().view="tiles";
            showItems(MenuSetting.getInstance().currItem);
        });

        bGo.setOnMouseClicked(event ->
                {
                    String input= tCurrDir.getText();
                    if(input.equals(hostName)) showItems(rootNode);
                    else showItems(getItemFromAddress(tCurrDir.getText()));
                }
        );
        tableView.setRowFactory( tv -> {
            TableRow<FileDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    FileDetails rowData = row.getItem();
                    if(rowData.getItem().isDirectory())
                    {
                        showItems(rowData.getItem());
                        //addTileItems(rowData.getItem());
                    }
                    else
                    {
                        /*try {
                            Desktop.getDesktop().open(rowData.getItem().getFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        try {
                            if( Desktop.isDesktopSupported() )
                            {
                                new Thread(() -> {
                                    try {
                                        Desktop.getDesktop().open(rowData.getItem().getFile());
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }).start();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return row ;
        });
        bBack.setOnMouseClicked(event ->
                {
                    if(!backlist.empty()){
                        FileTreeItem current= backlist.pop();
                        System.out.println("Stack Poped -> "+current.getAbsolutePath());
                        if(!backlist.empty()) {
                            current=backlist.pop();
                            System.out.println("Stack Poped -> "+current.getAbsolutePath());
                            showItems(current);
                           //addTileItems(current);
                        }
                        else {
                            System.out.println("Stack Pushed -> "+current.getAbsolutePath());
                            backlist.push(currItem);
                        }
                    }

                }
        );

        bUp.setOnMouseClicked(event ->
                {
                    if(!backlist.peek().getAbsolutePath().equals(hostName) && backlist.peek().getFile().getParent()==null){
                        showItems(rootNode);
                        //addTileItems(rootNode);
                    }
                    else if (!backlist.peek().getAbsolutePath().equals(hostName) && backlist.peek().getFile().getParent()!=null) {
                        System.out.println(" Parent : " + backlist.peek().getFile().getParent());
                        FileTreeItem item = getItemFromAddress(backlist.peek().getFile().getParent());
                        showItems(item);
                        //addTileItems(item);
                    }
                }
        );
    }

    private void addTileItems(FileTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        vBox=new VBoxItem[item.getChildren().size()];
        item.getChildren();
        ObservableList<FileTreeItem> list = ChildArrayHelper.getChildren(item);
        int i=0;
        Label label;
        for (FileTreeItem fileItem : list ) {
            if(list==drives)label= new Label(fileItem.getAbsolutePath());
            else label= new Label(fileItem.getFile().getName());
            label.setPrefWidth(50);label.setMaxWidth(50);label.setMinWidth(50);label.setAlignment(Pos.CENTER);
            try {
                vBox[i]=new VBoxItem(i,new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.BIG_ICON)),label,fileItem);
                i++;
            } catch (Exception e) {
                vBox[i]=new VBoxItem(i,new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.SMALL_ICON)),label,fileItem);
                i++;
                e.printStackTrace();
            }
        }
        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(vBox);
        tCurrDir.setText(item.getAbsolutePath());
        backlist.push(item);
        System.out.println("Stack Pushed -> "+item.getAbsolutePath());
    }


    private void addTableItems(FileTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
        item.getChildren();
        String name;
        ObservableList<FileTreeItem> list = ChildArrayHelper.getChildren(item);
        for (FileTreeItem fileItem : list) {
            if(list==drives)name=fileItem.getAbsolutePath();
            else name=fileItem.getFile().getName();
            //System.out.println("Loading files "+fileItem.getFile().toString());
            FileDetails fileDetails = new FileDetails(new ImageView(ImageHelper.getIcon(fileItem,ImageHelper.SMALL_ICON)),
                    name,
                    String.valueOf(fileItem.getFile().length()),
                    String.valueOf(sdf.format(fileItem.getFile().lastModified())), fileItem);
            //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
            imgList.add(fileDetails);
        }

        tableView.getItems().clear();
        tableView.setItems(imgList);
        tCurrDir.setText(item.getAbsolutePath());
        backlist.push(item);
        System.out.println("Stack Pushed -> "+item.getAbsolutePath());
    }


    private FileTreeItem getItemFromAddress(String currDir) {
        boolean isDrive=true;
        StringTokenizer st = new StringTokenizer(currDir,"/");
        if(System.getProperty("os.name").toLowerCase().contains("windows"))st = new StringTokenizer(currDir,"\\");
        FileTreeItem fileItem=null;
        String filename="";
        rootNode.setExpanded(true);
        if(!System.getProperty("os.name").toLowerCase().contains("windows"))
        {
            filename="/";
            for (FileTreeItem item: drives)
            {
                //System.out.println(item.getFile().toString());
                if(item.getFile().toString().equals(filename))
                {
                    fileItem=item;
                    break;
                }
            }
        }
        while (st.hasMoreTokens()) {
            filename+=st.nextToken();
            System.out.println(filename);
            if(isDrive && System.getProperty("os.name").toLowerCase().contains("windows")) {
                filename+="\\";
                isDrive=false;
                //ObservableList<FileTreeItem> children= rootNode.getChildren();
                for (FileTreeItem item: drives)
                {
                    //System.out.println(item.getFile().toString());
                    if(item.getFile().toString().equals(filename))
                    {
                        fileItem=item;
                        break;
                    }
                }
            }
            else {
                //System.out.println("In Else Item ");
                fileItem.getChildren();
                fileItem.setExpanded(true);
                for (FileTreeItem item:fileItem.childrenArray)
                {
                    //System.out.println("In for loop "+item.getFile().toString());
                    if(item.getFile().toString().equals(filename))
                    {
                        fileItem=item;
                        break;
                    }
                }
                if(System.getProperty("os.name").toLowerCase().contains("windows"))filename+="\\";
                else filename+="/";
            }
        }
        //System.out.println("Returned Item "+fileItem);
        return fileItem;
    }
    /*private FileTreeItem getItemFromAddress(String currDir) {
        boolean isDrive=true;
        StringTokenizer st = new StringTokenizer(currDir,"\\");
        FileTreeItem fileItem=null;
        String filename="";
        rootNode.setExpanded(true);
        while (st.hasMoreTokens()) {
            filename+=st.nextToken();
            System.out.println(filename);
            if(isDrive) {
                filename+="\\";
                isDrive=false;
                //ObservableList<FileTreeItem> children= rootNode.getChildren();
                for (FileTreeItem item: drives)
                {
                    //System.out.println(item.getFile().toString());
                    if(item.getFile().toString().equals(filename))
                    {
                        fileItem=item;
                        break;
                    }
                }
            }
            else {
                //System.out.println("In Else Item ");
                fileItem.getChildren();
                fileItem.setExpanded(true);
                for (FileTreeItem item:fileItem.childrenArray)
                {
                    //System.out.println("In for loop "+item.getFile().toString());
                    if(item.getFile().toString().equals(filename))
                    {
                        fileItem=item;
                        break;
                    }
                }
                filename+="\\";
            }
        }
        //System.out.println("Returned Item "+fileItem);
        return fileItem;
    }*/

    public void treeViewMouseClicked(MouseEvent mouseEvent) {
        //System.out.println(((TreeItem)(treeView.getSelectionModel().getSelectedItem())).getValue());
        FileTreeItem item = (FileTreeItem) treeView.getSelectionModel().getSelectedItem();
        System.out.println(item.getAbsolutePath());
        showItems(item);
    }

    private void showItems(FileTreeItem item)
    {
        MenuSetting.getInstance().currItem=item;
        System.out.println(MenuSetting.getInstance().currItem);
        System.out.println(MenuSetting.getInstance().view);
        if(MenuSetting.getInstance().view.equals("details"))
        {
            addTableItems(item);
        }
        else
        {
            addTileItems(item);
        }
    }

}
