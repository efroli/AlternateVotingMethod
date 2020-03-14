package p1_4020_802135440_192;

public class Ballot 
{
	//Constructor Instances
	private LinkedList<Integer> theBallot = new LinkedList<Integer>();
	private int ballotNumber;

	//Validation Instances
	private boolean isBallotEmpty = false;
	private boolean isBallotInvalid = false;
	private int rankValueChecker = 0;

	
	//Constructor
	public Ballot(String cvsString)
	{
		//Format "candidateID : rank". Rank is determined by index order.
		//Store candidate ID in index corresponding to rank. 

		String[] csvFile = cvsString.split(","); 	
		ballotNumber = Integer.parseInt(csvFile[0]);

		//Verifying if ballot is empty. Only contains the ballot number. 
		if(csvFile.length == 1)
			isBallotEmpty = true;

		//Verifies if rank is skipped (1,2,4), if it is repeated (1,2,2,3) and if rank exceeds
		//number of candidates (Example: 5 candidates: 1,2,3,4,6)
		LinkedList<Integer> tempRankHolder = new LinkedList<Integer>();

		for(int m=1; m < csvFile.length; m++)
		{
			String[] tempString = csvFile[m].split(":");
			tempRankHolder.add(Integer.parseInt(tempString[1]));			
		}
		//rankValueChecker is a constant that increases by one per iteration in list. 
		//Its value should be equal to the rankValue in the ballot. Since the rank value 
		//increases +1 and should be in order. 1,2,3,...n (n = number of candidates).
		for(int z=0; z < tempRankHolder.size(); z++)
		{				
			rankValueChecker++; 
			if(tempRankHolder.get(z) != rankValueChecker)
			{	isBallotInvalid = true;		}	
		}
		
		//Verifies if candidate ID is repeated in ballot. Rendering ballot invalid
		LinkedList<Integer> tempCandidateIDHolder = new LinkedList<Integer>();
		//Saving candidateIDs in list
		for(int m=1; m < csvFile.length; m++)
		{
			String[] tempString = csvFile[m].split(":");
			tempCandidateIDHolder.add(Integer.parseInt(tempString[0]));			
		}

		//There should only be 1 index per candidate ID (firstIndex = lastIndex because only one index)
		//If this is not true it means the candidate ID appears more than once. 
		//Rendering the ballot invalid
		for(int i=0; i < tempCandidateIDHolder.size(); i++)
		{
			if(tempCandidateIDHolder.firstIndex(tempCandidateIDHolder.get(i)) !=
					(tempCandidateIDHolder.lastIndex(tempCandidateIDHolder.get(i))	))
			{	this.isBallotInvalid = true;	}
		}


		if(isBallotEmpty == false && isBallotInvalid == false)
		{
			//Only adding candidate IDs to the ballot. In the order they are added to the list is 
			//what determines their rank. Since candidate rank per ballot is given in ascending order. 
			//Starting index=1 because index=0 is ballot number. 
			for(int m=1; m < csvFile.length; m++)
			{
				String[] tempString = csvFile[m].split(":");
				theBallot.add(Integer.parseInt(tempString[0]));			
			}
		}

	}//end constructor

	//Methods
	public int getBallotNumber()
	{	return ballotNumber;	}

	//Rank is given by index in array. Move through array searching for candidate ID number.
	public int getRankByCandidate(int candidateID)
	{		
		for(int i=0; i < theBallot.size(); i++)
		{			
			if(theBallot.get(i).equals(candidateID))
			{return i+1;}
		}

		return 0; //dummy. Only for compiler error. This is never reached. 
	}

	//Rank is given by index in array.
	public int getCandidateByRank(int rank)
	{	
		return theBallot.get(rank-1);
	}

	//The list method remove returns true if able to remove, false if unable to remove. 
	public boolean eliminate(int candidateID)
	{	
		return this.theBallot.remove(candidateID);
	}

	//Validation Methods  	
	public boolean isBallotEmpty(Ballot b)
	{
		if(b.isBallotEmpty == true)
		{	return true;	}
		return false;
	}

	public boolean isBallotInvalid(Ballot b)
	{
		if(b.isBallotInvalid == true)		
		{	return true;	}
		return false;
	}

//TEST METHOD. Used in Election Class. 
	public int ballotSize()
	{	return theBallot.size();	}


}//end class
