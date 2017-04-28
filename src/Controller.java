import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
    private FilePathTreeItem rootNode;
    public static String hostName="computer";
    public  static ObservableList<FilePathTreeItem> drives=null;
    public static VBoxItem[] vBox;
    public  static  Stack<FilePathTreeItem> backlist;
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
        /*VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(new ImageView(folderTile), new Label("File1"));
        vBox1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("File1 clicked");
                event.consume();
            }
        });
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(new ImageView(folderTile), new Label("File2"));
        vBox2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("File2 clicked");
                event.consume();
            }
        });*/
       /* VBox[] vBox=new VBox[2];
        int i=0;
        for (i=0;i<2;i++)
        {
            vBox[i].getChildren().addAll(new ImageView(folderTile), new Label("File"+i+3));
            vBox[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("File"+i+"clicked");
                    event.consume();
                }
            });
        }*/

       /* int i=0;
        for ( i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            RowConstraints row = new RowConstraints(100);
            gridPane.getColumnConstraints().add(column);
            gridPane.getRowConstraints().add(row);
        }
        vBox=new VBoxItem[4];
        for (i=0;i<4;i++) {
            vBox[i]=new VBoxItem(new ImageView(folderTile),new Label("File "+i));
            //GridPane.setConstraints(vBox[i], i, 0);
            //gridPane.getChildren().add(vBox[i]);
            flowPane.getChildren().add(vBox[i]);
        }
        */
        FilePathTreeItem.textField=tCurrDir;
        backlist=new Stack<>();

        System.out.println("Working Directory = " +System.getProperty("user.dir"));
        try{hostName= InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){
            x.printStackTrace();
        }
        tCurrDir.setText(System.getProperty("user.dir"));
        drives=FXCollections.observableArrayList();
        Iterable<Path> rootDirectories= FileSystems.getDefault().getRootDirectories();
        for(Path name:rootDirectories){
            FilePathTreeItem treeNode=new FilePathTreeItem(name.toFile());
            drives.add(treeNode);
            //rootNode.getChildren().add(treeNode);
        }

        rootNode=new FilePathTreeItem(hostName);
        treeView.setRoot(rootNode);
        FilePathTreeItem currItem=getSpecificItem(System.getProperty("user.dir"));
        //System.out.println("First Item"+ currItem);
        treeView.getSelectionModel().select(currItem);
        showItems(currItem);
        //backlist.push(currItem);
        //System.out.println("Stack Pushed -> "+currItem.getFile());
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

        /*for (FilePathTreeItem fileItem : FilePathTreeItem.allFolders){
            System.out.println(fileItem.getAbsolutePath());
            if(fileItem.getAbsolutePath().equals(tCurrDir.getText()))
            {
                treeView.getSelectionModel().select(fileItem);
                break;
            }
        }*/

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
                    else showItems(getSpecificItem(tCurrDir.getText()));
                    //backlist.push(item);
                   // System.out.println("Stack Pushed -> "+item.getFile());
                }
        );
        tableView.setRowFactory( tv -> {
            TableRow<FileDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    FileDetails rowData = row.getItem();
                    //System.out.println(rowData.getItem().getFile());
                    if(rowData.getItem().isDirectory())
                    {
                        showItems(rowData.getItem());
//                        addTableItems(rowData.getItem());
//                        addTileItems(rowData.getItem());
                    }
                }
            });
            return row ;
        });
        bBack.setOnMouseClicked(event ->
                {
                    if(!backlist.empty()){
                        FilePathTreeItem current= backlist.pop();
                        System.out.println("Stack Poped -> "+current.getAbsolutePath());
                        if(!backlist.empty()) {
                            current=backlist.pop();
                            System.out.println("Stack Poped -> "+current.getAbsolutePath());
                            showItems(current);
//                            addTableItems(current);
//                            addTileItems(current);
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
//                        addTableItems(rootNode);
//                        addTileItems(rootNode);
                    }
                    else if (!backlist.peek().getAbsolutePath().equals(hostName) && backlist.peek().getFile().getParent()!=null) {
                        System.out.println(" Parent : " + backlist.peek().getFile().getParent());
                        FilePathTreeItem item = getSpecificItem(backlist.peek().getFile().getParent());
                        showItems(item);
//                        addTableItems(item);
//                        addTileItems(item);
                    }
                }
        );
    }

    private void addTileItems(FilePathTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        vBox=new VBoxItem[item.getChildren().size()];
        item.getChildren();
        ObservableList<FilePathTreeItem> list = ChildArrayHelper.getChildren(item);
        int i=0;
        Label label;
        for (FilePathTreeItem fileItem : list ) {
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


    private void addTableItems(FilePathTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
        item.getChildren();
        String name;
        ObservableList<FilePathTreeItem> list = ChildArrayHelper.getChildren(item);
        for (FilePathTreeItem fileItem : list) {
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


    private FilePathTreeItem getSpecificItem(String currDir) {
        boolean isDrive=true;
        StringTokenizer st = new StringTokenizer(currDir,"\\");
        FilePathTreeItem fileItem=null;
        String filename="";
        rootNode.setExpanded(true);
        while (st.hasMoreTokens()) {
            filename+=st.nextToken();
            System.out.println(filename);
            if(isDrive) {
                filename+="\\";
                isDrive=false;
                //ObservableList<FilePathTreeItem> children= rootNode.getChildren();
                for (FilePathTreeItem item: drives)
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
                for (FilePathTreeItem item:fileItem.childrenArray)
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
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        //System.out.println(((TreeItem)(treeView.getSelectionModel().getSelectedItem())).getValue());
        FilePathTreeItem item = (FilePathTreeItem) treeView.getSelectionModel().getSelectedItem();
        System.out.println(item.getAbsolutePath());
        showItems(item);
//        addTableItems(item);
//        addTileItems(item);
        /*if(!((FilePathTreeItem)treeView.getSelectionModel().getSelectedItem()).getAbsolutePath().equals(hostName)) {
            FilePathTreeItem item = (FilePathTreeItem) treeView.getSelectionModel().getSelectedItem();
            //FilePathTreeItem item = getSpecificItem(item1.);
            System.out.println(item.getFile());
            addTableItems(item);
        }
        else
        {
            System.out.println(rootNode);

            treeView.getSelectionModel().select(rootNode);
            treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

            ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
            for (FilePathTreeItem fileItem : drives)
            {
                //System.out.println("Loading files "+fileItem.getFile().toString());
                FileDetails fileDetails = new FileDetails(new ImageView(getIcon(fileItem)),fileItem.getFile().toString(),
                        String.valueOf(fileItem.getFile().length()),
                        String.valueOf(sdf.format(fileItem.getFile().lastModified())),fileItem);
                //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                imgList.add(fileDetails);
            }
            tableView.getItems().clear();
            tableView.setItems(imgList);
            tCurrDir.setText(rootNode.getAbsolutePath());
            backlist.push(rootNode);
            System.out.println("Stack Pushed -> "+rootNode.getAbsolutePath());
        }*/
    }

    private void showItems(FilePathTreeItem item)
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
