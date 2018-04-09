package edu.psgv.sweng861.checker;

import java.util.ArrayList;

import edu.psgv.sweng861.playlist.Playlist;

public class FirstLineChecker {
	private final String PLAYLIST_VALIDATION_STRING = "#EXTM3U";
	//Checks simple playlist for proper first line tag
	public void visit(Playlist playList){
		ArrayList<String> res = playList.getContents();
		if(res.get(0).equals(PLAYLIST_VALIDATION_STRING)){
			playList.setFirstLineValid(true);
		}
		else{
			playList.setFirstLineValid(false);
		}
	}
}
