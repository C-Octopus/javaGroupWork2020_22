package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserHandle 
{
	//================================================================================
	//initial without user command
	public static void worldInitial()
	{
		//1.CHECK STORAGE DIR
		File worldStorage = new File("D://0storage");
		if(!worldStorage.exists())
		{
			worldStorage.mkdir();
		}

		//2.CHECK CONFIG INI
		File worldINI =  new File("D://0storage//world.execute(me).config.group22.ini");
		if(!worldINI.exists())
		{
			try
			{
				worldINI.createNewFile();
			    FileOutputStream output = new FileOutputStream ( worldINI , true ) ; 
			    String str = "-1" ; 
			    output.write(str.getBytes()) ;   
			    output.close ();  
			}catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		//3.CHECK PROJECT DIR
		File worldProject = new File("D://0storage//project");
		if(!worldProject.exists())
		{
				worldProject.mkdir();
		}
	}
	

	//================================================================================
	//user command: world .view.project
	public static void viewAllProjecs()
	{
		
		File worldINI = new File("D://0storage//world.execute(me).config.group22.ini");

		try
		{
			BufferedReader buffReader = new BufferedReader(new FileReader(worldINI));
			
			//Read the first line
			buffReader.readLine();
			
			// read THE OTHER line of a project info file
			String content=buffReader.readLine();
			while( content != null )
			{
				int i=1;
				/*
				 * Read THE OTHER line of a project info file
				 * every line represent a project
				 */
				System.out.println("Project " + i + " named:"+content);
				content=buffReader.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

	//================================================================================
	//user command: world .create
	public static void createProject(String projectName ,String workingPath) throws Exception
	{
		Project project = new Project(projectName ,workingPath);
		project.saveProjectInfoToFile();
		
		File worldINI =  new File("D://0storage//world.execute(me).config.group22.ini");
	    FileOutputStream output = new FileOutputStream ( worldINI , true ) ; 
	    String str = "\n" + projectName ; 
	    output.write(str.getBytes()) ;   
	    output.close ();  
	}

	//================================================================================
	//user command: world .open
	public static Project openAProject(String projectName) throws FileNotFoundException
	{
		Project project = new Project(projectName , null);
		project.loadProjectInfoFromFile();
		return project;
	}
	

	//================================================================================
	//user command: world .view.branch
	public static void viewAllBranchesInProject(Project project)
	{
		System.out.println("Current Project: " + project.projectName);
		project.showAllBranches();
	}


	//================================================================================
	//user command: world .view.commit
	public static void viewAllCommitLog(Project project , String branchName) throws FileNotFoundException
	{
		System.out.println("Current Project: " + project.projectName);
		
		Branch branch = project.getBranch(branchName);
		System.out.println("Current Branch: " + branch.branchName);
		
		//if there��s no commit in this branch (i.e: an initial master branch)
		if(branch.getNewestCommitName().equals("theres_no_commit_in_this_branch!"))
		{
			System.out.println("there's no commit in this branch!");
			return;
		}
		
		System.out.println("The commits in current branch are:");
		
		//show the newest commit for this branch
		Commit commit = new Commit(null,null,null,null);
		commit = KeyValueSL.loadCommitInfoFromStorage(branch.getNewestCommitName() , commit);
		System.out.println(commit.getName());
		
		//show the old commit for this branch
		while(true)
		{
			//there's no more last commit!(this is the oldest one!)
			if(commit.getlastCommit().equals("null"))
				break;
			commit = KeyValueSL.loadCommitInfoFromStorage(commit.getlastCommit() , commit);
			System.out.println(commit.getName());
		}
	}

	//================================================================================
	//user command: world .newbranch
	public static void createBranch(Project project , String branchName) throws IOException
	{
		Branch branch = new Branch(branchName , project.getBranchList()[project.getHead()].getNewestCommitName());
		project.addBranch(branch);
		project.saveProjectInfoToFile();
	}
	

	//================================================================================
	//user command: world .changebranch
	public static void changeCurrentBranch(Project project , String nameOfBranchYouWantTo) throws Exception
	{
		/*
		 * there're several things to do during the changing branch
		 * 1.change project's head to a new branch N.O.
		 * 2.change project's working space,
		 * which replace the files in working space with the new branch's files
		 */

		//finishing mission 1
		project.changeWorkingBranch(nameOfBranchYouWantTo);
		
		//finishing mission 2
		project.clearWorkingSpace();
		
		String newestCommitName = project.getBranch(nameOfBranchYouWantTo).getNewestCommitName();
		
		Commit newestCommit  = KeyValueSL.rebuildTreeLikeStructure(newestCommitName);
		
		KeyValueSL.recoverFileToWorkingSpace(newestCommit , project.getWorkingSpacePath());
		
		project.saveProjectInfoToFile();
	}
	
	
	public static void setWorkingSpace(String branchName)
	{
		System.out.println("please type the new working space path below; type \"cancel\" to cancel");
		Scanner input = new Scanner(System.in);
		String inPath = input.next();
		
		if(inPath.equals("cancel"))
			return;
	}
	
	
	public static void changedDefaultStorageSpace(String branchName)
	{
		
	}
	
	//user command: world .commit
	public static void executeCommit(Project project , String commitName) throws IOException
	{
		/*
		 * there're several things to do during the executing commit
		 * 1.copy the changed file from working space to storage
		 * 2.change branch's newest commit to the new one
		 */
		
		Tree parent =  KeyValueSL.createKVOForAFolder(project.getWorkingSpacePath());

		//finishing mission 2
		String lastCommit="";
		
		
		//System.out.println(project.getBranchList()[ project.getHead() ].getNewestCommitName());
		//System.out.println(project.getBranchList()[ project.getHead() ].branchName );
		
		if(  project.getBranchList()[ project.getHead() ].getNewestCommitName().equals("theres_no_commit_in_this_branch!"))
		{
			lastCommit=null;
		}
		else
		{
			lastCommit=project.getBranchList()[ project.getHead() ].getNewestCommitName();
		}

	    Commit newCommit = new Commit(null , commitName , parent , lastCommit);
	    newCommit.calculateValue();
		newCommit.setKey(newCommit.getName());
		newCommit.commitInfoStorage("D://0storage");
		
		KeyValueSL.copyAll("D://0storage" , parent);
		
		//finishing mission 1
		project.getBranchList()[ project.getHead() ].setNewestCommitName(commitName);
		project.saveProjectInfoToFile();
	}
	
	//user command: world .rollback
	public static void executeRollback(Project project , String commitName) throws Exception
	{
		/*
		 * there're several things to do during the rolling back
		 * 1.delete the existing file in working space
		 * 2.recover the files according to the commitName from storage
		 */
		
		//finishing mission 1
		project.clearWorkingSpace();
		
		//finishing mission 2
		Commit rebuildCommit = KeyValueSL.rebuildTreeLikeStructure(commitName);
        KeyValueSL.recoverFileToWorkingSpace(rebuildCommit,project.getWorkingSpacePath());
	}
	
	
	public static void executeRollback_hard(String commitName)
	{
		
	}
}