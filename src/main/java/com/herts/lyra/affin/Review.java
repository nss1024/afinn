package com.herts.lyra.affin;
/*
 * Data object to hold each "row" of the parsed excel document.
 * Each comment of the excel file is converted into one of these objects
 * ScoreCalculator uses a list of this object to calculate scores for each comments and set the values of the score field.
 */


public class Review {
	
	private String date;
	private String userName;
	private String helpfulIndex;
	private String comment;
	private int score;
	
	
	public Review() {	
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHelpfulIndex() {
		return helpfulIndex;
	}

	public void setHelpfulIndex(String helpfulIndex) {
		this.helpfulIndex = helpfulIndex;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
