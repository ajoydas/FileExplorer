import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Controller implements Initializable {
    public TreeView treeView;
    public TableView tableView;
    public TextField tCurrDir;
    public Button bGo;
    public MenuBar mMenu;
    public Button bBack;
    public Button bUp;

    TreeItem<String> rootNode;
    String hostName="computer";
    ObservableList<FilePathTreeItem> drives;
    Image fileImage = new Image(
            getClass().getResourceAsStream("file-icon.png"));
    Image folderImage = new Image(
            getClass().getResourceAsStream("folder-icon.png"));
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FilePathTreeItem.textField=tCurrDir;

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

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));


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
        }

        treeView.setRoot(rootNode);

        treeView.getSelectionModel().select(getSpecificItem(System.getProperty("user.dir")));
        treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());

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
                    treeView.getSelectionModel().select(item);
                    treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
                }
        );
    }

    private FilePathTreeItem getSpecificItem(String currDir) {
        boolean isDrive=true;
        StringTokenizer st = new StringTokenizer(currDir,"\\");
        FilePathTreeItem fileItem=null;
        String filename="";
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
        tCurrDir.setText(fileItem.getFile().toString());
        return fileItem;
    }


//    public void mouseClicked(MouseEvent mouseEvent) {
//        TreeItem<String> item= (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
//
//    }
}
