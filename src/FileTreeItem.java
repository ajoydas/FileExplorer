import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class FileTreeItem extends TreeItem<String>{
    public static TextField textField=null;
    public  ObservableList<FileTreeItem> childrenArray=FXCollections.observableArrayList();

    public static Image folderImage=new Image(ClassLoader.getSystemResourceAsStream("folder-icon.png"));
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
    public FileTreeItem(String hostname){
        super(hostname);
        this.absolutePath=hostname;
        this.file=null;
        this.isDirectory=true;
    }
    public FileTreeItem(File file){
        super(file.toString());
        this.file=file;
        this.absolutePath=file.getAbsolutePath();
        this.isDirectory=file.isDirectory();
        if(this.isDirectory){
            this.setGraphic(new ImageView(folderImage));

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

    private ObservableList<FileTreeItem> buildChildren(FileTreeItem treeItem){
        //System.out.println("Building Children for abs "+absolutePath+" host "+Controller.hostName);
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
                ObservableList<FileTreeItem> children=FXCollections.observableArrayList();

                for(File childFile:files){
                    FileTreeItem item= new FileTreeItem(childFile);
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