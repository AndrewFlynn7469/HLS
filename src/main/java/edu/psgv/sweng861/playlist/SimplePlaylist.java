package edu.psgv.sweng861.playlist;

import java.net.URL;
import java.util.ArrayList;

import edu.psgv.sweng861.checker.FirstLineChecker;

public class SimplePlaylist extends Playlist{
	private final String type = "simple";
	private static final String extension = "#EXTINF";
	
	public SimplePlaylist(ArrayList<String> contents, URL playlistUrl){
		super();
		setContents(contents);
	}
	
	//Accept visit from first line validator
	public void accept(FirstLineChecker checker){
			checker.visit(this);
	}
	
	@Override
	public String getType(){
		return type;
	}
	
	@Override 
	public String getExtension(){
		return extension;
	}
}
