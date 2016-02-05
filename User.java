///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Reddit.java
// File:             User.java
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
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class User {
	final private String name;
	final private Karma karma;
	private List<String> subscribed;
	private List<Post> posted;
	private List<Post> liked;
	private List<Post> disliked;

	public User (String name) {
		this.name = name;
		karma = new Karma();
		subscribed = new ArrayList<String>();
		posted = new ArrayList<Post>();	
		liked= new ArrayList<Post>();;
		disliked= new ArrayList<Post>();;

	}

	public String getName() {
		return this.name;
	}

	public Karma getKarma() {
		return this.karma;
	}
	/**
	 * Returns a copy of the list of subreddits the user is subscribed to; the list itself should not be exposed.
	 * @return List of user posted cubscribed
	 */
	public List<String> getSubscribed() {
		List<String> cpSubscribed = new ArrayList<String>();
		for(int i= 0; i< this.subscribed.size(); i++){
			cpSubscribed.add( subscribed.get(i));  
		}

		return cpSubscribed;
	}
	/**
	 * Returns a copy of the list of posts posted by the user; the list itself should not be exposed.
	 *
	 *@return List of user posted posts
	 */
	public List<Post> getPosted() {
		List<Post> cpPosted = new ArrayList<Post>();
		for(int i= 0; i< this.posted.size(); i++){
			cpPosted.add( posted.get(i));  
		}
		return cpPosted;


	}
	/**
	 * Returns a copy of the list of posts liked by the user; the list itself should not be exposed.
	 *
	 * 
	 */
	public List<Post> getLiked() {
		List<Post> cpLiked = new ArrayList<Post>();
		for(int i= 0; i< liked.size(); i++){
			cpLiked.add( liked.get(i));  
		}
		return cpLiked;

	}
	/**
	 * Returns a copy of the list of posts disliked by the user; the list itself should not be exposed.
	 *
	
	 */
	public List<Post> getDisliked() {
		List<Post> cpDisLiked = new ArrayList<Post>();
		for(int i= 0; i< this.disliked.size(); i++){
			cpDisLiked.add( disliked.get(i));  
		}
		return cpDisLiked;
	}
	/**
	 *Add the specified subreddit to the List of subscribed subreddits if the user 
	 *is not already subscribed. If the user is already subscribed, unsubscribe from the subreddit.
	 *
	 */
	public void subscribe(String subreddit) {
		if(subscribed.size() == 0){
			subscribed.add(subreddit);
		}

		else if (subscribed.contains(subreddit))
			unsubscribe(subreddit);
		else
			subscribed.add(subreddit);


	}
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 */
	public void unsubscribe(String subreddit) {
		if(subscribed.contains(subreddit)){	
			subscribed.remove(subreddit);
		}

	}
	/**
	 * Instantiate a new post with the appropriate parameters and add it to the list of posts posted by the user.
	 *
	 */
	public Post addPost(String subreddit, PostType type, String title) {
		Post post = new Post(this, subreddit, type, title);
		posted.add(post);
		//System.out.println();
		like(post);
		return post;
	}
	/**
	 * Upvote the post and add it to the List of liked posts if not already liked; else undo the like.
	 *If the post is currently disliked by the user, the dislike should be undone.
	 *
	 *
	 */
	public void like(Post post) {
		if(!liked.contains(post)&& !disliked.contains(post)){
			
			this.liked.add(post);
		    post.upvote();
		}
		    else if(liked.contains(post)){
			undoLike(post);
		}
		    else if(disliked.contains(post)){
			liked.add(post);
			undoDislike(post);
			post.upvote();
		}
	}
	/**
	 *Remove the post from the list of liked posts and update its karma appropriately.
	 *
	 *
	 */
	public void undoLike(Post post) {
		liked.remove(post);
		post.downvote();
		post.downvote();
	
	}
	/**
	 *Downvote the post and add it to the List of disliked posts if not already disliked; else undo the dislike.
	 *If the post is currently liked by the user, the like should be undone.
	 *
	 *
	 */
	public void dislike(Post post) {
		if(!disliked.contains(post) && !liked.contains(post)){		
			this.disliked.add(post);
			post.downvote();
		
		}	
			else if(disliked.contains(post)){	
			undoDislike(post);
		}
			else if(liked.contains(post)){
			undoLike(post);
			disliked.add(post);
			post.downvote();
		
		}
	}
	/**
	 *Remove the post from the list of disliked posts and update its karma appropriately.
	 *
	 */
	public void undoDislike(Post post) {
		disliked.remove(post);
		post.downvote();	
		post.upvote();

	}
}
