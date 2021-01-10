package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Tree extends KeyValueObject
{
	private KeyValueObject[] kid;
	private String value;
	
	public Tree(File realFile , String name)
	{
		super(realFile,name);
		this.kid=new KeyValueObject[0];
		this.value="";
	}
	
	public Tree(String key , File realFile , String name)
	{
		super(key,realFile,name);
		this.kid=new KeyValueObject[0];
		this.value="";
	}
	
	public void addKid(KeyValueObject KVO)
	{
		//KeyValueObject[] newKid	= new KeyValueObject[kid.length+1];
		this.kid = Arrays.copyOf(kid,kid.length+1);
		kid[kid.length-1]=KVO;
	}
	
	//
	public void calculateValue()
	{
		value += "name " +  getName();
		for(KeyValueObject kvo : kid)
		{
			if(kvo.getClass() == Blob.class)
			{
				value +=  "\n" + "100644 blob " + kvo.getKey() + " " + kvo.getName();
			}
			else
			{
				value +=  "\n" + "040000 tree " + kvo.getKey() + " " + kvo.getName();
			}
		}
		return;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public KeyValueObject[] getKid()
	{
		return kid;
	}
	
	//
	public void copyKVO(String dirPath) throws IOException
	{
		//for a folder
		//copy the folder¡®s value to a new text file
		//with the name of folder's key
		File file = new File(dirPath+ "//" + getKey());
		try
		{
			file.createNewFile();
		}catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			FileOutputStream copyFolderInfoFile = new FileOutputStream(file);
			copyFolderInfoFile.write(this.getValue().getBytes());
			copyFolderInfoFile.close();
		}catch(FileNotFoundException enf)
		{
			enf.printStackTrace();
		}
		
		//when the above action is done
		//try to check out the files or sub-folder inside this folder,
		//which is a recursion process
		KeyValueObject[] KVOList = getKid();
		
		for(KeyValueObject kvo : KVOList)
		{
				kvo.copyKVO(dirPath);
		}
		return;
	}
	
	
	public void recoverKVO(String parentPath) throws IOException
	{
		//for a folder
		//copy the folder¡®s value to a new text file
		//with the name of folder's key
		File destFile = new File(parentPath + getName());
		
		destFile.mkdir();
		return;
	}
}
