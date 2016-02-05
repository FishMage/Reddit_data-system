///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Reddit.java
// File:             RedditDB.java
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

public class RedditDB {
	private List<User> users;


	public RedditDB() {
		this.users = new ArrayList<User>();
	}

	public List<User> getUsers() {

		return users; 
	}

	public User addUser(String name) {
		if(!users.contains(name)){
			User newUser = new User(name);
			users.add(newUser);
			return newUser;
		}
		return null;
	}

	public User findUser(String name) {
		Iterator itr = getUsers().iterator();
		while(itr.hasNext()){
			User curr = (User)itr.next();
			if(curr.getName().equals(name)){
				return curr;
			}
		}
		return null;
	}

	public boolean delUser(String name) {
		List<Post> userPosted = new ArrayList();  // get each post
		boolean hasUser = false;
		int indexOfUser = 0;
		User user_del = null;
		for(int i = 0; i< users.size(); i++)
		{		
			if(name.equals(users.get(i).getName())){
				user_del  = users.get(i);
				userPosted = users.get(i).getPosted();	 // get all post 
				hasUser = true;						 // the delUser posted
			}

		}	
		for(int i = 0; i< user_del.getLiked().size(); i++){
			user_del.undoLike(user_del.getLiked().get(i));  
		}
		for(int j = 0; j< user_del.getDisliked().size(); j++){
			user_del.undoLike(user_del.getDisliked().get(j));  

		}
		for(int j = 0; j< users.size(); j++){
			for (int x = 0; x < userPosted.size(); x++){ 
				if(users.get(j).getLiked().contains(userPosted.get(x)))
					users.get(j).undoLike(userPosted.get(x));  
				// Undo all liked of the post
			}					
		}

		for(int k = 0; k< users.size(); k++){
			for (int y = 0; y <userPosted.size(); y++){ 
				if(users.get(k).getDisliked().contains(userPosted.get(y)))
					users.get(k).undoLike(userPosted.get(y));  
				// Undo all disliked of the post
			}					
		}

		return hasUser;


	}

	public List<Post> getFrontpage(User user) {
		List<Post> allPost =  new ArrayList(); // posts which will be return
		Post relevantPost = null;
		List<String> subreddits = null;// the subreddits that user has
		if(!user.getName().equals(null)){
		subreddits  = user.getSubscribed();
		}
		if(user.getName().equals("null")){
			for(int i = 0 ;i< users.size(); i++){
				for(int j = 0; j< users.get(i).getPosted().size(); j++)
					allPost.add( users.get(i).getPosted().get(j) ); 
				
			}	
		}
		else{

			for(int i = 0 ;i< users.size(); i++){// all user
				for(int j = 0; j< getUsers().get(i).getPosted().size(); j++){ 
					// all post posted by the user
					for(int k = 0; k< subreddits.size();k++){	
						//all subreddits that user subscribed
if(users.get(i).getPosted().get(j).getSubreddit().equals(subreddits.get(k))){
							relevantPost = users.get(i).getPosted().get(j);
			//relevant post which contains the subreddit that the user also has

							if(!user.getLiked().contains(relevantPost) &&
							   !user.getDisliked().contains(relevantPost)){
								if(!user.getPosted().contains(relevantPost))
									allPost.add(relevantPost);
								//not in disliked/liked, and not posted by user
							}
							if(user.getPosted().contains(relevantPost)){
								allPost.add(relevantPost);
								
							}
							 
						}
					}
				}
			}
		}


		return allPost;
	}

	public List<Post> getFrontpage(User user, String subreddit) {
		List<Post> allPost =  new ArrayList(); // posts which will be return
		Post relevantPost = null; 
		List<String> subreddits = null;   // the subreddits that user has
		if(!user.getName().equals(null)){
		subreddits  = user.getSubscribed();
		}
		if(user.getName().equals(null)){
			for(int i = 0 ;i<= users.size(); i++){
				for(int j = 0; j<= users.get(i).getPosted().size(); j++){
					if(users.get(i).getPosted().get(j).getSubreddit().equals(subreddit))
						allPost.add(users.get(i).getPosted().get(j));
					// get all post that have the subreddit
				}
			}
		}

		else{
			for(int i = 0 ;i< users.size(); i++){
				for(int j = 0; j< users.get(i).getPosted().size(); j++){
					if(users.get(i).getPosted().get(j).getSubreddit().equals(subreddit)){
						// loop all posts of certain user who have the subreddit
						relevantPost= users.get(i).getPosted().get(j);
						if(!user.getLiked().contains(relevantPost)||
								!user.getDisliked().contains(relevantPost)){
							allPost.add(relevantPost);
							// add the relevant Post

						}
						else if(user.getLiked().contains(relevantPost) && 
								user.getPosted().contains(relevantPost)){
							allPost.add(relevantPost);
						}
					}
				}
			}
		}
		return allPost;
	}
}
