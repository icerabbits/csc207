package manageFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/*
    SubAndImgDir takes in a file directory (String). It then automatically finds all image files (files that end in .gif,
.png, .bmp, .jpq, .jpeg) anywhere under the directory. It stores each image file, in the form of a String, into an
ArrayList called imageDir. The string consists of the entire path name minus the root directory. For example,
given a directory "/Users/jacky/group_0583", an image with the path "/Users/jacky/group_0583/phase2/dog.jpg" is found,
the String "/phase2/dog.jpg" is stored into imageDir.
    Also, it finds the directories that are one level below it and stores it in the form of a String into an ArrayList
called subDir. The string consists of the directory name minus the root directory. For example, given a directory
"/Users/jacky/group_0583/", an sub-directory path of "/Users/jacky/group_0583/phase2", the String "phase2" is stored
into subDir.
    We concatenate this both ArrayList into an new ArrayList called subAndImgDir.
 */

public class FileManager {
    private File directory;
    private ArrayList<String> imageDir = new ArrayList<>();
    private ArrayList<String> subDir = new ArrayList<>();
    private ArrayList<String> subAndImgDir = new ArrayList<>();

    public FileManager(String dir){
        directory = new File(dir);
        if (dir.equals(System.getProperty("user.home"))){
            getImgCurrentDir();
        }
        else{
            getAllImg(directory);
        }
        getSubDir();
        subAndImgDir.addAll(subDir);
        subAndImgDir.addAll(imageDir);
    }

    private static final String[] fileTypes = new String[]{
            "gif", "png", "bmp", "jpg", "jpeg"
    };


    //Get images from all subdirectories.
    private void getAllImg(File dir){
        for (String ext : fileTypes) {
            if (dir.toString().endsWith("." + ext)) {
                String imageName = dir.toString();
                imageDir.add(imageName.substring(directory.toString().length()));
            }
        }
        if (dir.isDirectory()) {
            for (File f: dir.listFiles()){
                getAllImg(f);
            }
        }
    }
    /**Gets the images of the current directory */
    public void getImgCurrentDir(){
        ArrayList<String> list = new ArrayList<>();
            for (File f: directory.listFiles()){
                for (final String ext : fileTypes) {
                    if (f.toString().endsWith("." + ext)) {
                        imageDir.add(f.getName());
                    }
                }
            }
    }

    //Get the directories in the current directory.
    private void getSubDir(){
        if (directory.isDirectory()){
            for (File file: directory.listFiles()){
                if (file.isDirectory()){
                    subDir.add(file.getName());
                }
            }
        }
    }

    public ArrayList<String> getSubAndImgDir() {
        return subAndImgDir;
    }


}