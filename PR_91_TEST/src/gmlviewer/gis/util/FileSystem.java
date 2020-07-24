package gmlviewer.gis.util;

import javax.swing.JFileChooser;
import java.awt.Component;
import java.io.File;

public class FileSystem {
  public FileSystem() {
    super();
  }

  public File selectAFolder(Component comp) {

    JFileChooser folderChooser = this.getFolderChooser();

    int option = folderChooser.showOpenDialog( comp );

    if (option == folderChooser.CANCEL_OPTION) {
      return null;
    }
    else {
      File file = folderChooser.getSelectedFile();
      if (file != null && file.isDirectory() ) {
        return file;
      } else {
        return null;
      }
    }

  }

  public File selectAFile(Component comp) {

    return this.selectAFile( comp, this.getFileChooser() );

  }

  public File selectAFile(Component comp, JFileChooser fileChooser) {

    int option = fileChooser.showOpenDialog( comp );

    if (option == fileChooser.CANCEL_OPTION) {
      return null;
    }
    else {
      File file = fileChooser.getSelectedFile();
      if (file != null ) {
        return file;
      } else {
        return null;
      }
    }

  }


  private JFileChooser getFolderChooser() {
    if( this.folderChooser != null ) {
      return this.folderChooser;
    } else {
      JFileChooser folderChooser = new JFileChooser();
      folderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      folderChooser.setFileFilter(new FolderFilter());
      this.folderChooser = folderChooser;
      return folderChooser;
    }
  }

  private JFileChooser getFileChooser( ) {
    if( this.fileChooser != null ) {
      return this.fileChooser;
    } else {
      JFileChooser fileChooser = new JFileChooser();
      this.fileChooser = fileChooser;
      return fileChooser;
    }
  }


  // static member
  private static JFileChooser folderChooser;
  private static JFileChooser fileChooser;
  // end of static member

}
