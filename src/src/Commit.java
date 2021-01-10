package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Commit extends KeyValueObject 
{
	//public String commitDate;
	private String lastCommitName;
	private Tree root;
	private String value;
	
	//constructor
	public Commit(File realFile , String name , Tree root, String lastCommitName)
	{
		/*
		 * QAQ
		 * realFile is never used since the data structure design is changed
		 * use null in constructor QAQ
		 * */
		super(realFile,name);
		this.root=root;
		this.value="";
		this.lastCommitName=lastCommitName;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public Tree getRoot()
	{
		return root;
	}
	
	public String getlastCommit()
	{
		return lastCommitName;
	}
	
	public void setRoot(Tree root)
	{
		this.root=root;
	}
	
	public void setlastCommit(String lastCommitName)
	{
		this.lastCommitName=lastCommitName;
	}
	
	public void calculateValue()
	{
		value = value + "lastCommit " + lastCommitName;
		value = value + "\n" + "tree " + root.getKey() + " " + root.getName();
		value = value + "\n" + this.getName();
		return;
	}
	
	
	public void commitInfoStorage(String dirPath) throws IOException
	{
		//=======================================
		
		//the following code will create a new commit file
		//to record the info of a commit
		//with the name of its hash key
		//and its value of ???
		//=======================================
		File commitFile = new File("D://0storage//" + getKey());
		
		try
		{
			commitFile.createNewFile();
		}catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		try
		{
			FileOutputStream fileOutput = new FileOutputStream(commitFile);
			fileOutput.write(this.getValue().getBytes());
			fileOutput.close();
		}catch(FileNotFoundException enf)
		{
			enf.printStackTrace();
		}
	}
}
