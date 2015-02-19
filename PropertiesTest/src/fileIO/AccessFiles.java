package fileIO;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AccessFiles {
	protected String buffReadPath(Path file){
		try{
			/*
			 * Default test file creation: UTF-8			
			 */
			Charset ch = Charset.forName("UTF-8");
			/*
			 * Charset ch = Charset.forName("US-ASCII");
			 */
			BufferedReader rd = Files.newBufferedReader(file, ch);
			String data = "";
			StringBuilder allData = new StringBuilder();
			if(null != rd){
				while(null != (data = rd.readLine())){
					allData.append(data+"\n");
				}
				return allData.toString();
			}else{
				return "**Failed: Reader is NULL";
			}
		}catch(Exception ex){
			return "**Failed: "+ex.getMessage();
		}
	}


	protected void buffWritePath(Path file){
		/*
		 * Creates Output file if it does not exist
		 * NON-APPEND
		 */
		Charset ch = Charset.forName("UTF-8");
		String data= "abc \ndef";
		try(BufferedWriter bw = Files.newBufferedWriter(file, ch)){
			bw.write(data,0,data.length());
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	protected boolean createFile(Path file){
		try{
			Files.createFile(file);
			return true;
		}catch(FileAlreadyExistsException ex){
			System.err.format("File %s exists", file);
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			System.err.format("Error creating %s", file);
			return false;
		}
	}
}
