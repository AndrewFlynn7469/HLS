package edu.psgv.sweng861.util;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;

public class BatchReader {
	//This module is to read a batch file
	private Logger logger = LogManager.getFormatterLogger(getClass());
	private BufferedReader bufferedReader;
	
	public BatchReader(String fileName) throws FileNotFoundException{
		//This is a constructor for the batch reader
		logger.info("Constructing batch reader utility");
		bufferedReader = new BufferedReader(new FileReader(fileName));
	}

	public ArrayList<String> getContents(){
		//This gets the contents of the file from the batch reader
		ArrayList<String> contents = new ArrayList<String>();
		String line;
		try{
			while((line = bufferedReader.readLine()) != null){
				contents.add(line);
			}
		}
		catch(IOException e){
			logger.debug("Could not read contents of batch file: " + e.getClass() + ": " + e.getMessage());
		}
		return contents;
	}
}
