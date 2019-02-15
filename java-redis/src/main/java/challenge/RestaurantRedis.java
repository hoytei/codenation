package challenge;

import java.io.Serializable;

/**
 * Classe para mapear o restaurante no Redis
 *
 */
public class RestaurantRedis implements Serializable {

	private String id;
    private String name;
    private Double x;
    private Double y;
    
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
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
    
    
	 

}
