package edu.psgv.sweng861.playlist;

import java.util.ArrayList;

import edu.psgv.sweng861.checker.FirstLineChecker;

public abstract class Playlist {
	private ArrayList<String> contents = new ArrayList<String>();
	private Boolean firstLineValid;
	//Get contents of playlist file
	public ArrayList<String> getContents(){
		return contents;
	}
	//Set contents of playlist file
	public void setContents(ArrayList<String> contents){
		this.contents = contents;
	}
	
	//Get information on first line validity
	public Boolean getFirstLineValid(){
		return firstLineValid;
	}
	
	//Set validity of first line
	public void setFirstLineValid(Boolean firstLineValid){
		this.firstLineValid = firstLineValid;
	}
	
	//Get type of playlist (simple or master)
	public abstract String getType();
	//Get Extension tag of playlist type (#EXTINF for simple or #EXT-X-STREAM-INF for master)
	public abstract String getExtension();
	
	//Accept visit 
	public abstract void accept(FirstLineChecker c);
}

