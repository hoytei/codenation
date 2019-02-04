package challenge;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private MongoOperations mongo;
	
	
	@Override
	public Recipe save(Recipe recipe) {
		mongo.insert(recipe);
		return recipe;
	}

	@Override
	public void update(String id, Recipe recipe) {
		mongo.updateFirst(query(where("_id").is(id)), 
						  Update.update("title", recipe.getTitle()).set("description", recipe.getDescription()).set("ingredients", recipe.getIngredients()), 
						  Recipe.class);
	}

	@Override
	public void delete(String id) {
		mongo.remove(mongo.findById(id, Recipe.class));
	}

	@Override
	public Recipe get(String id) {
		return mongo.findById(id, Recipe.class);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {
		return mongo.find(query(where("ingredients").in(ingredient))
				          .with(Sort.by("title").ascending()), Recipe.class);
	}

	@Override
	public List<Recipe> search(String search) {
		final Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);

        return mongo.find(
                query(new Criteria().orOperator(
                        where("title").regex(pattern),
                        where("description").regex(pattern))
                ).with(Sort.by("title").ascending()),

                Recipe.class
        );
	}

	@Override
	public void like(String id, String userId) {
		mongo.updateFirst(query(where("_id").is(id)), 
				new Update().addToSet("likes", userId), 
				  Recipe.class);
	}

	@Override
	public void unlike(String id, String userId) {
		mongo.updateFirst(query(where("_id").is(id)), 
				new Update().pull("likes", userId), 
				  Recipe.class);
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		comment.setId(ObjectId.get().toHexString());

        mongo.updateFirst(
                query(where("_id").is(id)),
                new Update().addToSet("comments", comment),
                Recipe.class
        );

        return comment;
	}

	@Override
    public void updateComment(String id, String commentId, RecipeComment comment) {

        mongo.updateFirst(
                query(where("_id").is(id).and("comments._id").is(commentId)),
                Update.update("comments.$.comment", comment.getComment()),
                Recipe.class
        );

    }

    @Override
    public void deleteComment(String id, String commentId) {

        mongo.updateFirst(
                query(where("_id").is(id)),
                new Update().pull("comments", query(where("_id").is(commentId))),
                Recipe.class
        );
    }

}
