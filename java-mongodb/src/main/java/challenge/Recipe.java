package challenge;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Recipe {	

	@Id
	private String id;
	private String title;
	private String description;
	List<Integer> likes;
	List<String> ingredients;
	List<RecipeComment> comment;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getLikes() {
		return likes;
	}
	public void setLikes(List<Integer> likes) {
		this.likes = likes;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public List<RecipeComment> getComment() {
		return comment;
	}
	public void setComment(List<RecipeComment> comment) {
		this.comment = comment;
	}
	
	
}
