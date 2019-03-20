package kr.co.wmhr.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;


public class FileUploadUtil {
	public static String doFileUpload(HttpServletRequest request, FileItem fileitem, String empCode)
			throws FileNotFoundException, IOException {

			InputStream in = fileitem.getInputStream();
			String fileName = fileitem.getName().substring(fileitem.getName().lastIndexOf("\\")+1);
			String fileExt = fileName.substring(fileName.lastIndexOf("."));

			String saveFileName = empCode + fileExt;

			String path = "C:\\Tomcat 8.5\\webapps\\profile\\" + saveFileName;
			FileOutputStream fout = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
				fout.write(buffer, 0, bytesRead);

			in.close();
			fout.close();

			return "C:\\Tomcat 8.5\\webapps\\profile\\" + saveFileName; 
		}
}
