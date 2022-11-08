package emall.usc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emall.usc.beans.OrderProduct;

@Repository
public interface OrderProductDao extends JpaRepository<OrderProduct, Integer> {
	
}
