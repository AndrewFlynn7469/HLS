package edu.psgv.sweng861;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.psgv.sweng861.checker.FirstLineChecker;
import edu.psgv.sweng861.playlist.Playlist;
import edu.psgv.sweng861.playlist.PlaylistFactory;
import edu.psgv.sweng861.util.*;
//C:\Users\Andrew\workspace\HLS\playlist.txt
public class HLS2Runner {
	private final Logger logger = LogManager.getFormatterLogger(getClass());
	private final String PLAYLIST_VALIDATION_STRING = "#EXTM3U";
	
	BatchReader batchReader;
	UrlParser urlParser;
	WebRequestUtil requestUtil;
	String batchFile;
	ArrayList<URL> batchUrls;

	//Initialization pulls in batch file from command line args
	public HLS2Runner(String batchFile){
		this.batchFile = batchFile;
	}
	
	//Start a run from interactive mode url
	public HLS2Runner(URL playlistUrl){
		batchUrls = new ArrayList<URL>();
		batchUrls.add(playlistUrl);
	}
	
	//Application layer of HLS1 reads contents from batch file, attempts to convert these to URLs, makes a GET request on them, prints contents and verifies first line
	public void start(){
		if(batchFile != null){
			try{
				batchReader = new BatchReader(batchFile);
			}
			catch(FileNotFoundException e){
				logger.error("Could not find batch file: " + e.getClass() + ": " + e.getMessage());
				System.exit(0);
			}
			
			ArrayList<String> batchContents = batchReader.getContents();
			urlParser = new UrlParser();
			batchUrls = urlParser.parseUrl(batchContents);
			logger.info("Playlist URLs: " + batchUrls);
			System.out.println("Playlist URLs: " + batchUrls);
		}
		requestUtil = new WebRequestUtil();
		for(URL url : batchUrls){
			try{
				System.out.println("Performing GET request for " + url);
				
				ArrayList<String> res = requestUtil.getPageContents(url);
				Playlist playlist = PlaylistFactory.getPlaylist(res, url);
				FirstLineChecker firstCheck = new FirstLineChecker();
				
				if("Master".equals(playlist.getType())){
					System.out.println("Master playlist detected");
				}
				else{
					System.out.println("Simple playlist detected");
				}
				
				if(res.isEmpty() ){
					System.out.println("Could not retrieve content for " + url);
					logger.debug("No content returned from " + url);
				}
				playlist.accept(firstCheck);
				if(playlist.getFirstLineValid()){
					System.out.println("Playlist starts with " + PLAYLIST_VALIDATION_STRING);
					logger.info("Playlist starts with " + PLAYLIST_VALIDATION_STRING);
				}
				else{
					System.out.println("Playlist has an invalid first line: " + res.get(0));
					logger.debug("Playlist has an invalid first line: " + res.get(0));
				}
			}
			catch(UnknownHostException e){
				System.out.println("Could not get contents for " + url + " (404): " + e.getClass() + ": " + e.getMessage());
				logger.debug("Could not get contents for " + url + " (404): " + e.getClass() + ": " + e.getMessage());
			}
			catch(IOException e){
				logger.debug("Failed to get response from URL " + url + " : " + e.getClass() + ": " + e.getMessage());
			}
		}
	}
}
