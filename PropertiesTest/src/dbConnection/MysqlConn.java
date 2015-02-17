package dbConnection;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class MysqlConn{
	private static String this_Name = " :: MySqlConn";
	private Properties prop = null;
	private OutputStream out = null;
	private InputStream in = null;

	public MysqlConn() {
		// TODO Auto-generated constructor stub
		prop = new Properties();
	}

	protected void putProp(){
		openStoreProp("dbConfig.properties");
		setData();
		closeStoreFiles();
	}

	protected void getProp(){
		openReadProp("dbConfig.properties");
		getData();
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
	private void setData(){
		try{
			prop.setProperty("database", "localhost");
			prop.setProperty("user", "");
			prop.setProperty("password", "");
			prop.setProperty("driver", "com.mysql.jdbc.Driver");
			prop.setProperty("connStr", "jdbc:mysql://localhost:3306/test");
			prop.store(out, "-- data for Test table --");
		
		}catch(Exception e){
			System.out.println(e.getMessage()+ this_Name);
		}
	}

	private void getData(){
		try{
			prop.load(in);
			//System.out.println(prop.getProperty("database"));
			Enumeration<?> e = prop.propertyNames();
			
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				System.out.println("Key :" + key + ",\t Value :" + value);
			}
		}catch(IOException ioe){
			System.out.println(ioe.getMessage()+ this_Name);
		}
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
