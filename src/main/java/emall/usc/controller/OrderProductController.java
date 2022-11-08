package emall.usc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emall.usc.beans.OrderProduct;
import emall.usc.http.Response;
import emall.usc.service.OrderProductService;

@RestController()
@RequestMapping("/orderproducts")
public class OrderProductController {
	@Autowired
	OrderProductService orderProductService;
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@GetMapping("/{id}")
	public OrderProduct getProduct(@PathVariable int id) {
		return orderProductService.getOrderProduct(id);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@GetMapping
	public List<OrderProduct> getOrderProducts() {
		return orderProductService.getOrderProducts();
	}
	
	/**
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@PostMapping
	public Response addOrderProduct(@RequestBody int amount, Product product) {
		return orderProductService.addOrderProduct(amount,product);
	}
	
	**/
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@PostMapping
	public Response addOrderProduct(@RequestBody OrderProduct orderProduct) {
		return orderProductService.addOrderProduct(orderProduct);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@PutMapping
	public Response changeOrderProduct(@RequestBody OrderProduct orderProduct) {
		return orderProductService.changeOrderProduct(orderProduct);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@DeleteMapping("/{id}")
	public Response deleteOrderProduct(@PathVariable int id) {
		return orderProductService.deleteOrderProduct(id);
	}
}

