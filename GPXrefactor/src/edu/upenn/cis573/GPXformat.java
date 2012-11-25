package edu.upenn.cis573;

/**
 * Represents the result of running the GPXchecker.checkFormat method.
 */

import java.util.ArrayList;

public class GPXformat {

    // indicates whether or not the format is valid
    private boolean v = false;
    // any error message that is to be shown
    private String m;
    // set of XML tags
    private ArrayList<String> t;
    
    public GPXformat() { }

    /* Accessors */
    public boolean isValid() { return v; }
    public String message() { return m; }
    public ArrayList<String> tags() { return t; }

    /* Mutators */
    public void setValid(boolean valid) { v = valid; }
    public void setMessage(String message) { m = message; }
    public void setTags(ArrayList<String> tags) { t = tags; }
}