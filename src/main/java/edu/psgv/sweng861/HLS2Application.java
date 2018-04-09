package edu.psgv.sweng861;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//This is the top level module
public class HLS2Application {
	static Logger logger = LogManager.getLogger();

	//This is the entry point to the application
	public static void main(String args[]){
		logger.info("Starting HLS version 2");
		System.out.println("Starting HLS version 2");
		if(args.length == 0){
			System.out.println("No batch file specified, starting in interactive mode");
			Boolean running = true;
			Scanner scanner = new Scanner(System.in);
			do{
				System.out.println("Enter a URL to run HLS validaor or press enter 0 to quit");
				String command = scanner.next();
				if(command.charAt(0) != '0'){
					System.out.println("got command " + command);
					URL url;
					try {
						url = new URL(command);
						HLS2Runner run = new HLS2Runner(url);
						run.start();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					System.out.println("Exiting program");
					running = false;
				}
			}
			while(running);
			scanner.close();
		}
		else{
			HLS2Runner run = new HLS2Runner(args[0]);
			run.start();
		}
	}
}
	
