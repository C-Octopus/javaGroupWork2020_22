package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class KeyValueObject 
{
	private String key;
	private File realFile;
	private String name;
	
	public KeyValueObject(String key , File realFile , String name)
	{
		this.key=key;
		this.realFile=realFile;
		this.name=name;
	}
	
	public KeyValueObject(File realFile , String name)
	{
		this.key="";
		this.realFile=realFile;
		this.name=name;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public File getRealFile()
	{
		return realFile;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setKey(String key)
	{
		this.key=key;
	}
	
	//Copy Method used to copy all file or folder to the storage
	public void copyKVO(String dirPath) throws IOException
	{
		//for a file that is not a folder(Tree object)
		//copy all of its content to a new file
		//with the name of this file's key
		
		
		//the following is the source and destination statement
		//=======================================
		//original:
		//this.realFile;
		
		//destination:
		File file = new File(dirPath + "//" + getKey());
		//=======================================
		
		
		
		//the following is the copy-and-write process 
		//=======================================
		InputStream originalFile = null;
		OutputStream copyFile = null;
		
		try
		{
			originalFile = new FileInputStream(realFile);
			copyFile = new FileOutputStream(file);        
			byte[] buf = new byte[1024];        
			int bytesRead;
	        
			while ((bytesRead = originalFile.read(buf)) != -1) 
			{
				copyFile.write(buf, 0, bytesRead);
			}
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			copyFile.close();
		}
		//=======================================
	}
	
	public void recoverKVO(String parentPath) throws IOException
	{
		//for a file that is not a folder(Tree object)
		//copy all of its content to a new file
		//with the name of this file's key
		
		
		//the following is the source and destination statement
		//=======================================
		//original:
		//this.realFile;
		File storageFile = new File("D://0storage//"+getKey());
		
		//destination:
		File destFile = new File( parentPath + getName());
		//=======================================
		
		
		
		//the following is the copy-and-write process 
		//=======================================
		InputStream inputFile = null;
		OutputStream outputFile = null;
		
		try
		{
			inputFile = new FileInputStream(storageFile);
			outputFile = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];        
			int bytesRead;
	        
			while ((bytesRead = inputFile.read(buf)) != -1) 
			{
				outputFile.write(buf, 0, bytesRead);
			}
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			outputFile.close();
		}
	}
	
}
