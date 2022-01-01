package tools;

import java.io.FileInputStream;
import java.io.InputStream;

/*
 * Extra methods to load things
 */

public class MinimHelper {
	public String sketchPath( String fileName ) {
        return "audios/"+fileName;
    }

    public InputStream createInput(String fileName) {
        InputStream is = null;
        try{
            is = new FileInputStream(sketchPath(fileName));
        }
        catch(Exception e){
//            System.out.println(e.toString());
        }
        return is;
    }
}
