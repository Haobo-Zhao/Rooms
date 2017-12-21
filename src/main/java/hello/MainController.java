package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping(path="/")
	public String getAllPosts(Model model) {
		model.addAttribute("posts", postRepository.findAll());
		return "index";
	}
	
	@GetMapping(path="/show/{id}")
	public String showThisOne(@PathVariable int id, Model model) {
		Iterable<Post> posts = postRepository.findAll();
		for (Post post : posts) {
			if (post.getId() == id) {
				model.addAttribute("post", post);
//				if (commentRepository.findAll() == null || !commentRepository.findAll().iterator().hasNext()) {
//					break;
//				} else {
					Iterable<Comment> allComments = commentRepository.findAll();
					List<Comment> comments = new ArrayList<>();
					for (Comment commentOfThisPost : allComments) {
						if (commentOfThisPost.getPostId() == post.getId()) {
							comments.add(commentOfThisPost);
						}
					}
					model.addAttribute("comments", comments);
					break;
//				}
			}
		}
		
		return "showPost";
	}
	
	@PostMapping(path="/create" ) // Map ONLY POST Requests
	public String addNewPost (Post post, HttpServletRequest request) {
		post.setDate(new Date().toString());
		for(User user : userRepository.findAll() ) {
			if (user.getUsername().equals(post.getUsername())) {
				post.setContact(user.getEmail() + "<br>" + post.getContact());
				break;
			}
		}
		postRepository.save(post);
		request.setAttribute("name", post.getId());
		return "forward:/upload";
	}
	
	@GetMapping(path="/create")	  // // Map ONLY GET Requests
	public String createNewJob() {
		return "create";
	}
	
	@GetMapping(path="/post/{id}/edit")	  // // Map ONLY GET Requests
	public String createNewJob(@PathVariable int id, Model model) {
		Iterable<Post> posts = postRepository.findAll();
		for (Post post : posts) {
			if (post.getId() == id) {
				model.addAttribute("post", post);
				break;
			}
		}
		return "edit";
	}
	
	@PostMapping(path="/post/{id}/edit") // Post MAPPING
	public String createNewJob(@PathVariable int id, Post editedPost, Model model) {
		editedPost.setDate(new Date().toString());
		editedPost.setId(id);
		postRepository.save(editedPost);
		
		model.addAttribute("posts", postRepository.findAll());
		return "redirect:/";
	}
	
	
//	 DESTROY
	@GetMapping(path="/post/{id}/delete")	  // // Map ONLY GET Requests
	public String getDeletePage(@PathVariable int id, Model model) {
//		Iterable<Post> posts = postRepository.findAll();
//		for (Post post : posts) {
//			if (post.getId() == id) {
//				model.addAttribute("post", post);
//				break;
//			}
//		}
//		return "index";
		
		for (Post post : postRepository.findAll()) {
			if (post.getId() == id) {
				postRepository.delete(post);
				break;
			}
		}
		
		model.addAttribute("posts", postRepository.findAll());
		return "redirect:/";
	}
	
//	@PostMapping(path="/post/{id}/delete") // Post MAPPING
//	public String deletePost(@PathVariable int id, Model model) {
//		for (Post post : postRepository.findById(id)) {
//			postRepository.delete(post);
//		}
//		
//		model.addAttribute("posts", postRepository.findAll());
//		return "index";
//	}
	
	
	
	
	
	
	
	// new user
	@PostMapping(path="/user" ) // Map ONLY POST Requests
	public String newUser (User user) {
		user.setDate(new Date().toString());
		userRepository.save(user);
		return "redirect:/user/add/" + user.getUsername() + "/" + user.getPassword();
	}
	
	@GetMapping(path="/user") // Map ONLY GET Requests
	public String register() {
		return "register";
	}
	
	@GetMapping(path="/showUser")
	public String showUsers(Model model) {
		Iterable<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "showUser";
	}
	
	@RequestMapping(path="/login")
	public String login(Model model) {
		return "login";
	}
	
	// logout
	@GetMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	
	
	
	//	comment
	@PostMapping(path="/post/{idOfPost}/comment") // Post MAPPING
	public String createNewcomment(@PathVariable int idOfPost, Comment comment) {
		comment.setDate(new Date().toString());
		commentRepository.save(comment);
		return "redirect:/show/" + idOfPost;
	}
	
	@GetMapping(path="/post/{idOfPost}/comment/edit/{commentId}") // Get and show the edit page
	public String editComment(@PathVariable int idOfPost, @PathVariable int commentId, Model model) {
		Iterable<Post> posts = postRepository.findAll();
		for (Post post : posts) {
			if (post.getId() == idOfPost) {
				model.addAttribute("post", post);
//				if (commentRepository.findAll() == null || !commentRepository.findAll().iterator().hasNext()) {
//					break;
//				} else {
					Iterable<Comment> allComments = commentRepository.findAll();
					List<Comment> comments = new ArrayList<>();
					for (Comment commentOfThisPost : allComments) {
						if (commentOfThisPost.getPostId() == post.getId()) {
							comments.add(commentOfThisPost);
						}
					}
					model.addAttribute("comments", comments);
					model.addAttribute("commentId", commentId);
					break;
//				}
			}
		}
		return "editComment";
	}
	
	
	@PostMapping(path="/post/{idOfPost}/comment/edit/{commentId}/save")
	public String saveEditedComment(@PathVariable int idOfPost, @PathVariable int commentId,@RequestParam("commentDescription") String commentDescription) {
		for (Comment comment : commentRepository.findAll()) {
			if (comment.getId() == commentId) {
				comment.setDescription(commentDescription);
				comment.setDate(new Date().toString());
				commentRepository.save(comment);
				break;
			}
		}
		return "redirect:/show/" + idOfPost;
	}
	
	
	@PostMapping(path="/post/{idOfPost}/comment/delete/{commentId}") // Post MAPPING
	public String createNewcomment(@PathVariable int idOfPost, @PathVariable int commentId) {
		for (Comment comment : commentRepository.findAll()) {
			if (comment.getId() == commentId) {
				commentRepository.delete(comment);
				break;
			}
		}
		return "redirect:/show/" + idOfPost;
	}
	
	@GetMapping(path="/contact")
	public String doContactUs() {
		return "/contactus";
	}
	
	@GetMapping(path="/about")
	public String doAboutUs() {
		return "/aboutus";
	}
}

