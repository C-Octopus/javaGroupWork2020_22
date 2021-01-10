package src;

import java.util.Scanner;

public class world 
{
	   public static void main(String[] args) throws Exception
	   {
		   //initialized
		   UserHandle.worldInitial();
		   
		   String command = ".view.commit" ;
		   
		   if(command.equals(".create") )
		   {
			   System.out.println("Welcome to create project guide!!");
			   System.out.println("Please enter your project's name, or you may use \"cancel\" to cancel this creating process.");
			   
			   Scanner input = new Scanner(System.in);
			   String projectName=input.next();
			   if(projectName.equals("cancel"))
			   {
				   System.out.println("See you next time, QAQ.");
				   return;
			   }
			   
			   System.out.println("Please enter your project's working space, using \"d\" for a default setting that using current path.");
			   System.out.println("You can also use \"cancel\" to cancel this creating process.");
			   
			   String workingSpacePath=input.next();
			   if(workingSpacePath.equals("cancel"))
			   {
				   System.out.println("See you next time, QAQ.");
				   return;
			   }
			   else if (workingSpacePath.equals("d"))
			   {
				   
			   }
			   //else if DirectoryExists(projectName)
			   //{
			   //   
			   //}
			   
			   UserHandle.createProject(projectName, workingSpacePath);
			   System.out.println("Your new project named \"" +  projectName +"\" has been sucessfully created! ( - ¨Œ -)]¡õ");
		   }
		   
		   
		   if(command.equals(".commit") )
		   {
			   System.out.println("You're trying to launch a commit process! ¡Æ(- ¡õ -)!!!");
			   System.out.println("Please enter the name of this commit, or you may use \\\"cancel\\\" to cancel this creating process.");
			   @SuppressWarnings("resource")
			   Scanner input = new Scanner(System.in);
			   String commitName=input.next();
			   if(commitName.equals("cancel"))
			   {
				   System.out.println("See you next time, TVT.");
				   return;
			   }
			   
			   Project project =  new Project("luoxukun");
			   
			   project.loadProjectInfoFromFile();
			   
			   UserHandle.executeCommit(project , commitName);
			   System.out.println("Your new commit named \"" +  commitName +"\" has been sucessfully finished! ( - ¨Œ -)]¡õ");
		   }
		   
		   
		   if(command.equals(".view.project"))
		   {
			   UserHandle.viewAllProjecs();
		   }
		   
		   if(command.equals(".view.branch"))
		   {
			   UserHandle.viewAllBranchesInProject();
		   }
		   
		   if(command.equals(".view.commit"))
		   {
			   UserHandle.viewAllCommitLog();
		   }
		   
		   
		   if(command.equals(".open"))
		   {
			   UserHandle.viewAllProjecs();
			   System.out.println("please select a project you wish to open");
			   System.out.println("Enter the name of a project,  or you may use \"cancel\" to cancel this creating process.");
			   
			   @SuppressWarnings("resource")
			   Scanner input = new Scanner(System.in);
			   String projectName=input.next();
			   if(projectName.equals("cancel"))
			   {
				   System.out.println("See you next time,  A W A ");
				   return;
			   }
			   
			   UserHandle.openAProject(projectName);
		   }
		   
		   
		   if(command.equals(".rollback"))
		   {
			   System.out.println("WARNING: You're trying to launch a rolling back process! ¡Æ(; ¡õ ;)|||  " );
			   
			   Project project =  new Project("luoxukun");
			   project.loadProjectInfoFromFile();
			   UserHandle.viewAllCommitLog();
			   
			   System.out.println("WARNING: if you're sure what you're doing, ");
			   System.out.println("please enter the name of this commit you want to roll back to, or you may use \"cancel\" to cancel this creating process.");
			   
			   Scanner input = new Scanner(System.in);
			   String commitName=input.next();
			   if(commitName.equals("cancel"))
			   {
				   System.out.println("May not a bad idea (?)  0.0 ");
				   return;
			   }
			   
			   UserHandle.executeRollback(project , commitName);

			   System.out.println("I hope it would'n be rainy....Sooner or later, time will tell. Your command is done.");
		   }
		   
		   if(command.equals(".newbranch"))
		   {
			   System.out.println("Welcome to create branch guide!!");
			   System.out.println("Please enter new branch's name, or you may use \"cancel\" to cancel this process.");
			   
			   Scanner input = new Scanner(System.in);
			   String branchName=input.next();
			   if(branchName.equals("cancel"))
			   {
				   System.out.println("See you next time, OxO.");
				   return;
			   }
			   
			   Project project =  new Project("luoxukun");
			   project.loadProjectInfoFromFile();
			   UserHandle.createBranch(project,branchName);
			   System.out.println("Branch named " + branchName +" has been created!");
		   }
		   
		   
		   if(command.equals(".changebranch"))
		   {
			   System.out.println("You're going to change branch");
			   
			   Project project =  new Project("luoxukun");
			   project.loadProjectInfoFromFile();
			   UserHandle.viewAllBranchesInProject();
			   
			   System.out.println("Please choose a branch's name, or you may use \"cancel\" to cancel this creating process.");
			   
			   Scanner input = new Scanner(System.in);
			   String branchName=input.next();
			   if(branchName.equals("cancel"))
			   {
				   System.out.println("See you next time, OxO.");
				   return;
			   }
			   
			   UserHandle.changeCurrentBranch(project,branchName);
		   }
		   
	   }
}
