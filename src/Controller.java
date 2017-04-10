import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
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
    public TableColumn tableIcon;
    public TableColumn tableName;
    public TableColumn tableSize;
    public TableColumn tableDate;
    public FlowPane flowPane;
    public GridPane gridPane;
    public ScrollPane scroll1;
    public ScrollPane scroll2;

    FilePathTreeItem rootNode;
    public static String hostName="computer";
    public  static ObservableList<FilePathTreeItem> drives=null;
    public static VBoxItem[] vBox;
    Stack<FilePathTreeItem> backlist;
    Image fileImage = new Image(
            getClass().getResourceAsStream("file-icon.png"));
    Image folderImage = new Image(
            getClass().getResourceAsStream("folder-icon.png"));
    Image fileTile = new Image(
            getClass().getResourceAsStream("file-tile.png"));
    Image folderTile = new Image(
            getClass().getResourceAsStream("folder-tile.png"));
    SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setVisible(false);
        //flowPane.setVisible(false);
        gridPane.setVisible(false);
        //scroll1.setVisible(false);
        scroll2.setVisible(false);
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

        int i=0;
        for ( i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            RowConstraints row = new RowConstraints(100);
            gridPane.getColumnConstraints().add(column);
            gridPane.getRowConstraints().add(row);
        }
        vBox=new VBoxItem[4];
        for (i=0;i<4;i++) {
            vBox[i]=new VBoxItem(new ImageView(folderTile),new Label("File "+i));
            /*GridPane.setConstraints(vBox[i], i, 0);
            gridPane.getChildren().add(vBox[i]);*/
            flowPane.getChildren().add(vBox[i]);
        }






        ObservableList<ImageView> images = FXCollections.observableArrayList();
        images.addAll(new ImageView(fileImage),new ImageView(folderImage));
//        flowPane.getChildren().add(vBox1);
//        flowPane.getChildren().add(vBox2);
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File2")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File3")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File4")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File4")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File5")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File6")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File7")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));
        flowPane.getChildren().add(new VBox(new ImageView(folderTile), new Label("File1")));









        FilePathTreeItem.textField=tCurrDir;
        backlist=new Stack<>();
        /*TreeItem<String> treeItemRoot = new TreeItem<> ("Root");

        TreeItem<String> nodeItemA = new TreeItem<>("Item A");
        TreeItem<String> nodeItemB = new TreeItem<>("Item B");
        TreeItem<String> nodeItemC = new TreeItem<>("Item C");
        treeItemRoot.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC);

        TreeItem<String> nodeItemA1 = new TreeItem<>("Item A1",
                new ImageView(folderImage));
        TreeItem<String> nodeItemA2 = new TreeItem<>("Item A2",
                new ImageView(folderImage));
        TreeItem<String> nodeItemA3 = new TreeItem<>("Item A3",
                new ImageView(fileImage));
        TreeItem<String> nodeItemB1 = new TreeItem<>("Item A1",
                new ImageView(folderImage));
        TreeItem<String> nodeItemB2 = new TreeItem<>("Item A2",
                new ImageView(folderImage));
        TreeItem<String> nodeItemB3 = new TreeItem<>("Item A3",
                new ImageView(fileImage));

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3);
        nodeItemB.getChildren().addAll(nodeItemB1, nodeItemB2, nodeItemB3);

        //treeView= new TreeView<>(treeItemRoot);
        treeView.setRoot(treeItemRoot);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().select(nodeItemB2);*/

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
            /*ObservableList<FileDetails> imgList = FXCollections.observableArrayList();

                System.out.println("Loading files "+treeNode.getFile().toString());
                FileDetails fileDetails = new FileDetails(new ImageView(getIcon(treeNode)),treeNode.getFile().getName(),
                        String.valueOf(treeNode.getFile().length()),
                        String.valueOf(sdf.format(treeNode.getFile().lastModified())),treeNode);
                //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                imgList.add(fileDetails);

            tableView.getItems().clear();
            tableView.setItems(imgList);*/
        }

        rootNode=new FilePathTreeItem(hostName);

        treeView.setRoot(rootNode);
        FilePathTreeItem currItem=getSpecificItem(System.getProperty("user.dir"));
        System.out.println("First Item"+ currItem);
        treeView.getSelectionModel().select(currItem);
        addTableItems(currItem);
        //backlist.push(currItem);
        //System.out.println("Stack Pushed -> "+currItem.getFile());
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        tableIcon=new TableColumn<FileDetails, ImageView>("Icon");
        tableIcon.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("icon"));
        tableIcon.setResizable(false);
        tableIcon.setPrefWidth(50);
        tableView.getColumns().add(tableIcon);
        tableName=new TableColumn<FileDetails, ImageView>("Name");
        tableName.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("fileName"));
        tableName.setMinWidth(150);
        tableView.getColumns().add(tableName);

        tableSize=new TableColumn<FileDetails, ImageView>("Size");
        tableSize.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("size"));
        tableView.getColumns().add(tableSize);
        tableDate=new TableColumn<FileDetails, ImageView>("Date Of Modification");
        tableDate.setCellValueFactory(new PropertyValueFactory<FileDetails, ImageView>("date"));
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

        bGo.setOnMouseClicked(event ->
                {
                    String input= tCurrDir.getText();
                    if(input.equals(hostName))addTableItems(rootNode);
                     else addTableItems(getSpecificItem(tCurrDir.getText()));
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
                        addTableItems(rowData.getItem());
                        //backlist.push(rowData.getItem());
                        //System.out.println("Stack Pushed -> "+rowData.getItem().getFile());
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
                            addTableItems(current);
                        }
                        else
                        {
                            System.out.println("Stack Pushed -> "+current.getAbsolutePath());
                            backlist.push(currItem);
                        }
                    }

                }
        );

        bUp.setOnMouseClicked(event ->
                {
                    if(!backlist.peek().getAbsolutePath().equals(hostName) && backlist.peek().getFile().getParent()==null){
                        addTableItems(rootNode);
                    }
                    else if (!backlist.peek().getAbsolutePath().equals(hostName) && backlist.peek().getFile().getParent()!=null) {
                        System.out.println(" Parent : " + backlist.peek().getFile().getParent());
                        FilePathTreeItem item = getSpecificItem(backlist.peek().getFile().getParent());
                        addTableItems(item);
                        //backlist.push(item);
                        // System.out.println("Stack Pushed -> "+item.getFile());
                    }
                }
        );

    }


    private void addTableItems(FilePathTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
        item.getChildren();
        if(!((FilePathTreeItem)treeView.getSelectionModel().getSelectedItem()).getAbsolutePath().equals(hostName)) {

            for (FilePathTreeItem fileItem : item.childrenArray) {
                //System.out.println("Loading files "+fileItem.getFile().toString());
                FileDetails fileDetails = new FileDetails(new ImageView(getIcon(fileItem)), fileItem.getFile().getName(),
                        String.valueOf(fileItem.getFile().length()),
                        String.valueOf(sdf.format(fileItem.getFile().lastModified())), fileItem);
                //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                imgList.add(fileDetails);
            }
        }
        else
        {
            for (FilePathTreeItem fileItem : drives)
            {
                //System.out.println("Loading files "+fileItem.getFile().toString());
                FileDetails fileDetails = new FileDetails(new ImageView(getIcon(fileItem)),fileItem.getFile().toString(),
                        String.valueOf(fileItem.getFile().length()),
                        String.valueOf(sdf.format(fileItem.getFile().lastModified())),fileItem);
                //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                imgList.add(fileDetails);
            }
        }
        tableView.getItems().clear();
        tableView.setItems(imgList);
        tCurrDir.setText(item.getAbsolutePath());
        backlist.push(item);
        System.out.println("Stack Pushed -> "+item.getAbsolutePath());
    }

    private WritableImage getIcon(FilePathTreeItem fileItem) {
        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(fileItem.getFile());
        java.awt.Image image = icon.getImage();
        BufferedImage bufferedImage = ImageHelper.toBufferedImage(image);
        return SwingFXUtils.toFXImage(bufferedImage, null);
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
            if(isDrive)
            {
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
            else
            {
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
        System.out.println("Returned Item "+fileItem);
        return fileItem;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        //System.out.println(((TreeItem)(treeView.getSelectionModel().getSelectedItem())).getValue());
        FilePathTreeItem item = (FilePathTreeItem) treeView.getSelectionModel().getSelectedItem();
        System.out.println(item.getAbsolutePath());
        addTableItems(item);
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


}
