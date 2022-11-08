package emall.usc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emall.usc.beans.Order;
import emall.usc.beans.User;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	List<Order> findAllByUser(User user);

}
