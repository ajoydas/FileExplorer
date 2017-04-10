import java.awt.event.MouseEvent;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FilePathTreeItem extends TreeItem<String>{
    public static TextField textField=null;
    public static  ObservableList<FilePathTreeItem> allFolders=FXCollections.observableArrayList();
    public  ObservableList<FilePathTreeItem> childrenArray=FXCollections.observableArrayList();

    public static Image folderCollapseImage=new Image(ClassLoader.getSystemResourceAsStream("folder-icon.png"));
    public static Image folderExpandImage=new Image(ClassLoader.getSystemResourceAsStream("folder-icon.png"));
    public static Image fileImage=new Image(ClassLoader.getSystemResourceAsStream("file-icon.png"));
    private boolean isLeaf;
    private boolean isFirstTimeChildren=true;
    private boolean isFirstTimeLeaf=true;
    private final File file;
    public File getFile(){return(this.file);}
    private final String absolutePath;
    public String getAbsolutePath(){return(this.absolutePath);}
    private final boolean isDirectory;
    public boolean isDirectory(){return(this.isDirectory);}
    public FilePathTreeItem(String hostname){
        super(hostname);
        this.absolutePath=hostname;
        this.file=null;
        this.isDirectory=true;
    }
    public FilePathTreeItem(File file){
        super(file.toString());
        this.file=file;
        this.absolutePath=file.getAbsolutePath();
        this.isDirectory=file.isDirectory();
        if(this.isDirectory){
            this.setGraphic(new ImageView(folderCollapseImage));
            //add event handlers
            this.addEventHandler(TreeItem.branchCollapsedEvent(),new EventHandler(){
                @Override
                public void handle(Event e){
                    FilePathTreeItem source=(FilePathTreeItem)e.getSource();
                    //System.out.println(source.absolutePath);
                    textField.setText(source.absolutePath);
                    if(!source.isExpanded()){
                        ImageView iv=(ImageView)source.getGraphic();
                        iv.setImage(folderCollapseImage);
                    }
                }
            } );
            this.addEventHandler(TreeItem.branchExpandedEvent(),new EventHandler(){
                @Override
                public void handle(Event e){
                    FilePathTreeItem source=(FilePathTreeItem)e.getSource();
                    //System.out.println(source.absolutePath);
                    textField.setText(source.absolutePath);
                    if(source.isExpanded()){
                        ImageView iv=(ImageView)source.getGraphic();
                        iv.setImage(folderExpandImage);
                    }
                }
            } );


        }else{
            this.setGraphic(new ImageView(fileImage));
        }
        //set the value (which is what is displayed in the tree)
        String fullPath=file.getAbsolutePath();
        if(!fullPath.endsWith(File.separator)){
            String value=file.toString();
            int indexOf=value.lastIndexOf(File.separator);
            if(indexOf>0){
                this.setValue(value.substring(indexOf+1));
            }else{
                this.setValue(value);
            }
        }
    }

    @Override
    public ObservableList<TreeItem<String>> getChildren(){
        //System.out.println("Getting Children for "+file.toString());
        if(isFirstTimeChildren){
            isFirstTimeChildren=false;
            super.getChildren().setAll(buildChildren(this));
        }
        return(super.getChildren());
    }

    @Override
    public boolean isLeaf(){
        if(isFirstTimeLeaf){
            isFirstTimeLeaf=false;
            if(!this.absolutePath.equals(Controller.hostName)) isLeaf=this.file.isFile();
            else isLeaf=false;
        }
        return(isLeaf);
    }

    private ObservableList<FilePathTreeItem> buildChildren(FilePathTreeItem treeItem){
        System.out.println("Building Children for abs "+absolutePath+" host "+Controller.hostName);
        childrenArray=FXCollections.observableArrayList();
        if(this.absolutePath.equals(Controller.hostName))
        {
            childrenArray=Controller.drives;
            return Controller.drives;
        }
        File f=treeItem.getFile();
        if((f!=null)&&(f.isDirectory())){
            File[] files=f.listFiles();
            if (files!=null){
                ObservableList<FilePathTreeItem> children=FXCollections.observableArrayList();

                for(File childFile:files){
                    FilePathTreeItem item= new FilePathTreeItem(childFile);
                    children.add(item);
                    //System.out.println("Adding child");
                    childrenArray.add(item);
                }
                return(children);
            }
        }
        return FXCollections.emptyObservableList();
    }

}