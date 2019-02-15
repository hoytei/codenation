package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import org.springframework.data.geo.Point;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private MongoOperations mongo;	
	@Autowired
	private RedisOperations<String, NeighborhoodRedis> redis;
	

	@Override
	public NeighborhoodRedis findInNeighborhood(double x, double y) {
		NeighborhoodMongo nmongo = findMongoNeighborhood(x, y);
		
		String key = "neighborhood:"+ nmongo.getId();
		
		NeighborhoodRedis nredis = redis.opsForValue().get(key);
		
		if(nredis == null) {
			createNewRedisNeighborhood(x, y, nmongo, key, nredis);
	    
		}
		
		return nredis;
	}


	private void createNewRedisNeighborhood(double x, double y, NeighborhoodMongo nmongo, String key,
			NeighborhoodRedis nredis) {
		
		nredis = new NeighborhoodRedis();
		nredis.setId(nmongo.getId());
		nredis.setName(nmongo.getName());
		
		nredis.setRestaurants(
				mongo.find(
		                query(where("location").within(nmongo.getGeometry()))
		                .with(Sort.by("name").ascending()),
		                RestaurantMongo.class)
		                .stream()
		                .sequential()
		                .map(this::convertMongoToRedis)
		                .collect(Collectors.toList()));
		
		redis.opsForValue().set(key, nredis);
	}
	
	private NeighborhoodMongo findMongoNeighborhood(double x, double y) { 
        return mongo.findOne(
                query(where("geometry").intersects(new GeoJsonPoint(new Point(x, y)))),
                NeighborhoodMongo.class);
    }
	
	private RestaurantRedis convertMongoToRedis(RestaurantMongo restaurantMongo) {
        final RestaurantRedis restaurantRedis = new RestaurantRedis();
        restaurantRedis.setId(restaurantMongo.getId());
        restaurantRedis.setName(restaurantMongo.getName());
        restaurantRedis.setX(restaurantMongo.getLocation().getX());
        restaurantRedis.setY(restaurantMongo.getLocation().getY());

        return restaurantRedis;
    }

	
}
