package src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class KeyValueSL 
{
	//====================================================================================================================
	//Calculate SHA-1 hash value for a String
	public static String SHA_1Calculate(String data) throws NoSuchAlgorithmException
	{
		MessageDigest hashResult =  MessageDigest.getInstance("SHA-1");
		hashResult.update(data.getBytes());
		byte[] hashResultByteArray = hashResult.digest();
		
		//
		String hashResultString = "";
		for(int count=0 ; count < hashResultByteArray.length ; count++)
		{
			hashResultString += Integer.toString(hashResultByteArray[count]&0xFF,16);
		}
		
		return hashResultString;
	}
	
	//====================================================================================================================
	//Calculate SHA-1 hash value for a File
	public static String SHA_1CalculateForFile(File dataFile) throws Exception
	{
		String result = "";
		try
		{
			FileInputStream instream= new FileInputStream(dataFile);
			
			byte[] buffer = new byte[2048];
			MessageDigest complete=MessageDigest.getInstance("SHA-1");
			int numRead=0;
			do
			{
				numRead=instream.read(buffer);
				if(numRead>0)
				{
					complete.update(buffer,0,numRead);
				}
			//file reading is completed, close the file
			}while (numRead != -1);
			
			instream.close();
			//
			byte[] sha1 = complete.digest();
			
			for(int count=0 ; count < sha1.length ; count++)
			{
				result += Integer.toString(sha1[count]&0xFF,16);
			}
			return result;
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	//====================================================================================================================
	//Save a file with a name in hash from [arg.] data
	public static void SaveFileWithHashKey(String data) throws Exception
	{
		//
		String hashResultString = SHA_1Calculate(data);
		
		//
		File file = new File("d://" + hashResultString + ".txt");
		try
		{
			file.createNewFile();
		}catch (IOException e1)
		{
			e1.printStackTrace();
		}
		//
		try
		{
			FileOutputStream fileOutput = new FileOutputStream(file);
			fileOutput.write(data.getBytes());
			fileOutput.close();
		}catch(FileNotFoundException enf)
		{
			enf.printStackTrace();
		}
		return;
	}
	
	//====================================================================================================================
	//Load a file named by a hash string (from specific path)
	public static void LoadFile(String PathWithHashKeyString) throws Exception
	{
		File fileName = new File(PathWithHashKeyString);
		
		try
		{
			BufferedReader buffReader=new BufferedReader(new FileReader(fileName));
			String content="init";
			while(content != null)
			{
				content=buffReader.readLine();
				//for test
				//if(content != null)
				//	System.out.println(content);
			}
			buffReader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}
	
	public static void LoadFileWithHashKey(String dirPath , String HashKeyString)
	{
		String PathWithHashKeyString=dirPath+HashKeyString;
		try
		{
			LoadFile(PathWithHashKeyString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}
	
	//====================================================================================================================
    //Sort the file list loaded from a folder, with last modified time decreasing
	public static File[] FileSort(File[] fs)
	{
		File temp;
		for(int i=0 ; i<fs.length ; i++)
		{
			for(int j=0 ; j<fs.length ; j++)
			{
				if(fs[i].lastModified() > fs[j].lastModified())
				{
					temp=fs[i];
					fs[i]=fs[j];
					fs[j]=temp;
				}
			}
		}
		return fs;
	}
	
	//Depth First Travel a folder to create a tree-blob structure
	public static void dirDepthFirstTravel(String dirPath , Tree parent)
	{
		File dir =  new File(dirPath);
		File[] fs = FileSort( dir.listFiles() );
		
		//Depth First Travel
		for(int i=0 ; i < fs.length ; i++)
		{
			if(fs[i].isFile())
			{
				try
				{
					Blob newBlobForAFile = new Blob(SHA_1CalculateForFile(fs[i]) , fs[i] , fs[i].getName());
					parent.addKid(newBlobForAFile);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			else if(fs[i].isDirectory())
			{
				Tree newTreeForAFolder = new Tree(fs[i] , fs[i].getName());
				dirDepthFirstTravel(dirPath + File.separator + fs[i].getName() , newTreeForAFolder);
				parent.addKid(newTreeForAFolder);
			}
		}
		parent.calculateValue();
		try 
		{
			parent.setKey(SHA_1Calculate(parent.getValue()));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
	
	//create a parent Tree Object base on the Path(or use the current location)
	public static Tree createKVOForAFolder(String dirPath)
	{
		File dir =  new File(dirPath);
		Tree parent= new Tree(dir,dir.getName()) ;
		dirDepthFirstTravel(dirPath,parent);
		return parent;
	}

	//====================================================================================================================	
	//Copy File
	public static void copyAll(String dirPath ,  KeyValueObject parent) throws IOException
	{
		parent.copyKVO(dirPath);
	}	
	
	//====================================================================================================================	
	//load commit
	public static Commit loadCommitInfoFromStorage(String commitName , Commit commit) throws FileNotFoundException
	{
		/*
		 * load a commit information
		 * from the storage
		 * with the name(and path) of the commit
		 */
		File commitFile = new File("D://0storage//" + commitName);

		try
		{
			BufferedReader buffReader=new BufferedReader(new FileReader(commitFile));
			
			/*
			 * Read line 1 of A commit info file
			 * which content the name of its previous commit
			 */
			String content=buffReader.readLine();
			String[] Line = content.split(" ");
			commit.setlastCommit(Line[1]);
			
			/*
			 * Read line 2 of A commit info file
			 * which content the key([1]) and name([2]) of the root it is now pointing
			 */
			content=buffReader.readLine();
			Line=content.split(" ");
			Tree parent = new Tree(Line[1],   (new File("D://STUDY//2020.9//java//fortest" + Line[2]))   ,Line[2]);
			commit.setRoot(parent);
			
			/*
			 * Read line 3 of A commit info file
			 * which content the name of this commit
			 */
			content=buffReader.readLine();
			commit.setName(content);
			
			commit.calculateValue();
			
			buffReader.close();
			return commit;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return commit;
	}
	
	//====================================================================================================================	
	//load tree
	public static Tree loadTreeInfoFromStorage(String treeKey , Tree tree) throws FileNotFoundException
	{
		File treeInfoFile = new File("D://0storage//" + treeKey);
		try
		{
			BufferedReader buffReader=new BufferedReader(new FileReader(treeInfoFile));
			
			//read the first line of a tree info file 
			String content=buffReader.readLine();
			while(content != null)
			{

				/*
				 * Read THE OTHER line of a tree info file
				 * which might represents a file(blob) or a sub-folder([sub]Tree)
				 * if the kid is a file(blob) then create a blob object and load info
				 * if the kid is a sub-folder([sub]tree) then create a tree object 
				 * and recursive call loadTreeInfoFromStorage() to Read its info
				 */
				String[] Line = content.split(" ");
				//System.out.print(Line[1]+" ");
				if(Line[1].equals("blob") )
				{
					//(new File("D://STUDY//2020.9//java//fortest" + Line[3]) 
					Blob blob = new Blob(Line[2] , null , Line[3] );
					tree.addKid(blob);
				}
				else if( Line[1].equals("tree") )
				{
					Tree subTree = new Tree(Line[2] , null , Line[3] );
					loadTreeInfoFromStorage(Line[2] , subTree);
					tree.addKid(subTree);
				}
				//this should be at the end of the loop to get next line 
				content=buffReader.readLine();
			}
			buffReader.close();
			tree.calculateValue();
			return tree;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tree;
	}
	//====================================================================================================================
	//rebuild tree-like structure
	public static Commit rebuildTreeLikeStructure(String RebuildCommitName) throws FileNotFoundException
	{
		Commit RebuildCommit = new Commit(null,RebuildCommitName,null,null);
		RebuildCommit = loadCommitInfoFromStorage(RebuildCommitName , RebuildCommit);
		
		RebuildCommit.setRoot(  loadTreeInfoFromStorage(RebuildCommit.getRoot().getKey()  ,  RebuildCommit.getRoot())  );
		return RebuildCommit;
	}
	
	//====================================================================================================================
	//recover file into original working space  
	public static void recoverFileToWorkingSpace(Tree root ,String parentPath) throws IOException
	{
		KeyValueObject kid[] = root.getKid();
		
		for(KeyValueObject kvo : kid)
		{
			if(kvo.getClass() == Blob.class)
			{
				kvo.recoverKVO(parentPath+"//" );
			}
			else //meaning that kvo.getClass() == Tree.class
			{
				kvo.recoverKVO(parentPath+"//" );
				recoverFileToWorkingSpace((Tree)kvo , parentPath+"//" + kvo.getName());
			}
		}
		
	}
	
	public static void recoverFileToWorkingSpace(Commit RebuildCommit , String wokingSpacePath) throws IOException
	{
		recoverFileToWorkingSpace(RebuildCommit.getRoot() , wokingSpacePath);
	}
	
	//just for test ~ delete file in folder=================================================================
	public static void delFolder(File folder) 
	{
		File[] workingSpaceFile = folder.listFiles();
		for(File f: workingSpaceFile)
		{
			if(f.isFile())
				f.delete();
			else if (f.isDirectory())
			{
				delFolder(f);
				f.delete();
			}
		}
	}
	
	public static void clearWorkingSpace(String workingSpacePath)
	{
		File workingSpace = new File(workingSpacePath);
		if(!workingSpace.exists())
		{
			System.out.println("The working space Path of this project is invalid!");
			return;
		}
		
		delFolder(workingSpace);
	}
	
	
	//====================================================================================================================
	//main
    public static void main(String[] args) throws IOException 
	{
    	File rootFolder = new File("D://STUDY//2020.9//java//fortest");
		Tree parent = new Tree(rootFolder , rootFolder.getName());
		dirDepthFirstTravel("D://STUDY//2020.9//java//fortest" , parent);
		
		
	    Commit newCommit = new Commit(null , "this is a commit test!" , parent , null);
	    newCommit.calculateValue();
		newCommit.setKey(newCommit.getName());
		newCommit.commitInfoStorage("D://0storage");
		
		copyAll("D://0storage" , parent );
     
        Commit RebuildCommit = rebuildTreeLikeStructure("this is a commit test!");
        recoverFileToWorkingSpace(RebuildCommit,"D://1recover");
        
        //RebuildCommit = loadCommitInfoFromStorage("this is a commit test!" , RebuildCommit);
        //System.out.print( RebuildCommit.getRoot().getKid()[0].getValue() );
        
        /*
         *  System.out.print( RebuildCommit.getRoot().getValue() );
         *  System.out.print(RebuildCommit.getValue());
         *  System.out.print("this is a test message from group 22!");
        */
        
        
        //if(n==0)
        //{
		//newCommit = new Commit(null , "this is a commit test NO2" , parent , newCommit);
	    //newCommit.calculateValue();
		//newCommit.setKey(newCommit.getName());
		//newCommit.commitInfoStorage("D://0storage//");
        //}
        //else
        // 	return;
    }
}
