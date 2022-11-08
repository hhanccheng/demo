package emall.usc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emall.usc.beans.User;
import emall.usc.beans.UserInfo;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {
	UserInfo findByUser(User user);
}

