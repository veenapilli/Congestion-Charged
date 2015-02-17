package dbConnection;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Properties;

import propertiesApi.AccessProp;
public class TestDbProp {
	public static void main(String[] args){
		//		MysqlConn msc = new MysqlConn();
		//		msc.putProp();
		//		msc.getProp();
		AccessProp apSql = new AccessProp();
		String filename = "mysqlTestConn.properties";
		Properties data = null;
		MysqlDbConn dbSql = new MysqlDbConn();

		/* 
		 * set values of DB and tables used here... 
		 * 
		 */
		/*
		String[] keys = {"dvr","mgr","usr","pwd", "db","tab"};
		String[] values = {"com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test","","","test","RandomValues"};
		String filename = "mysqlTestConn.properties";
		String comments = "connection parameters to test DB RandomValues table";
		apSql.putProp(filename, keys, values, comments);*/

		/*
		 * fetch values from properties file and set access to DB
		 */
		data = apSql.getProp(filename);
		ResultSet rs = null;
		if(null != data){
			System.out.println("Reading data");
			Enumeration<?> e = data.propertyNames();
			while(e.hasMoreElements()){
				String key = (String) e.nextElement();
				String value = data.getProperty(key);
				System.out.println(key+"-"+value);
				if(key.equals("dvr")){
					dbSql.setSqlDvr(value);
					System.out.println("Setting dvr");
				}
				else if(key.equals("mgr"))
					dbSql.setDvrMgrConn(value);
				else if(key.equals("usr"))
					dbSql.setUsrName(value);
				else if(key.equals("pwd"))
					dbSql.setPwd(value);
				else if(key.equals("db"))
					dbSql.setDb(value);
				else if(key.equals("tab"))
					dbSql.setTable(value);
			}
			try {
				System.out.println("Reading DB");
				System.out.println(dbSql.getDb()+" "+dbSql.getTable()+" "+
				dbSql.getDvrMgrConn()+" "+dbSql.getSqlDvr()+" "+
						dbSql.getUsrName()+" "+dbSql.getPwd());
				dbSql.setConnection();
				rs = dbSql.readDB();
				if(null != rs) dbSql.writeResultSet(rs);
				
				System.out.println("INSERT DB:");
				String[] insdata = new String[5];
				insdata[0] = "NULL";
				insdata[1] = "\"poivot123\"";
				insdata[2] = "\"dgh\"";
				insdata[3] = "\"e\"";
				insdata[4] = "'2014-02-02'";

				/*dbSql.insertDB(insdata);
				rs = dbSql.readDB();
				if(null!=rs)dbSql.writeResultSet(rs);*/

				System.out.println("UPDATE DB:");
				String[] upddata = new String[4];
				upddata[0] = "\"poi\"";
				upddata[1] = "\"dgh\"";
				upddata[2] = "\"e\"";
				upddata[3] = "'2014-02-02'";

				if(true == dbSql.updateDB(upddata, "3"))
					System.out.println("UPDATE SUCCESS");
				rs = dbSql.readDB();
				if(null!=rs)dbSql.writeResultSet(rs);

				System.out.println("DELETE DATA IN DB:");
				if(true == dbSql.removeDataDB("8"))
					System.out.println("DELETE SUCCESS");
				rs = dbSql.readDB();
				if(null!=rs)dbSql.writeResultSet(rs);

				System.out.println("METEDATA DB:");
				dbSql.writeMetaData();
				dbSql.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else{
			System.out.println("data is null");
		}

	}

}
