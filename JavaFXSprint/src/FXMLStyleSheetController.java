// Import various libraries necessary for project compliation
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.imageio.ImageIO;


public class FXMLStyleSheetController implements Initializable {
    
    // Intaintiate variables that will be used throughout the lifecycle of the controller
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
    public BufferedImage originalImage;
    public String extensionSaved;
    public File saveFile;
    
    // Intaintiate variables that are tied to components in the FXML
    @FXML
    private Window stage;
    public ImageView paintImage;
    Parent root;
    
    /*
        If the user clicks the Exit MenuItem Button from the Menu, this method
        will be called.  This method displays the user a confirmation dialog box
        prompting the user to either close out of the application or cancel and
        dismiss the dialog box.
    */
    @FXML
    public void confirmExit() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirming Exit");
        alert.setHeaderText("Leaving the program");
        alert.setContentText("Are you sure you want to exit this program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        } else {
            //Nothing happens when the user presses cancel besides the dialog box closing
        }
    }
    
    /*
        If the user clicks the Open MenuItem button from the Menu, this method
        will be called.  This method first opens a File Explorer by calling the
        "configureFileChooser" method.  After the user selects an image, the image
        file will be reaad and the image chosen will be placed and shown in the
        ImageView.
    */
    @FXML
    public void openFile(final ActionEvent e) {
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try{                   
                originalImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(originalImage, null);
                paintImage.setImage(image);
            }
            catch(Exception error) {
                System.out.println("There was an error");
            }
        }
    }
    
    /*
        This method is called from the method "openFile".  This method opens up
        a FileChooser obejct in the user's home folder, and allows the user to
        search by "All Images","jpg", or "png" files.
    */
    @FXML
    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
    }
 
    /*
        If the user clicks the Exit MenuItem button from the Menu, this method
        will be called.  This method first opens a confirmation dialog prompting
        the user to verify that they want to remove the image in the application.
        If they agree to remove the image, the ImageView will be set to none and
        no picture will appear in the display.  If the user presses cancel, there
        will be no action upon the ImageView and the dialog box will close.
    */
    @FXML
    public void clearingImage() {
        if(paintImage.getImage()!=null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("Removing the Image");
            alert.setContentText("Are you sure you want to remove the existing image?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                paintImage.setImage(null);
            } else {
                //Nothing happens when the user presses cancel
            }
        }
    }
    
    /*
        If the user clicks the Save MenuItem button from the Menu, this method
        will be called.  This method first tests whether or not the user has
        already saved the file.  If they have not saved the file, the "saveAs"
        method will be called essentially.  The code in the else bracket is the
        exact same as the code for the "saveAs" method.  If, on the other hand,
        the user has already saved the file, this will resave the image that was
        last loaded into the ImageView.  The file will save as a jpg or png based
        on what the image was last saved as.  Be aware that this will rewrite the
        file.
    */
    @FXML
    public void save(ActionEvent e) {
        if (saveFile != null) {
            try {
                switch(extensionSaved) {
                    case "jpg": ImageIO.write(originalImage,"jpg",saveFile); break;
                    case "png": ImageIO.write(originalImage,"png",saveFile); break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        else {
            FileChooser fileChooserSave = new FileChooser();
            fileChooserSave.setTitle("Save Image");
            fileChooserSave.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File fileSave = fileChooserSave.showSaveDialog(stage);
            saveFile = fileSave;
            String fileName = fileSave.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".")+1,fileSave.getName().length());
            if (fileSave != null) {
                try {
                    switch(fileExtension) {
                        case "jpg": ImageIO.write(originalImage,"jpg",fileSave); extensionSaved = "jpg";break;
                        case "png": ImageIO.write(originalImage,"png",fileSave); extensionSaved = "png";break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    /*
        If the user clicks the Save As MenuItem button from the Menu, this method
        will be called.  This method opens up a FileChooser object prompting the
        user to find a location to save the image.  The file name is currently
        left blank prompting the user to leave their own name for the image.  
        The user can either choose to save the photo as a jpg or png (jpg is 
        selected by default).  If the user chooses to close out of the FileChooser
        without selected a place to save the image, nothing will occur.
    */
    @FXML
    public void saveAs(ActionEvent e) {
            FileChooser fileChooserSave = new FileChooser();
            fileChooserSave.setTitle("Save Image");
            fileChooserSave.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File fileSave = fileChooserSave.showSaveDialog(stage);
            saveFile = fileSave;
            String fileName = fileSave.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".")+1,fileSave.getName().length());
            if (fileSave != null) {
                try {
                    switch(fileExtension) {
                        case "jpg": ImageIO.write(originalImage,"jpg",fileSave); extensionSaved = "jpg";break;
                        case "png": ImageIO.write(originalImage,"png",fileSave); extensionSaved = "png";break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
