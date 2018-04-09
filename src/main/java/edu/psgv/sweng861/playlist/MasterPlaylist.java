package edu.psgv.sweng861.playlist;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.psgv.sweng861.checker.FirstLineChecker;
import edu.psgv.sweng861.util.WebRequestUtil;

public class MasterPlaylist extends Playlist{
	private Logger logger = LogManager.getFormatterLogger(getClass());
	private final String type = "Master";
	private final String extension = "#EXT-X-STREAM-INF";
	private URL playlistUrl;
	private ArrayList<SimplePlaylist> playlists;
	
	//Constructor for master playlist, sets base URL for simple playlists and appends them to the list of playlists.
	public MasterPlaylist(ArrayList<String> contents, URL playlistUrl){
		try{
			initPlaylistUrl(playlistUrl);
		}
		catch(MalformedURLException e){
			logger.debug("Could not get playlist URL for " + playlistUrl + ": " + e.getClass() + ": " + e.getMessage());
		}
		setContents(contents);
	}
	
	//Accept visit from first line validator
	public void accept(FirstLineChecker checker){
		checker.visit(this);
	}
	//Sets base URL for finding simple playlists
	private void initPlaylistUrl(URL playlistUrl) throws MalformedURLException{
		String [] parts = playlistUrl.toString().split("/");
		String url = "";
		for(int i = 0; i < (parts.length - 1); i++){
			url += parts[i];
		}
		this.playlistUrl = new URL(url);
	}
	
	//Sorts through master playlist contents and finds the relative URLS of the simple playlists
	public void setPlaylists(ArrayList<String> contents){
		for(int i = 0; i < contents.size(); i++){
			String line = contents.get(i);
			if(line.charAt(0) == '#'){
				String [] parts = line.split(":");
				if (parts[0].equals(extension)){
					appendSimplePlaylist(contents.get(i+1));
				}
			}
		}
	}
	
	//Uses the relative URL of a simple playlist to initialize a simple playlist and append to the master's list
	private void appendSimplePlaylist(String relUrl){
		URL simpleUrl;
		WebRequestUtil requestUtil = new WebRequestUtil();
		ArrayList<String> res;
		try{
			simpleUrl = new URL(playlistUrl +relUrl);
		}
		catch(MalformedURLException e){
			logger.debug("Could not get simpole playlist URL for " + relUrl + ": " + e.getClass() + ": " + e.getMessage());
			return;
		}
		try{
			res = requestUtil.getPageContents(simpleUrl);
		}
		catch(Exception e){
			logger.debug("Could not get page contents for " + simpleUrl + ": " + e.getClass() + ": " + e.getMessage());
			return;
		}
		playlists.add(new SimplePlaylist(res, simpleUrl));
	}
	
	//Gets the base URL of the master playlist
	public URL getPlaylistUrl(){
		return playlistUrl;
	}
	
	//Gets playlist type
	@Override
	public String getType(){
		return type;
	}
	
	//Gets extension tag
	@Override 
	public String getExtension(){
		return extension;
	}
	
	//Gets simple playlists that belong to master
	public ArrayList<SimplePlaylist> getPlaylists(){
		return playlists;
	}
}
