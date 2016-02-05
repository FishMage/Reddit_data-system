///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Reddit.java
// File:             Post.java
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
public class Post {
	final private User user;
	final private String subreddit;
	final private PostType type;
	final private String title;
	private int karma;

	public Post(User user, String subreddit, PostType type, String title) {
		
		this.type = type;
		this.title = title;
		this.subreddit = subreddit;
		this.user = user;
		
		
				
	}

	public void upvote() {
		user.getKarma().upvote(type);
		this.karma += 2;
		
	}

	public void downvote() {
		user.getKarma().downvote(type);
		this.karma -= 1;
	}

	public User getUser() {
		return this.user;
	}

	public String getSubreddit() {
		return this.subreddit;
	}

	public PostType getType() {
		return this.type;
	}

	public String getTitle() {
		return this.title;
	}

	public int getKarma() {
		return this.karma;
	}
}
