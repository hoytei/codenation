package challenge;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class RecipeComment {
	
	@Id
	private String id;
	private String comment;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
