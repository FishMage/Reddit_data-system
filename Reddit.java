///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS 367 Program1
// Files:            RedditDB.java; User.java; 
//					 Reddit,java; Post,java; Karma.java
// Semester:         CS367 Spring 2015
//
// Author:           Siyu Chen(Richard)
// Email:            schen358@wisc.edu
// CS Login:         chens
// Lecturer's Name:  Jim Skrentny
// Lab Section:      
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;


public class Reddit  {
	private static RedditDB db = new RedditDB();
	private static  String loginUser = null;  // represents the current logging in user
	private static  boolean exitMainMenu = false; 
	private static List<File> file = new ArrayList();
	private static boolean invalidSubMenuCommand = false; // boolean value used
														// to loop the subMenu

	public static void main(String[]args) throws FileNotFoundException{
		boolean argsEnd = false;
		String newFileName = null;


		if(args.length == 0){
			String fileName = null;
			for(int i=0; i< args.length; i++){
				fileName += args[i]+" ";
			}
			System.out.println("Usage: java Reddit" + fileName);
			System.exit(0);
		}

		try{
			for(int i = 0; i< args.length; i++){
				File newFile = new File(args[i]);
				
				loadFile(newFile);
			}

		}
		catch (FileNotFoundException  E){
			System.out.println("File "+ newFileName+" not found.");
		}
		db.getUsers().add(0,db.addUser("admin")); // creat admin first
		db.getUsers().remove(db.getUsers().size()-1); 
				// move admin to the first one of the users List


		while(exitMainMenu== false){
			User curr = db.findUser(loginUser);
			printMainMenu();
			exitMainMenu = handleMainMenuInput(curr); //handle the input
		}
	}
	/**
	 * Load the file that passed by the command line arguments
	 *
	 */
	public static void loadFile(File f) throws FileNotFoundException{
		Scanner scnr = new Scanner(f);
		String userName = f.getName().replaceAll(".txt", "");
		User curr = db.addUser(userName);
		String[] subreddits = scnr.nextLine().split(", ");
		for(int i = 0; i<subreddits.length; i++){
			curr.subscribe(subreddits[i]); // load the first line

		}
		while(scnr.hasNextLine()){
			String content = "";
			String[] data = scnr.nextLine().split(", ");
			if(data.length>3){
			for(int i = 2; i< data.length; i++){
				if(i==data.length-1){
					content+=data[i];
					break;
				}
				 content += data[i]+", "; 
				 // situation that the content contains comma", "
				 
			}
			
			curr.addPost(data[0], PostType(data[1]), content);
			}
			else if(data.length<=3){
				curr.addPost(data[0], PostType(data[1]), data[2]);
				// situation that the content does not contain comma", "
			}
		}
	}

	/**
	 *Convert String to PostType
	 *@return the postType of the related String 
	 */

	private static PostType PostType(String s) {  //convert Sting to post type
	
		if (s != null) {
			PostType[] p  = PostType.values();

			for (int i = 0; i< p.length; i++) {
				if(p[i].valueOf(s)==PostType.LINK){
					return PostType.LINK;
				}
				if(p[i].valueOf(s)==PostType.SELF){
					return PostType.SELF;
				}
				if(p[i].valueOf(s)==PostType.COMMENT){
					return PostType.COMMENT;
				}
			}

		}
		return null;

	}
	/**
	 *print the [xxx@reddit]$ to realize interaction
	 *
	 */
	public static void printMainMenu(){

		if(loginUser==null){
			System.out.print("[anon@reddit]$ ");
		}
		else
			System.out.print("["+loginUser + "@reddit]$ ");

	}
	/**
	 *handle user's input
	 *@return true if needs to exit the submenu
	 */
	public static boolean handleSubMenuInput(Post post){
		Scanner scnr = new Scanner(System.in);
		String input = scnr.next();
		
		if(input.equals("a")){
			if(loginUser== null){
				System.out.println("Login to like post."); // no user logging in 
				invalidSubMenuCommand = true;
				return false;
			}
			db.findUser(loginUser).like(post);	 // current user like the post

			invalidSubMenuCommand = false;
			return false;
		}
		
		if(input.equals("z")){
			if(loginUser==null){
				System.out.println("Login to dislike post."); // no user logging in 
				invalidSubMenuCommand = true;
				return false;
			}
			
			db.findUser(loginUser).dislike(post);// current user dislike the post
			invalidSubMenuCommand = false;
			return false;
		}
		if(input.equals("j")){
			invalidSubMenuCommand = false;
			return false;
		}

		if(input.equals("x")){
			System.out.println("Exiting to the main menu...");
			invalidSubMenuCommand = false;
			return true;  // exit the sub menu is true
		}
		else{
			System.out.println("Invalid Command!"); 
			//invalidSubMenuCommand = false;
			invalidSubMenuCommand = true; // need another valid input
			return false;
		}

	}
	/**
	 *handle user's input
	 *@return true if needs to exit the mainmenu
	 */
	public static boolean handleMainMenuInput(User user) {

		Scanner scnr = new Scanner(System.in);
		String input = scnr.nextLine();
		if(input.equals("s")){  // summary 
			if(loginUser==null){
				System.out.println("Invalid command!");
				return false;
			}
			else if(loginUser.equals("admin")){
				Iterator itr = db.getUsers().iterator();

				while(itr.hasNext()){
					User curr = (User) itr.next();
					System.out.println(curr.getName()+"\t"+
							curr.getKarma().getLinkKarma()
							+"\t" + curr.getKarma().getCommentKarma());
				}
				return false;
			}

		}
		if(input.startsWith("d")){	//delete User
			if(loginUser == null||!loginUser.equals("admin") ){
				System.out.println("Invalid command!");
				return false;
			}
			else{
				String delUserName = input.substring(2);
				Iterator itr = db.getUsers().iterator();
				while(itr.hasNext()){
					User curr = (User) itr.next();
					if(curr.getName().equals(delUserName)){
						db.delUser(delUserName);
						System.out.println("User "+ delUserName+ " deleted");
						return false;
					}
				}
				System.out.println("User "+delUserName+" not found");
				return false;
			}
		}
		if(input.startsWith("l")){

			if(input.length()==1){
				if(loginUser!= null){
					System.out.println("User "+loginUser+" logged out");
					loginUser = null;
					return false;
				}
				else{
					System.out.println("No user logged in.");
					return false;
				}

			}
			else{
				String loginUserName = input.substring(2);
				boolean hasUser = false;
				Iterator itr = db.getUsers().iterator();
				while(itr.hasNext()){
					User curr = (User) itr.next();
					if(curr.getName().equals(loginUserName))
						hasUser = true;  // the user exist	
				}
				if(loginUserName.equals(loginUser)|| loginUser !=null){
					System.out.println("User " +loginUser+ " already logged in.");
				}//someone has already logged in 
				else if(loginUser == null && !hasUser ){ 
					//no one logged in but the logging in user does not exist
					System.out.println("User "+loginUserName+" not found.");
				}

				else {
					loginUser = loginUserName;
					System.out.println("User "+loginUserName+ " logged in.");
				}

				return false;
			}
		}

		if(input.equals("f")){
			List<Post> frontPage  = null;
			boolean exitSubMenu = false;
			User nullUser = new User("null");
			if(loginUser!=null){
				frontPage = db.getFrontpage(user); 
				}
			else{
				frontPage = db.getFrontpage(nullUser); 
			}
			System.out.println("Displaying the front page...");
			Iterator itr = frontPage.iterator();
			while(itr.hasNext() && !exitSubMenu){
				Post curr = (Post)itr.next();
				do{
					System.out.println(curr.getKarma()+"\t"+
							curr.getTitle());
					printMainMenu();
					exitSubMenu = handleSubMenuInput(curr);
				}while(invalidSubMenuCommand);
			}
			if(exitSubMenu){
				
				return false;
			}
			else
			System.out.println("No posts left to display");
			System.out.println("Exiting to the main menu...");
			return false;
			
		}
		if(input.startsWith("r")){
			String subredditname = input.substring(2);
			boolean exitSubMenu = false;
			List<Post> subReddit = null;
			User nullUser = new User("null");
			if(loginUser!=null){
				subReddit = db.getFrontpage(user, subredditname); 
				}
			else{
				subReddit = db.getFrontpage(nullUser,subredditname); 
			}
			
			System.out.println("Displaying /r/subreddit...");
			Iterator itr = subReddit.iterator();
			while(itr.hasNext() && !exitSubMenu){

				Post curr = (Post)itr.next();
				System.out.println(curr.getKarma()+"\t"+
						curr.getTitle());

				printMainMenu();
				exitSubMenu = handleSubMenuInput(curr);

			}
			System.out.println("No posts left to display");
			System.out.println("Exiting to the main menu...");
			
				return false;

			
		}
		if(input.startsWith("u")){
			boolean exitSubMenu = false;
			boolean hasUser = false;
			String userName = input.substring(2);
			
			Iterator itra = db.getUsers().iterator();
			while(itra.hasNext()){
				User curr = (User) itra.next();
				if(curr.getName().equals(userName)){
					hasUser = true;
				}
			}
			if(!hasUser){
				System.out.println("User "+userName+" not found.");
				return false;
			}
			List<Post> posted = db.findUser(userName).getPosted();
			Iterator itr = posted.iterator();
			System.out.println("Displaying /u/ "+userName+" ...");
			while(itr.hasNext()){
				Post curr = (Post)itr.next();
				System.out.println(curr.getKarma()+"\t"+
						curr.getTitle());
				printMainMenu();
				exitSubMenu = handleSubMenuInput(curr);
			}
			System.out.println("No posts left to display");
			System.out.println("Exiting to the main menu...");
			return false;
		}
		if(input.equals("x")){
			System.out.println("Exiting to the real world...");
			System.exit(0);
		}
		else{
			System.out.println("Invalid command!");
			return false;
		}

		return false;
	}
}
