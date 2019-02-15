package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurant")
public class RestaurantMongo {

	@Id
	private String id;
	private String name;
	private GeoJsonPoint location;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GeoJsonPoint getLocation() {
	    return location;
	}
	public void setLocation(GeoJsonPoint location) {
	    this.location = location;
	}
	
	
	
	

}
