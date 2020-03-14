package p1_4020_802135440_192;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
	//DEV TEST print
//				System.out.println(candidatesData);
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
				
//	//DEV TEST print
//				System.out.println("BALLOT INFO");  
//				System.out.println("Size of List");  
//				System.out.println(listOfBallots.size());  
//				System.out.println("Blank Ballots");  
//				System.out.println(numOfBlankBallots);  
//				System.out.println("Invalid Ballots");  
//				System.out.println(numOfInvalidBallots);  
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
				//candidate's ID number.
				String firstName = candidatesInput.next();
				String[] lastName = candidatesInput.next().split(",");
				String fullName = String.join(" ", firstName, lastName[0]);
				listOfCandidates.add(fullName);
				
//	//DEV TEST print
//				System.out.println("");  
//				System.out.println("CANDIDATES INFO");  
////				System.out.println(firstName+" "+lastName[0]);
////				System.out.println(fullName);
//				System.out.println(listOfCandidates.get(listOfCandidates.firstIndex(fullName)));  
				

//				Ballot tempBallot = new Ballot(candidatesData);		
//				//Add ballot to list only if it's valid or not empty. 
//				if(tempBallot.isBallotEmpty(tempBallot) == true)
//				{	numOfBlankBallots++;	}
//				
//				else if(tempBallot.isBallotInvalid(tempBallot) == true)
//				{	numOfInvalidBallots++;	}
//				
//				else if(tempBallot.isBallotEmpty(tempBallot) == false && 
//				   tempBallot.isBallotInvalid(tempBallot) == false)
//				{	listOfBallots.add(tempBallot);	}			
	//DEV TEST print
//				System.out.println("Size of List");  
//				System.out.println(listOfBallots.size());  
//				System.out.println("Blank Ballots");  
//				System.out.println(numOfBlankBallots);  
//				System.out.println("Invalid Ballots");  
//				System.out.println(numOfInvalidBallots);  
			}
			candidatesInput.close();
		}
		catch(FileNotFoundException e)
		{	e.printStackTrace();	}
		
		/*
		 * -check who is topCandidate getCandidateByRank(int n)
		 * -list of eliminated candidates (LinkedList)
		 * -temporary array that accumulates number of 1's a candidate gets. This is saved in a specific index. 
		 * candidate ID = index+1 (since starts in zero). Index with lowest value is index to be eliminated. 
		 * Index corresponding to candidate ID. 
		 * -use method from ballot isEmpty or isInvalid to accumulate numOf___Ballots. 
		 * -create result.txt file that outputs desired info. 
		 * -counting rules method(s) 
		 * 
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
		 */	
		
//**************** DEV PLAYGROUNDD AREA (Real Output must be in result.txt file) ****************//
				
//	//DEV TEST BALLOT CONTENT
//	//My own tester that verifies for each type of invalid ballot 
//	//(empty, repeated rank, repeated candidate ID, rank value > number of candidates)
//		System.out.println("");
//		System.out.println("BALLOT CONTENT TEST");
//		Ballot test = new Ballot("824,5:1,2:2,3:3,4:4,1:5");
////		Ballot test = new Ballot("824"); //testing empty
////		Ballot test = new Ballot("824,5:2,2:2,3:3,4:4,1:5"); //invalid: rank repeated
////		Ballot test = new Ballot("824,5:1,2:2,3:3,4:4,1:6"); //invalid: rank exceeds number of candidates
////		Ballot test = new Ballot("824,3:1,2:2,3:3,4:4,1:5"); //invalid: candidate ID repeat.
//		//Implementation of constructor adapts to size of string input. No need to verify if a 
//		//candidate is not voted for. 
//
//		System.out.println("Ballot Info: 824,5:1,2:2,3:3,4:4,1:5");
//		System.out.println(test.ballotSize());
//		System.out.println(test.isBallotEmpty(test));
//		System.out.println(test.isBallotInvalid(test));
//		
//		//Modify Expected values based on values in test ballot above. 
//		System.out.println("Ballot number: Expected: 824 | Recieved: "+test.getBallotNumber());
//		System.out.println("1st Candidate: Expected: 5 | Recieved: "+test.getCandidateByRank(1));
//		System.out.println("2nd Candidate: Expected: 2 | Recieved: "+test.getCandidateByRank(2));
//		System.out.println("3rd Candidate: Expected: 3 | Recieved: "+test.getCandidateByRank(3));
//		System.out.println("4th Candidate: Expected: 4 | Recieved: "+test.getCandidateByRank(4));
//		System.out.println("5th Candidate: Expected: 1 | Recieved: "+test.getCandidateByRank(5));
//	//END DEV TEST 
		
		runTheElection(listOfBallots, listOfCandidates, eliminatedCandidates);
	
	}//end main	
	
	
	//Methods
	public static void runTheElection(LinkedList<Ballot> ballotsList, LinkedList<String> candidatesList, 
			LinkedList<Integer> eliminatedList)
	{
		int looserAmountOfOnes = 0;
		int topCandidateAmountOfOnes = 0;
		
		int[] oneCounter = new int[candidatesList.size()]; 
		boolean looserEliminated = false; 
		boolean isThereAWinner = false;
		
		updateResultFile("result.txt", "Number of Ballots Recieved "+numOfBallots);
		updateResultFile("result.txt", "Number of Blank Ballots "+numOfBlankBallots);
		updateResultFile("result.txt", "Number of Invalid Ballots  "+numOfInvalidBallots);

		
		//Election Cycle 
		while(!isThereAWinner)
		{
			numOfRounds++; //Round number in which candidate was eliminated
			
			//Using an array to store the amount of 1's per index. Where the order of the index
			//coincides with the candidateID. (technically index+1).
			for(int i=0; i < ballotsList.size(); i++)
			{
				Ballot tempBallot = ballotsList.get(i);
				int topCandidateID = tempBallot.getCandidateByRank(1);
			
				//Corresponding index with Candidate ID and saving in candidateID - 1 == index+1. 
				for(int f=0; f < tempBallot.ballotSize(); f++ )
				{
					if(f+1 == topCandidateID)
					{	oneCounter[f]++;	} //POSIBLE PROBLEM if prob: one = one + 1;
				}
			}
			
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
			
			if(isWinningConditionMet(topCandidateAmountOfOnes, numOfBallots))
			{	
				isThereAWinner = true;	
				updateResultFile("result.txt", "Winner: <"+candidatesList.get(topCandidateID-1)+"> wins "
						+ "with <"+topCandidateAmountOfOnes+"> #1’s.");
				continue; 
			}
			
			//Nobody has more than 50% lets eliminate the lowest ranking candidate
			while(!looserEliminated)
			{
				//Verifying who is lowest ranking candidate
				looserAmountOfOnes = oneCounter[0]; 
				int lowestCandidateID = 1;
				for(int y=0; y < oneCounter.length-1; y++)
				{
					if(looserAmountOfOnes > oneCounter[y+1])
					{	
						looserAmountOfOnes = oneCounter[y+1];	
						lowestCandidateID = y+2;
					}
					
				}
				
				//Verifying if there are ties then saving the candidate's id in another array. 
				//Then only those candidates will be verified through all ballots. 
				boolean isThereATie = false;
				int[] tiedCandidates = new int[candidatesList.size()];
				int x = 0;
				tiedCandidates[x] = lowestCandidateID;
				
				for(int y=0; y < oneCounter.length-1; y++)
				{
					if(looserAmountOfOnes == oneCounter[y+1] && lowestCandidateID != y+1)
					{	isThereATie = true;
						tiedCandidates[x+1] = y+2;
						x++;
					}
				}
				if(isThereATie == false)
				{	tiedCandidates[0] = 0;	}
				
				
				{looserEliminated = true;}
				
			}//looserEliminated
									
		}//isThereAWinner
		
		/*
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
		 * 
		 * 
		 */
		/*
		 * -winningNumber variable (se ajusta cada vez q se elimina un candidato) tamaño inicial es 
		 * num de candidatos
		 * -array temporero para almacenar cantidad de 1's. El de menor cantidad será eliminado. 
		 * Initial size is the size of the candidatesList (each index represents candidate) 
		 * Ex. Abraham index 0, Benito index 1, etc. 
		 * -De haber empate corre otro metodo donde verifica los segundos, terceros, etc. Hasta el 
		 * desempate. Base condition si son iguales el del ID mas bajito gana. 
		 * Pueden haber triple/cuadruple empates.
		 * 
		 * Escribe el txt file por round por round
		 */
		
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
	
	//JAVADOC FORMAT ALL METHODS
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
	
	
	
}//end class
