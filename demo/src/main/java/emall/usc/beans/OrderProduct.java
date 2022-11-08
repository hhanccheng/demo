package emall.usc.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // jpa or mapping
@Table(name = "usc_order_product")
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_PRODUCT_SEQ")
	@SequenceGenerator(name = "ORDER_PRODUCT_SEQ",sequenceName = "USC_ORDER_PRODUCT_SEQ",allocationSize = 1)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	@JsonIgnoreProperties("purchaseOrderProducts")
	Order order;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	Product product;
	@Column
	private int quantity;
	
	public OrderProduct() {
		super();
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public OrderProduct(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", productName=" + product.getName() + 
				", productPrice=" + product.getPrice() +", quantity=" + quantity +"]";
	}
	
}

