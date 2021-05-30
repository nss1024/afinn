package com.herts.lyra.affin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class ScroeCalculator {
	private Map<String,Integer> wordLexicon;
	private Map<String,Integer> emojiLexicon;
	private Map<String,Integer> expressionsLexicon;
	private List<Review> reviewsList;
	private ExcelProcessor ep;
	private AffinLexicon afl;
	
	
	

	public ScroeCalculator (String filePath)
	{
		ep = new ExcelProcessor(filePath);
		afl = new AffinLexicon();
		try {
			reviewsList = ep.parseExcel();
		} catch (IOException e1) {
		
			System.out.println("The source file could not be found in the path provided");
			//e1.printStackTrace();
		}
		try {
		
		wordLexicon = afl.generateAffinLexicon("AFINN-en-165.txt");
		emojiLexicon = afl.generateAffinLexicon("AFINN-emoticon-8.txt");	
		expressionsLexicon = afl.generateAffinExpressionList("AFINN-en-165.txt");
		} catch(IOException e) 
		{e.printStackTrace();}
	}
	
	/**
	 * Calculate one "overall" score for all of the reviews in a list, returns and integer representing the score
	 * @return 
	 */
	public int calculateOverallScore () 
	{
		int overallSentimentScore=0;
		for (int i=0;i<reviewsList.size();i++)
		{
			overallSentimentScore = overallSentimentScore + reviewsList.get(i).getScore();
		}
		return overallSentimentScore;
	}
	
	/**
	 * Generates an output file containing a comments and their sentiment score
	 * @param resultFilePath - obtained by providing the "r" or "result" option 
	 * and arguments in the command line
	 */
	public void generateOutputFile( String resultFilePath)
	
	{
		try {
			PrintWriter writer = new PrintWriter(new File(resultFilePath+"myAffinResults.tsv"));
			
			
			for (int i =0;i<reviewsList.size();i++) 
			{
				StringBuilder sb = new StringBuilder();
				sb.append(reviewsList.get(i).getDate());				
				sb.append("\t");
				sb.append(reviewsList.get(i).getComment());				
				sb.append("\t");
				sb.append(reviewsList.get(i).getScore());				
				sb.append("\n");				
				writer.write(sb.toString());
				
			}
			writer.close();
		}catch(Exception e) {
			
			System.out.println("The result file location could not be found in the file path provided");
			//e.printStackTrace();
			}
	}
	
	/**
	 * The method will iterate through a list a review objects and calculate the score for each one.
	 * Scores are calculated by iterating through each review object's comment field three times. 
	 * On each occasion, string matching is performed
	 * First iteration looks for expressions, if found, score is updated and expression is removed from the string 
	 * so it will not be counted twice in subsequent iterations
	 * Second iteration looks for specific words, if found , score is updated and word is removed
	 * Third iteration looks for emoticons, if found, score is updated and emoticon removed
	 * Any remaining text is discarded as would have a sentiment score of 0
	 */
	public void calculateReviewScores() // Surround with try-catch to handle nullpointrexception
	{
		
		for (int i=0;i<reviewsList.size();i++) //iterate through the list of review objects
		{
			String review =" "+reviewsList.get(i).getComment().toLowerCase()+" ";
			review=review.replaceAll("\\.", " ");//remove all dots
			review=review.replaceAll("\\,", " ");//remove all commas
			review=review.replaceAll("\\!", " ");//remove all !
			review=review.replaceAll("\\?", " ");// remove all ?
			
						
				for (String key : expressionsLexicon.keySet()) 
				{	
					if (review.contains(key)) 
					{
						int currentScore = reviewsList.get(i).getScore();
						int expressionCounter = 1;						
						if (StringUtils.countMatches(review, key)>1) {expressionCounter=StringUtils.countMatches(review, key);}
						currentScore = currentScore + (expressionsLexicon.get(key)*expressionCounter);
						reviewsList.get(i).setScore(currentScore);
						for (int j=0;j<expressionCounter;j++) {
							review=review.replace(key, "");	       
						}                                      
						
					}
				}
				
				for (String key : wordLexicon.keySet()) 
				{   
					if (review.contains(" "+key+" "))
					{
						int currentScore = reviewsList.get(i).getScore();
						int wordCounter = 1;
						if (StringUtils.countMatches(review, key)>1) {wordCounter=StringUtils.countMatches(review, key);}
						currentScore = currentScore + (wordLexicon.get(key)*wordCounter);
						reviewsList.get(i).setScore(currentScore);
						for (int j=0;j<wordCounter;j++) {      
							review=review.replace(key, "");	     
						}                                      
						
					}
				}
				
				for (String key : emojiLexicon.keySet()) 
				{   
					if (review.contains(key)) 
					{   
						int currentScore = reviewsList.get(i).getScore();
						int emojiCounter=1;
						if (StringUtils.countMatches(review, key)>1) {emojiCounter=StringUtils.countMatches(review, key);}
						currentScore = currentScore + (emojiLexicon.get(key)*emojiCounter);
						reviewsList.get(i).setScore(currentScore);
						for (int j=0;j<emojiCounter;j++) {     
							review=review.replace(key, "");	   
						}                                      
						
					}
				}
			
		}
		
	}

}
