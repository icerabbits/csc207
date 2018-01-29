package manageFiles;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

/**
 * ImageManager
 * Contains ArrayList of image files and all available tags used across all images
 * Can add to all available tags and add and remove image files
 *
 * @author Gini Chin/Howard Huang
 */

public class ImageManager implements java.io.Serializable{

    public ArrayList <ImageFile> allImage = new ArrayList <ImageFile> ();
    private  ArrayList allAvailableTags = new ArrayList();

    /**
     * Constructor.
     *
     * Initialize Image Manager with the start of the program
     */
    public ImageManager(){
    }

    /**
     *  Get All Available Tags
     * @return allAvailableTags in ArrayList of String containing all the available tags ever entered/found
     */
    public ArrayList getAllAvailableTags() {
        return allAvailableTags;
    }

    /**
     * Add a new image file to the image manager
     * Check if image contains tags that are not in all available tags, if so add it in
     *
     * @param ImageFile image  new image file to be add into ArrayList of imageFiles
     */
    public void addImageFile(ImageFile image){
        allImage.add(image);
        ArrayList temp = (ArrayList)image.getTags().getCurTags().clone();

        if (temp.size()>0){
            String tag ="";
            String tags = "";
            for(int i=0; i<temp.size();i++){
                tag = (String) temp.get(i);
                tags += tag.substring(1) + " ";
            }
            this.addToAllAvailableTags(tags);
        }
    }

    /**
     * Remove a specified image file
     *
     * @param ImageFile image remove a given image file from the ArrayList of image Files
     */
    public void removeImageFile (ImageFile image){
        allImage.remove(image);
    }

    /**
     * Add tags to all available tags, tags are seperated by space
     * if it has not already been added
     *
     * @param String tag of tags to be added in String
     */
    public void addToAllAvailableTags (String tag){
        String [] allTags = tag.split(" ");
        String temp ="";
        for (int i=0; i<allTags.length; i++){
            if (!allAvailableTags.contains(allTags[i])){
                allAvailableTags.add(allTags[i]);
            }else{
                int k = allAvailableTags.indexOf(allTags[i]);
                for (int j=k-1; j>=0; j--){
                    Collections.swap(allAvailableTags, j+1, j);
                }
            }
        }
    }

    /**
     * Check if an image file already exist in the image manager
     * if the name of the file is the same then the image file already exist
     *
     * @param String name name of the image file being checked
     * @return int return the indx if it exist or else return -1
     */
    public int exist (String name){
        for (int i=0 ;i<allImage.size(); i++){
            if (name.equals(allImage.get(i).getName())){
                return i;
            }
        }
        return -1;
    }

    /**
     * Return file name + tag of all image files in Image Manager seperate by line
     *
     * @return String of name of image file seperate by line
     */
    public String toString (){
        String temp ="";
        for (int i=0; i<allImage.size(); i++){
            temp += (i+1) + ". " + allImage.get(i).toString() + "\n";
        }
        return temp;
    }
}
