package propertiesApi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class AccessProp {
	private static String this_Name = " :: AccessProp";

	private Properties prop = null;
	private OutputStream out = null;
	private InputStream in = null;

	public AccessProp() {
		// TODO Auto-generated constructor stub
		prop = new Properties();
	}
	protected boolean putProp(String fileName, String [] keys, String [] values, String comment){
		if(null == comment) 
			comment = "";
		if(null == fileName || fileName.equals("")){
			System.out.println("File name NULL or Empty"+ this_Name);	

			return false;
		}
		if( null == keys || null == values){
			int len = keys.length;
			for(int i = 0; i< len; i++){
				if(null == keys[i] || null == values[i] || keys[i].equals("")){
					System.out.println("Data set has NULL or keys have Empty strings "+ this_Name);
					return false;
				}
			}
		}
		openStoreProp(fileName);
		setData(keys, values, comment);
		closeStoreFiles();
		return true;
	}

	protected void getProp(String fileName){
		openReadProp(fileName);
		displayData();
		closeReadFiles();
	}

	private void openStoreProp(String filename){
		try{
			//"dbConfig.properties"
			out = new FileOutputStream(filename);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage()+ this_Name);
		}
	}
	
	private void openReadProp(String filename){
		try{
			//"dbConfig.properties"
			in = new FileInputStream(filename);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage()+ this_Name);
		}
	}
	
	private void setData(String[] keys, String[] values, String comment){

		try{
			int numProp = keys.length;
			for(int i = 0;i < numProp; i++ ){
				prop.setProperty(keys[i], values[i]);
			}
			prop.store(out, comment);

		}catch(Exception e){
			System.out.println(e.getMessage()+ this_Name);
		}
	}

	private Enumeration<?> displayData(){ 
		/* also use as fetch data, data is being returned*/ 
		try{
			prop.load(in);
			//System.out.println(prop.getProperty("database"));
			Enumeration<?> e = prop.propertyNames();
			/* display data */
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				System.out.println("Key :" + key + ",\t Value :" + value);
			}
			return e;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage()+ this_Name);
		}
		return null;
	}
	
	private void closeStoreFiles(){
		if(null != out){
			try{
				out.close();
			}catch(IOException ioe){
				System.out.println(ioe.getMessage()+ this_Name);
			}
		}	
	}
	
	private void closeReadFiles(){
		if(null != in){
			try{
				in.close();
			}catch(IOException ioe){
				System.out.println(ioe.getMessage()+ this_Name);
			}
		}	
	}

}
