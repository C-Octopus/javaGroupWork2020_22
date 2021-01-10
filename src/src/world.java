package src;

import java.util.Scanner;

public class world 
{
	
	   public static void main(String[] args) throws Exception
	   {
		   //initialized
		   UserHandle.worldInitial();
		   @SuppressWarnings("resource")
		   Scanner input = new Scanner(System.in);
		   
		   while(true)
		   {
			   System.out.println("Waiting for order.....enter \"world.end to quit! \"");
			   String command = input.next(); //".view.branch" ;
			   
			   if(command.equals("world.create") )
			   {
				   System.out.println("Welcome to create project guide!!");
				   System.out.println("Please enter your project's name, or you may use \"cancel\" to cancel this creating process.");
				   
				   String projectName=input.next();
				   if(projectName.equals("cancel"))
				   {
					   System.out.println("See you next time, QAQ.");
					   continue;
				   }
				   
				   System.out.println("Please enter your project's working space, ");
				   System.out.println("You can also use \"cancel\" to cancel this creating process.");
				   
				   String workingSpacePath=input.next();
				   if(workingSpacePath.equals("cancel"))
				   {
					   System.out.println("See you next time, QAQ.");
					   continue;
				   }
				   
				   UserHandle.createProject(projectName, workingSpacePath);
			   }
			   
			   
			   if(command.equals("world.commit") )
			   {
				   System.out.println("You're trying to launch a commit process! ¡Æ(- ¡õ -)!!!");
				   System.out.println("Please enter the name of this commit, or you may use \\\"cancel\\\" to cancel this creating process.");
				   
				   String commitName=input.next();
				   if(commitName.equals("cancel"))
				   {
					   System.out.println("See you next time, TVT.");
					   continue;
				   }
				   
				   UserHandle.executeCommit(commitName);
			   }
			   
			   
			   if(command.equals("world.view.project"))
			   {
				   UserHandle.viewAllProjecs();
			   }
			   
			   if(command.equals("world.view.branch"))
			   {
				   UserHandle.viewAllBranchesInProject();
			   }
			   
			   if(command.equals("world.view.commit"))
			   {
				   UserHandle.viewAllCommitLog();
			   }
			   
			   
			   if(command.equals("world.open"))
			   {
				   //(1)
				   System.out.println("There're following projects, please select a project you wish to open");
				   UserHandle.viewAllProjecs();

				   //(2)
				   System.out.println("Please enter the name of a project,  or you may use \"cancel\" to cancel this creating process.");

				   String projectName=input.next();
				   if(projectName.equals("cancel"))
				   {
					   System.out.println("See you next time,  A W A ");
					   continue;
				   }
				   
				   //(3)
				   UserHandle.openAProject(projectName);
			   }
			   
			   
			   if(command.equals("world.rollback"))
			   {
				   System.out.println("WARNING: You're trying to launch a rolling back process! ¡Æ(; ¡õ ;)|||  " );
				   System.out.println("Here are all of the commits in this branch!  " );
				   UserHandle.viewAllCommitLog();
				   
				   System.out.println("WARNING: if you're sure what you're doing, ");
				   System.out.println("please enter the name of this commit you want to roll back to, or you may use \"cancel\" to cancel this creating process.");

				   String commitName=input.next();
				   if(commitName.equals("cancel"))
				   {
					   System.out.println("May not a bad idea (?)  0.0 ");
					   continue;
				   }
				   
				   UserHandle.executeRollback(commitName);
			   }
			   
			   if(command.equals("world.newbranch"))
			   {
				   System.out.println("Welcome to create branch guide!!");
				   System.out.println("Please enter new branch's name, or you may use \"cancel\" to cancel this creating process.");
				   
				   String branchName=input.next();
				   if(branchName.equals("cancel"))
				   {
					   System.out.println("See you next time, OxO.");
					   continue;
				   }
				   
				   UserHandle.createBranch(branchName);
			   }
			   
			   
			   if(command.equals("world.changebranch"))
			   {
				   System.out.println("You're going to change branch,  here are all branches in the project:");
				   UserHandle.viewAllBranchesInProject();
				   
				   System.out.println("Please enter a branch's name, or you may use \"cancel\" to cancel this creating process.");

				   String branchName=input.next();
				   if(branchName.equals("cancel"))
				   {
					   System.out.println("See you next time, OxO.");
					   continue;
				   }
				   
				   UserHandle.changeCurrentBranch(branchName);
			   }
			   
			   if(command.equals("world.end"))
			   {
				   System.out.println("Good night~");
				   break;
			   }
			   
			   else
			   {
				   System.out.println("ops....wrong command...");
			   }
		   }
	   }
}
