package dbConnection;

public class TestDbProp {
	public static void main(String[] args){
		MysqlConn msc = new MysqlConn();
		msc.putProp();
		msc.getProp();
	}
	
}
