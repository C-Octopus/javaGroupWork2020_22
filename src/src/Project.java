package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Project 
{
	public String projectName;
	private Branch[] branchList;
	private int head;
	private String workingSpacePath;
	
	//constructor
	public Project(String projectName)
	{
		this.projectName =  projectName;
		branchList = new Branch[0];
	}
	
	public Project(String projectName , String workingSpacePath)
	{
		this.projectName =  projectName;
		branchList = new Branch[1];
		branchList[0] = new Branch( "master" , "theres_no_commit_in_this_branch!" );
		head=0;
		this.workingSpacePath = workingSpacePath;
	}
	
	//change working branch=======================================
	public void changeWorkingBranch(String nameOfBranchYouWantTo)
	{
		for(int i=0 ; i<branchList.length ; i++)
		{
			if(branchList[i].branchName.equals(nameOfBranchYouWantTo)) 
			{
				head=i;
				return;
			}
		}
		System.out.println("there's no branch named "+nameOfBranchYouWantTo+", please check out your input!");
	}
	
	//load project info from file==================================
	public void loadProjectInfoFromFile() throws FileNotFoundException
	{
		File projectInfoFile = new File("D://0storage//project//" + projectName);

		try
		{
			@SuppressWarnings("resource")
			BufferedReader buffReader = new BufferedReader(new FileReader(projectInfoFile));
			
			//read the 1-3 line of the project info file to get project's name and working space path
			buffReader.readLine();
			setWorkingSpacePath(buffReader.readLine());
			buffReader.readLine();
			
			//read line 4 of info file to get the head pointer
			head=Integer.parseInt(buffReader.readLine());
			
			// read THE OTHER line of a project info file
			String content = buffReader.readLine();
			while( content != null )
			{
				/*
				 * Read THE OTHER line of a project info file
				 * every line represent a branch
				 * [0] is for the name of branch , [1] is for the newest commit of branch
				 * create this branch object
				 * and then add it to the branch list
				 */
				String[] Line = content.split(" ");
				Branch br = new Branch( Line[1] , Line[2] );
				addBranch(br);
				content = buffReader.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//save project info to file==================================
	public void saveProjectInfoToFile() throws IOException
	{
		File projectInfoFile = new File("D://0storage//project//" + projectName);
		
		if(!projectInfoFile.exists())
		{
			try
			{
				projectInfoFile.createNewFile();
			}catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		//Line1 project's name
		String projectInfo = projectName;
		
		//Line2 project's working space path
		projectInfo +=  "\n" +  getWorkingSpacePath();
		

		//Line3-4 project's activated branch
		projectInfo += "\n" + "current activated branch:" ;
		projectInfo += "\n" + head;
		
		//Line5+ project's branches
		for(Branch br : branchList)
		{
			projectInfo += "\n" + "branch " + br.branchName + " " + br.getNewestCommitName();
		}
		
		try
		{
			FileOutputStream fileoutput = new FileOutputStream(projectInfoFile);
			fileoutput.write(projectInfo.getBytes());
			fileoutput.close();
		}catch(FileNotFoundException enf)
		{
			enf.printStackTrace();
		}
	}
	

	//branch/branchList related==================================
	public void addBranch(Branch br)
	{
		this.branchList = Arrays.copyOf(branchList,branchList.length+1);
		branchList[branchList.length-1]=br;
	}
	
	public void createBranch(String newBranchName)
	{
		for(Branch br : branchList)
		{
			if( br.branchName.equals(newBranchName) )
			{
				System.out.println("the branch name is already in use for a branch! Please change to another.....");
				return;
			}
		}
		
		Branch newBranch = new Branch(newBranchName , branchList[head].getNewestCommitName() );
		
		addBranch(newBranch);
	}
	
	
	public void showAllBranches()
	{
		System.out.println("Up to now there're following branches in this project :");
		for(Branch br : branchList)
			System.out.println(br.branchName);
	}
	
	
	public Branch[] getBranchList()
	{
		return branchList;
	}
	
	public void setBranchList(Branch[] brl)
	{
		branchList = brl;
	}
	
	public Branch getBranch(String branchName)
	{
		for(Branch br : branchList)
		{
			if(br.branchName.equals(branchName))
				return br;
		}
		return null;
	}
	
	public int getHead()
	{
		return head;
	}
	
	
	//working space related==================================
	public void setWorkingSpacePath(String workingSpacePath)
	{
		this.workingSpacePath=workingSpacePath;
	}
	
	public String getWorkingSpacePath()
	{
		return workingSpacePath;
	}
	
	public void delFolder(File folder) 
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
	
	public void clearWorkingSpace()
	{
		File workingSpace = new File(workingSpacePath);
		if(!workingSpace.exists())
		{
			System.out.println("The working space Path of this project is invalid!");
			return;
		}
		
		delFolder(workingSpace);
	}
}
