package emall.usc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emall.usc.beans.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer>{
	User findByUsername(String username);
	
}
