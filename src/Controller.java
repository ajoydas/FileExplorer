import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

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

    TreeItem<String> rootNode;
    String hostName="computer";
    ObservableList<FilePathTreeItem> drives;
    Stack<FilePathTreeItem> backlist;
    Image fileImage = new Image(
            getClass().getResourceAsStream("file-icon.png"));
    Image folderImage = new Image(
            getClass().getResourceAsStream("folder-icon.png"));
    SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        rootNode=new TreeItem<>(hostName,new ImageView(new Image(ClassLoader.getSystemResourceAsStream("folder-icon.png"))));
        Iterable<Path> rootDirectories= FileSystems.getDefault().getRootDirectories();
        for(Path name:rootDirectories){
            FilePathTreeItem treeNode=new FilePathTreeItem(name.toFile());
            drives.add(treeNode);
            rootNode.getChildren().add(treeNode);

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

        treeView.setRoot(rootNode);
        FilePathTreeItem currItem=getSpecificItem(System.getProperty("user.dir"));
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
                    FilePathTreeItem item = getSpecificItem(tCurrDir.getText());
                    addTableItems(item);
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
                        System.out.println("Stack Poped -> "+current.getFile());
                        if(!backlist.empty()) {
                            current=backlist.pop();
                            System.out.println("Stack Poped -> "+current.getFile());
                            addTableItems(current);
                        }
                        else
                        {
                            System.out.println("Stack Pushed -> "+current.getFile());
                            backlist.push(currItem);
                        }
                    }

                }
        );

        bUp.setOnMouseClicked(event ->
                {
                    FilePathTreeItem item = getSpecificItem(backlist.peek().getFile().getParent());
                    addTableItems(item);
                    //backlist.push(item);
                    // System.out.println("Stack Pushed -> "+item.getFile());
                }
        );

    }


    private void addTableItems(FilePathTreeItem item) {
        treeView.getSelectionModel().select(item);
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

        ObservableList<FileDetails> imgList = FXCollections.observableArrayList();
        item.getChildren();
        for (FilePathTreeItem fileItem : item.childrenArray)
        {
            //System.out.println("Loading files "+fileItem.getFile().toString());
            FileDetails fileDetails = new FileDetails(new ImageView(getIcon(fileItem)),fileItem.getFile().getName(),
                    String.valueOf(fileItem.getFile().length()),
                    String.valueOf(sdf.format(fileItem.getFile().lastModified())),fileItem);
        //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
        imgList.add(fileDetails);
        }
        tableView.getItems().clear();
        tableView.setItems(imgList);
        tCurrDir.setText(item.getFile().toString());
        backlist.push(item);
        System.out.println("Stack Pushed -> "+item.getFile());
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
        while (st.hasMoreTokens()) {
            filename+=st.nextToken();
            //System.out.println(filename);
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
                //fileItem.getChildren();
                fileItem.setExpanded(true);
                for (FilePathTreeItem item:fileItem.childrenArray)
                {
                    //System.out.println(item.getFile().toString());
                    if(item.getFile().toString().equals(filename))
                    {
                        fileItem=item;
                        break;
                    }
                }
                filename+="\\";

            }

        }
        return fileItem;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println(((TreeItem)(treeView.getSelectionModel().getSelectedItem())).getValue());
        if(((TreeItem)(treeView.getSelectionModel().getSelectedItem()))!=rootNode) {
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
                FileDetails fileDetails = new FileDetails(new ImageView(getIcon(fileItem)),fileItem.getFile().getName(),
                        String.valueOf(fileItem.getFile().length()),
                        String.valueOf(sdf.format(fileItem.getFile().lastModified())),fileItem);
                //FileDetails item_2 = new FileDetails(new ImageView(writableImage),"File2","700kb","20-4-17");
                imgList.add(fileDetails);
            }
            tableView.getItems().clear();
            tableView.setItems(imgList);
            tCurrDir.setText(rootNode.getValue());
            //backlist.push(rootNode);
            //System.out.println("Stack Pushed -> "+item.getFile());
        }
    }
}
