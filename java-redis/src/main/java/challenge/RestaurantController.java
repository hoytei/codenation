package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService service;

	@GetMapping(path="/findInNeighborhood")
	public NeighborhoodRedis findInNeighborhood(@RequestParam Double x, @RequestParam Double y) {
		return service.findInNeighborhood(x, y);
	}

}
