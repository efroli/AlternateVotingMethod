package p1_4020_802135440_192;

//import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.nio.*;
//import java.nio.file.*;

public class Election 
{
	private int numOfRounds = 0;
	private int numOfBallots = 0;
	private int numOfBlankBallots = 0;
	private int numOfInvalidBallots = 0;	
	
	private LinkedList<Integer> eliminatedCandidates = new LinkedList<Integer>();


	public static void main(String[] args) 
	{
		String votesData;
		LinkedList<Ballot> listOfBallots = new LinkedList<Ballot>();

		//Reading ballots from csv file
		//Based on: Java Reading a CSV File Tutorial by Jose Vidal (https://www.youtube.com/watch?v=3_40oiUdLG8)
		String csvFileName = "efroliballot.csv";
		File file = new File(csvFileName);
		try
		{
			Scanner input = new Scanner(file);
			while(input.hasNext())
			{
				votesData = input.next();
				System.out.println(votesData);
			//Adding line from csv file (a ballot) to list of ballots (list of Ballots = Elections). 
				Ballot tempBallot = new Ballot(votesData);
				listOfBallots.add(tempBallot);
	//DEV TEST print
				System.out.println(listOfBallots.size());  
			}
			input.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	//DEV TEST BALLOT CONTENT
	//My own tester that verifies for each type of invalid ballot 
	//(empty, repeated rank, repeated candidate ID, rank value > number of candidates)
		System.out.println("");
		System.out.println("BALLOT CONTENT TEST");
		Ballot test = new Ballot("824,5:1,2:2,3:3,4:4,1:5");
//		Ballot test = new Ballot("824"); //testing empty
//		Ballot test = new Ballot("824,5:2,2:2,3:3,4:4,1:5"); //invalid: rank repeated
//		Ballot test = new Ballot("824,5:1,2:2,3:3,4:4,1:6"); //invalid: rank exceeds number of candidates
//		Ballot test = new Ballot("824,3:1,2:2,3:3,4:4,1:5"); //invalid: candidate ID repeat.
		//Implementation of constructor adapts to size of string input. No need to verify if a 
		//candidate is not voted for. 

		System.out.println("Ballot Info: 824,5:1,2:2,3:3,4:4,1:5");
		System.out.println(test.ballotSize());
		System.out.println(test.isBallotEmpty(test));
		System.out.println(test.isBallotInvalid(test));
		
		//Modify Expected values based on values in test ballot above. 
		System.out.println("Ballot number: Expected: 824 | Recieved: "+test.getBallotNumber());
		System.out.println("1st Candidate: Expected: 5 | Recieved: "+test.getCandidateByRank(1));
		System.out.println("2nd Candidate: Expected: 2 | Recieved: "+test.getCandidateByRank(2));
		System.out.println("3rd Candidate: Expected: 3 | Recieved: "+test.getCandidateByRank(3));
		System.out.println("4th Candidate: Expected: 4 | Recieved: "+test.getCandidateByRank(4));
		System.out.println("5th Candidate: Expected: 1 | Recieved: "+test.getCandidateByRank(5));
	//END DEV TEST 
		
	/*
	 * -check who is topCandidate getCandidateByRank(int n)
	 * -list of eliminated candidates (LinkedList)
	 * -temporary array that accumulates number of 1's a candidate gets. This is saved in a specific index. 
	 * candidate ID = index+1 (since starts in zero). Index with lowest value is index to be eliminated. 
	 * Index corresponding to candidate ID. 
	 * -use method from ballot isEmpty or isInvalid to accumulate numOf___Ballots. 
	 * */	
		
	
	}//end main	
	
	
	//Methods
	
	
}//end class
