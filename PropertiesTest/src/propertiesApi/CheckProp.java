package propertiesApi;

public class CheckProp {
	public static void main(String[] agrs){
		AccessProp ap = new AccessProp();
		String[] keys={"abc","def","ghi"};
		String[] values={"1","2","3"};
		String comment = "Test";
		String filename = "testProp.properties";

		if(true == ap.putProp(filename, keys, values, comment)){
			ap.getProp(filename);
		}else{
			System.out.println("Encountered error");
		}
	}
}
