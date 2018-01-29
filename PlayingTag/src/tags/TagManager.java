package tags;

import java.util.ArrayList;

/**
 * TagManager
 * Contains all the current tags and past tags of an Image File
 * Can add and delete tags, reverse to a past tags
 *
 *
 * @author Gini Chin/Howard Huang
 */

public class TagManager implements java.io.Serializable{
    private ArrayList curTags = new ArrayList();
    private ArrayList pastTags= new ArrayList();

    /**
     * Constructor.
     *
     * Initialize Tag Manager with empty current and past tags for a new image
     */
    public TagManager(){
    }

    /**
     * Constructor.
     * Initializing Tag Manager for an existing image
     * Past Tag is in the form of ArrayList of an ArrayList
     *
     * @param ArrayList curTags Replace current tags with an existing ArrayList of tags
     * @param ArrayList pastTags Replace past tags with an existing ArrayList of tags
     */
    public TagManager(ArrayList curTags, ArrayList pastTags) {
        this.curTags = curTags;
        this.pastTags = pastTags;
    }

    /**
     * Set CurrentTags
     * @param ArrayList tags set current tags with an existing ArrayList of tags
     */
    public void setCurTags(ArrayList tags) {this.curTags = tags;}

    /**
     * Set PastTags (Past Tag is in the form of ArrayList of an ArrayList)
     * @param ArrayList tags set past tags with a given ArrayList of tags
     */
    public void setPastTags(ArrayList tags) {this.pastTags = tags;}

    /**
     *  Get CurTags
     * @return curTags in an ArrayList of current tags in String
     */
    public ArrayList getCurTags() {
        return curTags;
    }

    /**
     * Get PastTags
     * @return pastTags in an ArrayList of past tags in String
     */
    public ArrayList getPastTags() {
        return pastTags;
    }

    /**
     * Takes in a String of new tags that are seperated with space between each tag
     * The tags are split up based on space and @ is added to the front of each tag
     * A copy of current set of tags are added to pastTags
     * The new tags are added one by one to curTags
     *
     * @param  String tag a list of tags seperated by space between each tag *tags don't contain the symbol @
     */
    public void curAddTag(String tag) {

        ArrayList cur = (ArrayList)curTags.clone();
        String [] allTags = tag.split(" ");
        String temp ="";
        for (int i=0; i<allTags.length; i++){
            if (allTags[i]!=null && allTags[i]!=""){
                temp = "@"+allTags[i];
                if (!curTags.contains(temp)){
                    this.curTags.add(temp);
                }
            }
        }
        // Adding the previous set of tags to past tags
        this.pastTags.add(cur);
    }

    /**
     * Takes in a String of tags that are seperated with space between each tag
     * The tags are split up based on space and @ is added to the front of each tag
     * A copy of current set of tags are added to pastTags
     * If the tags exist in the current tags, then the tag is removed from current tags
     *
     * @param  String tag a list of tags seperated by space between each tag *tags don't contain the symbol @
     */
    public void curRemoveTag(String tag) {
        String [] allTags = tag.split(" ");
        String temp ="";
        boolean checker = false;
        ArrayList cur = (ArrayList)curTags.clone();
        for (int i=0; i<allTags.length; i++){
            temp = "@"+allTags[i];
            if (curTags.contains(temp)){
                checker = true;
                this.curTags.remove(temp);
                this.curTags.remove(temp + " ");
            }
        }
        if (checker){
            // Adding the previous set of tags to past tags
            this.pastTags.add(cur);
        }
    }

    /**
     * Takes in a String containing the only the tags from the selected past name of the image
     *  Clear current Tag. If the tag is not empty, the tags replace current tag
     * A copy of current set of tags are added to pastTags
     *
     * @param  String tag a list of tags seperated by space between each tag *tags do contain the symbol @
     */
    public void reverseTag(String tag){
        ArrayList cur = (ArrayList)curTags.clone();
        this.curTags.clear();
        String [] allTags = tag.split(" ");
        String temp ="";
        for (int i=0; i<allTags.length; i++){
            if (allTags[i]!=null && allTags[i].trim().length() > 0){
                this.curTags.add(allTags[i]);
            }
        }
        this.pastTags.add(cur);
    }

    /**
     * Overriding the toString method of Object
     *  Return a String listing current nad past tags on a seperate line
     *
     * @return String of current and past tags of this image
     */
    public String toString (){
        String temp = "";
        temp += "Current Tags: ";
        for (int i=0; i<curTags.size(); i++){
            temp += curTags.get(i) + ", ";
        }
        temp += "\nPast Tags: ";
        for (int i=0; i<pastTags.size(); i++){
            temp += pastTags.get(i) + ", ";
        }

        return temp;
    }
}
