///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Reddit.java
// File:             Karma.java
// Semester:         CS367 Spring 2015
//
// Author:           Siyu Chen(Richard)
// CS Login:         chens
// Lecturer's Name:  Jim Skrentny
// Lab Section:      
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
public class Karma {
	private int linkKarma;
	private int commentKarma;

	public Karma() {
		this.linkKarma = 0;
		this.commentKarma = 0;
	}

	public void upvote(PostType type) {
		if(type.equals(PostType.LINK))
		this.linkKarma+=2;
		else if(type.equals(PostType.COMMENT))
		this.commentKarma+=2;
	}

	public void downvote(PostType type) {
		if(type.equals(PostType.LINK))
		this.linkKarma-=1;
		else if(type.equals(PostType.COMMENT))
		this.commentKarma-=1;
		
	}

	public int getLinkKarma() {
		return this.linkKarma;
	}

	public int getCommentKarma() {
		return this.commentKarma;
	}
}
	