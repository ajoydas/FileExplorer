# FileExplorer
App type: JavaFx

This provide a graphical user interface that can show all the files in any directory.
It support two different style of viewing the files.
In Windows os it is able to run from any folder and show the files of that folder. While in Linux it starts from user's home directory.

It opens the file with the default application.

# Documentation:
**Part 1: Classes:**

Main [extends Application] :
Starts the JavaFx application and sets the layout.
- start
- main

Controller [implements Initializable]:
This class contains the controller codes. It sets multiple (go, back, up, treeview and tableview click) event listeners, sets item on table/tile view and converts addresses to file tree items.
- initialize
- addTileItems
- addTableItems
- getItemFromAddress
- treeViewMouseClicked
- showItems

FileTreeItem [extends TreeItem<String>]:
An extened treeItem that contains File information. It implements lazy loading, sets the icon on treeItem. 
- FileTreeItem (String)  : for root node
- FileTreeItem (File) : for other nodes
- getChildren
- isLeaf
- buildChildren

ChildArrayHelper :
It returns the children array of the special rootnode or general filetreenode depending on the input. Works as a factory class.
- getChildren

MenuSetting:
It saves the current view (details/tiles) that is showing and the current filetreeitem that has been populated on the view. Works as a singleton setting class.
- MenuSetting
- getInstance

FileDetails :
It stores file info (file tree item, name, size, last modified date) for viewing on a table row. Works as a model class.

VBoxItem [extends VBox] :
It extends the functionality of a Vbox for storing information of a file tree item. It contains onclick listener on that vbox and sets and resets the background depending on the number of clicks. Works as a adapter class.
- VBoxItem
- setLayout

ImageHelper:
Generates the small or big sized image depending on the input. Works as a factory class.
- getIcon
- getSmallIcon
- getBigIcon
- toBufferedImage



**Part 2:**

Singleton Pattern:
- MenuSetting
- java.lang.System
- javafx.application.Application

Factory Pattern:
- ChildArrayHelper
- ImageHelper
- java.text.SimpleDateFormat
- javafx.scene.paint.Color

Adapter Pattern:
- FilePathTreeItem
- VBoxItem
- javafx.collections.ObservableList

Composite Pattern:
- FilePathTreeItem
- java.io.File
- javafx.scene.control.TreeItem
- javafx.scene.layout.Pane
- javafx.scene.layout.AnchorPane
- javafx.scene.layout.FlowPane
- javafx.scene.layout.VBox



User Interface:

![Alt text](/Demo_images/mainview.JPG?raw=true "Main View")

![Alt text](/Demo_images/mainviewTiles.JPG?raw=true "Main Tiles View")

![Alt text](/Demo_images/view2.JPG?raw=true "Another Details View")

![Alt text](/Demo_images/view2tiles.JPG?raw=true "Another Tiles View")
