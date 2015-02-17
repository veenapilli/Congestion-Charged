package dbConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.*;
/*
 * MySql conn implemented on any table. Send data in format of query.
 */
public class MysqlDbConn {
	private String sqlDvr = null, dvrMgrConn = null;
	private String usrName = null, pwd = null, db = null;
	private String qry = null, table = null, defID = "NULL";

	List<String> tableList = new ArrayList<String>();
	List<String> tableCols = new ArrayList<String>();

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	protected MysqlDbConn() {
		// TODO Auto-generated constructor stub

	}
	/*
	protected MysqlDbConn(String sqlDvr, String dvrConn, String name, String passwd ) {
		// TODO Auto-generated constructor stubtableCols.get(i)+"` ="+ param[i-1]
		if(null != sqlDvr && null != dvrConn && null != name && null != passwd){
			setSqlDvr("com.mysql.jdbc.Driver");
			setDvrMgrConn("jdbc:mysql://localhost:3306/test");
			setUsrName(name); setPwd(pwd);
		}
	}*/

	protected void setConnection() throws Exception{
		Class.forName(sqlDvr);
		connect = DriverManager
				.getConnection(dvrMgrConn, usrName, pwd);
	}
	protected ResultSet readDB() throws SQLException{
		if( null == table) System.out.println("Table Name not specified");
		if( null == connect) System.out.println("No connection to table");


		qry = "SELECT * FROM "+ table;
		try{
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery(qry);


			return resultSet;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	protected boolean insertDB(String[] param) throws SQLException{
		if( null == table){ System.out.println("Table Name not specified"); return false;}

		if( null == connect){ System.out.println("No connection to table");return false;}
		for (String s : param) {
			if(null == s)
				return false;
		} 
		getColumnsOfTable();

		int count =  tableCols.size();
		String insPart1 = "INSERT INTO `"+table+"` ("+tableCols.get(0);
		String insCols = "";
		for(int i = 1; i < count; i++)
			insCols += ", "+ tableCols.get(i);
		String insPart2 = ") VALUES ("+defID;
		for(int i = 1; i < count; i++)
			insPart2 += ", "+ param[i];
		insPart2 += ");";

		qry = insPart1 + insCols+insPart2;
		System.out.println(qry);
		try{
			statement = connect.createStatement();
			statement.executeUpdate(qry);
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	protected boolean updateDB(String[] param , String updateRow) throws Exception{
		if( null == table){ System.out.println("Table Name not specified"); return false;}

		if( null == connect){ System.out.println("No connection to table");return false;}
		for (String s : param) {
			if(null == s){
				System.out.println("PARAM ERR");
				return false;
			}
		} 
		getColumnsOfTable();

		int count =  tableCols.size();
		String insPart1 = "UPDATE `" + table + 
				"` SET `" + tableCols.get(1)+"` ="+ param[0];
		String insCols = "";
		for(int i = 1; i < count; i++){
			System.out.println(i+" "+tableCols.get(i)+" ="+ param[i-1]);
			insCols += ", `" + tableCols.get(i)+"` ="+ param[i-1];
		}
		String insPart2 = " WHERE `" + 
				tableCols.get(0)+"` ="+ updateRow+";";


		qry = insPart1 + insCols+insPart2;
		System.out.println(qry);
		try{
			statement = connect.createStatement();
			statement.executeUpdate(qry);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}

	}

	protected boolean removeDataDB(String deleteRow) throws Exception{
		if( null == table){ System.out.println("Table Name not specified"); return false;}
		if( null == deleteRow){ System.out.println("Table Name not specified");return false;}
		if( null == connect){ System.out.println("No connection to table");return false;}

		getColumnsOfTable();


		qry = "DELETE FROM "+ table+ " WHERE `"+
				tableCols.get(0)+"` = "+deleteRow ;
		System.out.println(qry);
		try{
			statement = connect.createStatement();
			statement.executeUpdate(qry);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}

	}

	protected void writeResultSet(ResultSet resultSet) throws SQLException {
		if(null != resultSet){
			int count = resultSet.getMetaData().getColumnCount();
			System.out.println("Dataset contains:");
			while (resultSet.next()) {
				for(int i = 1; i <= count; i++){
					System.out.print(resultSet.getString(i)+"\t");	
				}
				System.out.println("\n");
			}
		}else System.out.println("Result set is null");
	}


	protected void getColumnsOfTable() throws SQLException{
		// fetch columns of any specified table in a db 

		resultSet = connect.getMetaData().getColumns(db, null, table, "%");
		while(resultSet.next()){
			tableCols.add(resultSet.getString(4));
			System.out.println(resultSet.getString(4));
		}
	}

	protected void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	protected void writeMetaData() throws SQLException {
		// fetch tables in a database ("test") or all databases ("%")
		String [] types = {"TABLE"};
		resultSet = connect.getMetaData().getTables(db, null, "%", types);
		int i = 0;
		while(resultSet.next()){
			tableList.add(resultSet.getString(3));
		}
		// fetch columns of table "RandomValues"
		if(tableList.contains("RandomValues")){
			resultSet = connect.getMetaData().getColumns(db, null, table, "%");
			while(resultSet.next())
				System.out.println(resultSet.getString(4));
		}

	}

	public String getSqlDvr() {
		return sqlDvr;
	}

	public void setSqlDvr(String sqlDvr) {
		this.sqlDvr = sqlDvr;
	}

	public String getDvrMgrConn() {
		return dvrMgrConn;
	}

	public void setDvrMgrConn(String dvrMgrConn) {
		this.dvrMgrConn = dvrMgrConn;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getQry() {
		return qry;
	}

	public void setQry(String qry) {
		this.qry = qry;
	}
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

}
