package src;

public class Branch 
{
	public String branchName;
	private String newestCommitName;
	
	public Branch(String branchName , String newestCommitName)
	{
		this.branchName=branchName;
		this.newestCommitName=newestCommitName;
	}
	
	public String getNewestCommitName()
	{
		return newestCommitName;
	}
	
	public void setNewestCommitName(String newCommit)
	{
		newestCommitName=newCommit;
	}
}
