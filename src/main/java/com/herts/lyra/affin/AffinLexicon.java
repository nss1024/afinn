package com.herts.lyra.affin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AffinLexicon {
	
	/**
	 * The method will parse a text file containing a list of words and list of associated scores 
	 * producing a map of key value pairs corresponding to words and their scores.
	 * The file must contain a list of words and scores separated by space or tab.
	 * @param filePath - file path of the text file to be parsed
	 * @return - returns a map with String - Integer key value pairs , words are the keys, scores are the values
	 * @throws IOException if the file is not found
	 */
	
	public Map<String,Integer> generateAffinLexicon(String filePath)throws IOException
	{
		Map<String,Integer> affinLexicon = new HashMap<String,Integer>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line="";
		while((line=br.readLine())!=null) 
		{	
			
			String[] ws = line.trim().split("\\s+");			
			if (ws.length==2) {
			affinLexicon.put(ws[0], Integer.parseInt(ws[1]));
			
			}
		}
		
		return affinLexicon;
	}

	/**
	 * The method will parse a text file containing a list of expressions (multiple words) and list of associated scores 
	 * producing a map of key value pairs corresponding to the expressions and their scores.
	 * The file must contain a list of expressions and scores separated by space or tab.
	 * @param filePath - file path of the text file to be parsed
	 * @return - returns a map with String - Integer key value pairs , expressions are the keys, scores are the values
	 * @throws IOException if the file is not found
	 */
	public Map<String,Integer> generateAffinExpressionList(String filePath)throws IOException
	{
		Map<String,Integer> affinExpressionLexicon = new HashMap<String,Integer>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while(br.readLine()!=null) 
		{
			String[] ws = br.readLine().trim().split("\\s+");
			
			if (ws.length>2) {
			
				StringBuilder comment = new StringBuilder();
				for (int i =0;i<ws.length-1;i++) {comment.append(ws[i]+" ");}
				affinExpressionLexicon.put(comment.toString().toLowerCase(), Integer.parseInt(ws[ws.length-1]));
				
				}
		}
		
		return affinExpressionLexicon;
	}

}
