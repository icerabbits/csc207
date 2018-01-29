package manageFiles;

import tags.TagManager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.net.*;

/**
 * ImageFile
 * Contains TagManager, name of the image file, path of that image file and file type
 * Can add, remove tags, reverse to a previous name, search for similar image by tags
 *
 * @author Gini Chin/Howard Huang
 */

public class ImageFile implements java.io.Serializable{
    private String fileName;
    private TagManager tags;
    private String fileDir;
    private String fileType;

    /**
     * Constructor.
     *
     * Initialize Image File with a new image
     */
    public ImageFile(){

    }
    /**
     * Constructor.
     * Initializing Image File with an existing image
     * If the image name includes a tag, at that tag to tag manager and all available tags
     * A tag is identify by @ symbol
     *
     * @param String fileNmae Replace file name with an existing file name
     * @param TagManager tags Initialize Tag Manager with an existing Tag Manager
     * @param String fileDir Initializing file directory with a given file directory
     * @param String fileType Initialize information for file type
     */
    public ImageFile(String fileName, TagManager tags, String fileDir, String fileType) {
        this.fileDir = fileDir;
        this.tags = tags;
        String [] temp = fileName.split("@");
        this.fileName = temp[0].trim();
        if (temp.length >1){
            ArrayList existingTags = new ArrayList();
            String tag;
            for (int i=1; i<temp.length-1; i++){
                if(temp[i].trim().length()>0){
                    tag = "@"+ temp[i].trim();
                    existingTags.add(tag);
                }
            }
            tag = "@"+ temp[temp.length-1].trim();
            existingTags.add(tag);
            tags.setCurTags(existingTags);
        }
        this.fileType = fileType;
    }

    /**
     * Set fileDir
     * @param String fileDir set file directory with a given String
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    /**
     * Set the File name
     *
     * @param  String name set file name with a given file name
     */
    public void setFileName(String name) {
        this.fileName = name;
    }

    /**
     *  Get fileDir
     * @return fileDir in String
     */
    public String getFileDir() {
        return fileDir;
    }

    /**
     *  Get fileName
     * @return fileName in String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *  Get tags
     * @return tags in Tag Manager
     */
    public TagManager getTags() {
        return tags;
    }

    /**
     *  Get fileType
     * @return fileType in String
     */
    public String getFileType() {
        return fileType;
    }

    /**
     *  Get the current tags
     * @return Current tags in the form of an ArrayList
     */
    public ArrayList getCurTags(){
        return this.tags.getCurTags();
    }

    /**
     *  Get the past tags
     * @return Past tags in the form of an ArrayList
     */
    public ArrayList getPastTags(){
        return this.tags.getPastTags();
    }

    /**
     * Return the entire path of the image including the directory its in, it's file name, tags and file type
     *
     * @return String the entire path of the image (directory path + name + tags + file type)
     */
    public String getName(){
        String temp = "";

        for (int i=0; i<this.getCurTags().size(); i++){
            temp += " " + this.getCurTags().get(i);
        }
        File dir = new File(this.getFileDir());
        String fileName = this.getFileName();
        Path dirPath = dir.toPath();
        Path source = dirPath.resolve(fileName);
        return source.toString() + temp + fileType;
    }

    /**
     * Return the last used name of the image
     * In the format of the entire path of the image including the directory its in, it's file name, tags and file type
     *
     * @return String the entire path of the image (directory path + name + tags + file type)
     */
    public String getPastName (){
        String temp = "";

        File dir = new File(this.getFileDir());
        String fileName = this.getFileName();
        Path dirPath = dir.toPath();
        Path source = dirPath.resolve(fileName);

        if (!this.getPastTags().isEmpty()){
            ArrayList temp2 = (ArrayList) this.getPastTags().get(this.getPastTags().size()-1);
            for (int i=0; i<temp2.size(); i++){
                temp += " " + temp2.get(i);
            }
            return source.toString() + temp + fileType;
        }else{
            return source.toString();
        }
    }

    /**
     * Return ArryaList Past names of the image
     * Add tags and file type to the image name for each name
     *
     * @return ArryaList of String contaning all Past names the image had
     */
    public ArrayList getAllPastName (){
        String temp = "";
        ArrayList names = new ArrayList();
        for (int i =0; i<this.tags.getPastTags().size(); i++){
            temp += this.fileName;
            for (int k=0; k<((ArrayList) this.tags.getPastTags().get(i)).size(); k++){
                temp += " "+((ArrayList) this.tags.getPastTags().get(i)).get(k);
            }
            names.add(temp+fileType);
            temp ="";
        }
        return names;
    }

    /**
     * Add tags to current tags and update the name of the file
     * Tags are given in the form of String seperate by space between each tag
     *
     * @param String  input String input by user of tags seperate by space between each tag
     */
    public void addTags(String input){
        tags.curAddTag(input);
        updateName(getPastName(), getName());
    }

    /**
     * Remove tags in current tags and update the name of the file
     * Tags are given in the form of String seperate by space between each tag
     *
     * @param String  input String input by user of tags seperate by space between each tag
     */
    public void removeTags (String input){
        tags.curRemoveTag(input);
        updateName(getPastName(), getName());
    }

    /**
     * Revert image name to a past name based on the String given and update name of the file
     * The given String is in format of file name + tags + file type
     *
     * @param  String pastName a past file name in the form of file name + tags + file type
     */
    public void revertToPastName (String pastName){
        String s = pastName.substring(this.fileName.length()+1,(pastName.length()-this.fileType.length()-1));
        tags.reverseTag(s);
        updateName(getPastName(), getName());
    }

    /**
     * Update the actual name to reflect the image file name and tags
     *
     *
     * @param  String pastName a past file name in the form of file name + tags + file type
     * @return  boolean true if file name change successfully, false if another file with the same name exist and file name not change
     */
    public boolean updateName (String oldName, String newName) {

        // File with old name
        File file = new File(oldName);

        // File with new name
        File file2 = new File(newName);

        // Rename file
        if (oldName.equals(newName)) {
            return false;
        } else {
            file.renameTo(file2);
            return true;
        }
    }

    /**
     * Search for similar images using google image by name and tags of the image (if file name does not contain numbers)
     *
     */
    public void searchSimilar() throws Exception{
        //Ref: https://stackoverflow.com/questions/748895/how-do-you-open-web-pages-in-java
        String base = "https://www.google.ca/search?tbm=isch&source=hp&biw=1366&bih=637&q=";
        if (this.getFileName().matches("[a-zA-Z][a-z]+")){
            base += this.getFileName() + "+";
        }
        if (!tags.getCurTags().isEmpty()){
            for (int i=0; i<tags.getCurTags().size()-1;i++){
                base+=tags.getCurTags().get(i).toString().substring(1) + "+";
            }
            base+=tags.getCurTags().get(tags.getCurTags().size()-1).toString().substring(1);
        }
        URI uri = new URI(base);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     * Overriding the Object toString method
     * Return name of the file in String (file name + tags)
     *
     * @return  name of the file + tags in String
     */
    public String toString (){

        String temp = "";

        for (int i=0; i<tags.getCurTags().size(); i++){
            temp += tags.getCurTags().get(i) +" ";
        }
        return fileName + " " + temp;
    }

}
