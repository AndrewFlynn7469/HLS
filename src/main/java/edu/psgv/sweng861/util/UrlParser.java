package edu.psgv.sweng861.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UrlParser {
	//This utility converts strings into URL
	private Logger logger = LogManager.getFormatterLogger(getClass());
	
	public URL parseUrl(String urlString) throws MalformedURLException{
		//This parses a String to URL
		logger.info("Parsing URL " + urlString);
		URL url = new URL(urlString);
		return url;
	}
	
	public ArrayList<URL> parseUrl(ArrayList<String> urlArray){
		//This parses a list of Strings into URLs
		ArrayList<URL> urls = new ArrayList<URL>();
		for(String urlString : urlArray){
			try{
				urls.add(parseUrl(urlString));
			}
			catch(MalformedURLException e){
				System.out.println("Could not parse URL from input: " + urlString + " : " + e.getClass() + ": " + e.getMessage());
				logger.error("Could not parse URL from input: " + urlString + " : " + e.getClass() + ": " + e.getMessage());
			}
		}
		return urls;
	}
}
