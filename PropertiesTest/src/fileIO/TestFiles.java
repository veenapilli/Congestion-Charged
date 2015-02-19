package fileIO;
import java.nio.file.*;
public class TestFiles {
	public static void main(String []args){
		AccessFiles af = new AccessFiles();
		Path filePath = null;
		
		filePath = Paths.get("src/fileIO/InputUTF.txt");
		System.out.println(af.buffReadPath(filePath));
	
		filePath = Paths.get("src/fileIO/OutputUTF.txt");
		af.buffWritePath(filePath);
	}
}
