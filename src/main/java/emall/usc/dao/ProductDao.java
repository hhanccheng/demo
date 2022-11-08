package emall.usc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emall.usc.beans.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
	
}

