package p1_4020_802135440_192;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


//****************** NOTE TO TA ******************//
/*
 * Everything is correctly implemented (Ballot class, Reading Candidate csv and Ballot csv in main. 
 * Problem in code is tieBreaker method (not fully implemented or at least buggy). 
 * github repo with my own test cases written in main is available here : 
 * 			https://github.com/efroli/AlternateVotingMethod
 */

public class Election 
{
	private static int numOfRounds = 0;
	private static int numOfBallots = 0;
	private static int numOfBlankBallots = 0;
	private static int numOfInvalidBallots = 0;
	
	private static LinkedList<Integer> eliminatedCandidates = new LinkedList<Integer>();
	private static LinkedList<Ballot> listOfBallots = new LinkedList<Ballot>();
	private static LinkedList<String> listOfCandidates = new LinkedList<String>();

	
	public static void main(String[] args) 
	{	
		String candidatesData;

		//Reading BALLOTS from csv file
		//Based on: Java Reading a CSV File Tutorial by Jose Vidal (https://www.youtube.com/watch?v=3_40oiUdLG8)
		String ballotCsv = "efroliballot.csv";
		File ballotFile = new File(ballotCsv);
		try
		{
			Scanner ballotInput = new Scanner(ballotFile);
			while(ballotInput.hasNext())
			{
				candidatesData = ballotInput.next();
				numOfBallots++;
			//Adding line from csv file (a ballot) to list of ballots (list of Ballots = Elections). 
				Ballot tempBallot = new Ballot(candidatesData);
			
				//Add ballot to list only if it's valid or not empty. 
				if(tempBallot.isBallotEmpty(tempBallot) == true)
				{	numOfBlankBallots++;	}
				
				else if(tempBallot.isBallotInvalid(tempBallot) == true)
				{	numOfInvalidBallots++;	}
				
				else if(tempBallot.isBallotEmpty(tempBallot) == false && 
				   tempBallot.isBallotInvalid(tempBallot) == false)
				{	listOfBallots.add(tempBallot);	}
				
			}
			ballotInput.close();
		}
		catch(FileNotFoundException e)
		{	e.printStackTrace();	}
		
		
		//Reading CANDIDATES from csv file
		//Based on: Java Reading a CSV File Tutorial by Jose Vidal (https://www.youtube.com/watch?v=3_40oiUdLG8)
		String candidatesCsv = "efrolicandidates.csv";
		File candidatesFile = new File(candidatesCsv);
		try
		{
			Scanner candidatesInput = new Scanner(candidatesFile);
			while(candidatesInput.hasNext())
			{
				//Adding line from csv file (candidate info) to list of candidates.
				//candidate IDs are in ascending order and with continuous values starting from 1.
				//Example: 5 candidates: 1,2,3,4,5. Therefore the list's index will give us the
				//candidate's ID number (technically index+1).
				String firstName = candidatesInput.next();
				String[] lastName = candidatesInput.next().split(",");
				String fullName = String.join(" ", firstName, lastName[0]);
				listOfCandidates.add(fullName);
				
			}
			candidatesInput.close();
		}
		catch(FileNotFoundException e)
		{	e.printStackTrace();	}
		

		runTheElection(listOfBallots, listOfCandidates, eliminatedCandidates);
	//DEV TEST PRINT 
		System.out.println("Done");
	
	}//end main	
	
	
	//Methods
	public static void runTheElection(LinkedList<Ballot> listOfBallots, LinkedList<String> listOfCandidates, 
			LinkedList<Integer> eliminatedCandidates)
	{
		
		boolean isThereAWinner = false;
	
		//Necessary output info that comes before Round Results.
		updateResultFile("result.txt", "Number of Ballots Recieved: "+numOfBallots);
		updateResultFile("result.txt", "Number of Blank Ballots: "+numOfBlankBallots);
		updateResultFile("result.txt", "Number of Invalid Ballots: "+numOfInvalidBallots);

		
		//Election Cycle 
		while(!isThereAWinner)
		{
			numOfRounds++; //Round number in which candidate was eliminated
			
			//Values inside while loop so they can reset every round. 
			int looserAmountOfOnes = 0;
			int topCandidateAmountOfOnes = 0;
			
			//The elements in the array allocated by new will automatically be initialized to 
			//zero (for numeric types), false (for boolean), or null (for reference types).
			int[] oneCounter = new int[listOfCandidates.size()]; 
			
			//Using an array to store the amount of 1's per index. Where the order of the index
			//coincides with the candidateID. (technically index+1).
			for(int i=0; i < listOfBallots.size(); i++)
			{
				Ballot tempBallot = listOfBallots.get(i);
				int topCandidateID = tempBallot.getCandidateByRank(1);
			
				//Corresponding index with Candidate ID and saving in #1's counter (topCandidateID - 1 == index+1).
				for(int f=0; f < oneCounter.length; f++)
				{
					if(f+1 == topCandidateID)
					{	oneCounter[f] = oneCounter[f] + 1;	} 
				}
			}
//DEV TEST PRINT
			System.out.println("One Counter: ");
			
			for(int i=0; i<oneCounter.length; i++)
			{	System.out.println("Value at index = "+i+" is "+oneCounter[i]);	}
			System.out.println("Index with 1 values (value = 3): Expected 2, 3");
			isThereAWinner = true; //STOPS INFINITE LOOP
//END DEV TEST

			//Verifying who is Top Candidate to evaluate if they have more than 50% of 1's.
			//Moving through the array to see which index (CandidateID) has the highest 
			//amount of 1's.
			topCandidateAmountOfOnes = oneCounter[0]; 
			int topCandidateID = 1;
			for(int y=0; y < oneCounter.length-1; y++)
			{
				if(topCandidateAmountOfOnes < oneCounter[y+1])
				{	
					topCandidateAmountOfOnes = oneCounter[y+1];	
					topCandidateID = y+2;
				}
			}
			
			if(isWinningConditionMet(topCandidateAmountOfOnes, listOfBallots.size()))
			{	
				isThereAWinner = true;	
				updateResultFile("result.txt", "Winner: <"+listOfCandidates.get(topCandidateID-1)+"> wins "
						+ "with <"+topCandidateAmountOfOnes+"> #1’s.");
				continue; //POSIBLE PROBLEM IF DOES NOT EXIT WHILE LOOP ITERATION. 
			}
			
			//Nobody has more than 50% lets eliminate the lowest ranking candidate
			//Verifying who is the lowest ranking candidate
			looserAmountOfOnes = oneCounter[0]; 
			int looserCandidateID = 1;
			for(int y=0; y < oneCounter.length-1; y++)
			{
				if(looserAmountOfOnes > oneCounter[y+1])
				{	
					looserAmountOfOnes = oneCounter[y+1];	
					looserCandidateID = y+2;
				}

			}
	//DEV TEST PRINT
			System.out.println("Looser Amount of Ones: Expected: 0 | Recieved: "+looserAmountOfOnes);
			System.out.println("Looser Candidate ID: Expected: 1 | Recieved: "+looserCandidateID);

			//Verifying if there are ties between candidates. Array size is the amount of candidates in election.
			//Candidates ID will be determined by index (candidate ID = index+1). 
			//Only those candidates will be verified through all ballots.
			//If there is a tie the value of the corresponding index (ID number - 1) for the candidate will be 1.
			//Representing that this candidate must be verified. 
			//The elements in the array allocated by new will automatically be initialized to 
			//zero (for numeric types), false (for boolean), or null (for reference types).
			boolean isThereATie = false;
			int[] tiedCandidates = new int[listOfCandidates.size()];
			
			
	//DEV TEST PRINT
			System.out.println("tied Candidates in List: ");
			System.out.println("Index with 1 values: Expected 0, 1, 4");

			
			for(int y=0; y < oneCounter.length; y++)
			{
//	//DEV TEST PRINT
//				System.out.println("BEFORE TIE CHECK: Candidates at index = "+y+" is "+tiedCandidates[y]);
				//Candidate ID is being compared to Index+1 since this is the candidates ID. The value in the index
				//is the amount of #1's per candidate. 
				if(oneCounter[y] == looserAmountOfOnes && looserCandidateID != y+1)
				{	
					isThereATie = true;
					tiedCandidates[looserCandidateID-1] = 1;
					tiedCandidates[y] = 1;	
	//DEV TEST PRINT
					System.out.println("Candidates at index = "+y+" is "+tiedCandidates[y]);
	//END DEV TEST

				}		
			}
	//DEV TEST PRINT
			System.out.println("is There A Tie? "+isThereATie);
			for(int i=0; i< tiedCandidates.length; i++)
				System.out.println("Candidates at index = "+i+" is "+tiedCandidates[i]);

			
			if(isThereATie == true)
			{	looserCandidateID = tieBreaker(tiedCandidates, listOfBallots, 1);	}
//			
//			eliminateLowestRankingCandidate(looserCandidateID, listOfBallots, eliminatedCandidates);
//			updateResultFile("result.txt", 
//					"Round "+numOfRounds+": "+listOfCandidates.get(looserCandidateID-1)+ 
//					" was eliminated with "+looserAmountOfOnes+ " #1’s.");				
				
				/*
				 * Design 
				 * paso 1 contar 1's
				 * paso 2 check si hay ganador 
				 * paso 3 if no hay ganador itera hasta q se obtenga looser
				 * 		compare 2's, is there a looser? if not keep comparing until looser found.
				 * paso 4 delete looser from ballots where he is top candidate (has 1's). 
				 * 		Check that new top candidate is not already eliminated. If already eliminated 
				 * 		delete from ballot. Check top candidate until he does not appear on eliminatedList.  
				 * paso 5 escribe en result.txt resultado de ronda. 
				 * -----------------------------
				 * Output in result.txt file
				 * Number of ballots received
				 * Number of blank ballots (no candidates were selected)
				 * Number of invalid ballots
				 * Round results. For each eliminated candidate you must store:
				 * Number of 1’s that the candidate received at the moment of elimination
				 * Round in which the candidate was eliminated.
				 * 
				 * Use the following format: “Round <num>: <Candidate name> was eliminated with <num> #1’s”.
				 * Winner, in the following format: “Winner: <Candidate name> wins with <num> #1’s”.
				 * -----------------------------
				 * TEST CASES (5 candidates)
				 * -triple tie until 4 
				 * -tie  with one candidate not voted for 
				 * -everything tied so must choose based on candidate ID 
				 */
								
//			}//looserEliminated
									
		}//isThereAWinner
		
	}//end runTheElection method
	
	//Number of Ones is taken from array that accumulates the 
	//top candidate (getCandidateByRank) per ballot. Each index in the array represents the 
	//candidatesID (technically speaking index+1).
	public static boolean isWinningConditionMet(int numOfOnes, int numOfBallots)
	{
		float percentageOfOnes = numOfOnes / numOfBallots;
		if(percentageOfOnes > 0.50)
		{	return true;	}
		
		return false;
	}
	
	//Creates, Saves and Updates to Result.txt file
	//Base on: Writing to Text Files (Java) - Creates saveToFile method (https://www.youtube.com/watch?v=Wrx-_oPiI90)
	public static void updateResultFile(String fileName, String sentenceToAdd) //result.txt as parameter argument
	{
		try 
		{
			File theFile = new File(fileName);	
			FileWriter fw = new FileWriter(theFile, true); //true argument appends to the file. 
			PrintWriter pw = new PrintWriter(fw);

			pw.println(sentenceToAdd);
			pw.close();
		}
		catch(IOException e)
		{	e.printStackTrace();	}
	}
	
	//Parameter is a list of candidates with the same rank. Those candidates will be compared in every ballot.
	//counting the amounts of #2's, #3's...etc until a tie breaker is found. Tie is broken when one candidate
	//has less votes than the other. Be it number of #1,#2,#3 and so on. If every single vote in every single
	//ballot is identical the candidate with the largest ID number is eliminated. 
	//Returns the lowest ranking candidate's id number. 
	public static int tieBreaker(int[] tiedCandidatesList, LinkedList<Ballot> listOfBallots, int currentRank)
	{
		int lowestRankingCandidatesID = 1; //assuming the first candidate in tiedCandidateList is the lowest. 

		//The elements in the array allocated by new will automatically be initialized to 
		//zero (for numeric types), false (for boolean), or null (for reference types).
		int[] rankCounter = new int[tiedCandidatesList.length];
		//Starts in 2 because a tie was already establish for rank = 1. CurrentRank++ gives us the
		//next rank to be verified per candidate.
		currentRank++;
		
	//DEV TEST PRINT 
		System.out.println("");
		System.out.println("Inside tieBreaker Method: ");

		
//		//Holds candidate ID's that are going to be verified. This avoids verifying not tied candidates by
//		//accident. 
//		int[] tiedCandidatesID = new int[tiedCandidatesList.length];
//		for(int i=0; i < tiedCandidatesList.length; i++)
//		{
//			if(tiedCandidatesList[i] == 1)
//				tiedCandidatesID[i] = i+1;
//	//DEV TEST PRINT 
//			System.out.println("tieBreaker Candidate ID's to check. index = "+i+" is "+tiedCandidatesID[i]);
//		}

	//DEV TEST PRINT 
		System.out.println("Current Rank Evaluating: "+currentRank);
		for(int i=0; i < tiedCandidatesList.length; i++)
		{
			System.out.println("tieBreaker Candidates at index = "+i+" is "+tiedCandidatesList[i]);
		}
	//END TEST PRINT 

		
	//NEED TO TEST 
		//If all currentRank = listOfCandidate's size this means all ranks in all ballots are identical for
		//the tied candidates. Therefore the candidate with the largest ID number (given by index of array)
		//will be deemed the looser and eliminated. 
		if(currentRank == listOfCandidates.size())
		{
			for(int i = listOfCandidates.size(); i > 0 ; i--)
			{	
				if(tiedCandidatesList[i] == 1)
				{	return i;	}

			}
		}

		//Candidate's ID number is stored in index (where ID Number - 1 = index).
		//Counting amount of rank values per candidate in tiedCandidates parameter. 
		for(int i=0; i < listOfBallots.size(); i++)
		{
			Ballot tempBallot = listOfBallots.get(i);
			int topCandidateID = tempBallot.getCandidateByRank(currentRank);

			//Corresponding index with Candidate ID and saving in #1's counter (topCandidateID - 1 == index+1).
			//Accumulating amount of rank values per index (where index corresponds with candidate ID). 
			for(int f=0; f < rankCounter.length; f++ )
			{
				if(f+1 == topCandidateID)
				{	rankCounter[f] = rankCounter[f] + 1;	} 
			}
		}

	//DEV TEST PRINT 
		for(int i=0; i < rankCounter.length; i++)
		{
			System.out.println("tieBreaker rankCounter value at index = "+i+" is "+rankCounter[i]);
		}
		
		//Verifying who is the lowest ranking candidate
		//Establish lowest candidate's id and number of rank values. 
		int looserAmountOfRankNum = rankCounter[0]; 
			lowestRankingCandidatesID = 1; //return this value when tie broken. 
		int[] tiedCandidatesAgain = new int[tiedCandidatesList.length];
		boolean tiedAgain = false;
		int[] indexOfTiedCandidates = new int[tiedCandidatesList.length];
//		for(int y=0; y < rankCounter.length-1; y++)
//		{
//			if(tiedCandidatesList[y] == 1)
//			{
				for(int y=0; y < rankCounter.length-1; y++)
				{
//					if(tiedCandidatesList[y] == 1)
//					{
					if(looserAmountOfRankNum > rankCounter[y+1])
					{	
						looserAmountOfRankNum = rankCounter[y+1];	
						lowestRankingCandidatesID = y+2;
						System.out.println("Lowest Ranking Candidate ID Through Loop: "+lowestRankingCandidatesID);
					}
//					}
				}
//			}
//		}
				
//				//Verifying who is the lowest ranking candidate
//				looserAmountOfOnes = oneCounter[0]; 
//				int looserCandidateID = 1;
//				for(int y=0; y < oneCounter.length-1; y++)
//				{
//					if(looserAmountOfOnes > oneCounter[y+1])
//					{	
//						looserAmountOfOnes = oneCounter[y+1];	
//						looserCandidateID = y+2;
//					}
//
//				}

	//DEV TEST PRINT
		System.out.println("Lowest Ranking Candidate ID: Expected: 2 | Recieved: "+lowestRankingCandidatesID);



		for(int y=0; y < rankCounter.length-1; y++)
		{
			//Verifying if there is another tie. 
			if(looserAmountOfRankNum == rankCounter[y+1] && lowestRankingCandidatesID != y+2)
			{	
				tiedAgain = true;
				tiedCandidatesAgain[lowestRankingCandidatesID-1] = 1;
				tiedCandidatesAgain[y+1] = 1;
			}
		}
		//DEV TEST PRINT
		System.out.println("Is There a Tie? "+tiedAgain);

		if(tiedAgain == true)
			tieBreaker(tiedCandidatesAgain, listOfBallots, currentRank);
		//DEV TEST PRINT
		System.out.println("Lowest Ranking Candidate before ending method: "+lowestRankingCandidatesID);

		return lowestRankingCandidatesID;
	}//end tieBreaker

	public static void eliminateLowestRankingCandidate(int candidateID, LinkedList<Ballot> listOfBallots, 
			LinkedList<Integer> eliminatedCandidates)
	{
		
	}
	
}//end class