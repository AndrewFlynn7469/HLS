package edu.psgv.sweng861.playlist;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaylistFactory {
	private static Logger logger = LogManager.getFormatterLogger(PlaylistFactory.class);
	private static final String MASTER_EXT = "#EXT-X-STREAM-INF";
	private static final String SIMPLE_EXT = "#EXTINF";
	
	//Reads the playlist tag extension and determines if the playlist is master or simple
	public static Playlist getPlaylist(ArrayList<String> content, URL playlistUrl) throws IOException{
		String extension;
		try{
			extension = getExtension(content);
		}
		catch(IOException e){
			logger.error("Could not construct playlist object: " + e.getClass() + ": " + e.getMessage());
			throw e;
		}
		
		if(extension == MASTER_EXT){
			return new MasterPlaylist(content, playlistUrl);
		}
		else if(extension == SIMPLE_EXT){
			return new SimplePlaylist(content, playlistUrl);
		}
		else{
			throw new IOException("Could not construct playlist (unknown extension)");
		}
	}
	
	private static String getExtension(ArrayList<String> content) throws IOException{
		for(String line : content){
			if(line.length() > 0 && line.charAt(0) == '#'){
				String [] parts = line.split(":");
				if (parts[0] == MASTER_EXT || parts[0] == SIMPLE_EXT){
					return parts[0];
				}
			}
		}
		throw new IOException("Invalid playlist file.");
	}
}
