package utilities;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class DeleteFiles {
	public static void main(String[] args) {
		//DeleteFiles.DeleteFiles("C:\\Users\\antof\\eclipse-workspace\\AutomationPractice\\recordings");
		DeleteFiles.DeleteFilesPrimero("C:\\Users\\antof\\eclipse-workspace\\AutomationPractice\\Evidencias");
	}
/*	public static void DeleteFiles(String ubicacion) {
        File file = new File(ubicacion);
        long diff = new Date().getTime() - file.lastModified();
        	if (diff >= 0 * 24 * 60 * 60 * 1000) {
        		DeleteFilesPrimero(file);
        	}
	}*/

    private static void DeleteFiles(File file) {
        if (file.isDirectory()) {
        	System.out.println(file.getName()+" "+file.length());
            for (File f : file.listFiles()) {
            	
            	long diff = new Date().getTime() - file.lastModified();
                if (diff >= 3 * 24 * 60 * 60 * 1000) {
                	 DeleteFiles(f);
                }
            }
            file.delete();
            System.out.println("sali del for");
           }
        else file.delete();
    }
    private static void DeleteFilesPrimero(String ubicacion) {
    	File file = new File(ubicacion);
        if (file.isDirectory()) {
        	long diff = new Date().getTime() - file.lastModified();
        	File[] list = file.listFiles();
            if ((diff >= 3 * 24 * 60 * 60 * 1000) && list!=null && list.length>1) {
                for (int i=0; i<list.length-1;i++) {
                	File f = list[i];
                	DeleteFiles(f);
            }
            }
           }
        else file.delete();
    }


}
