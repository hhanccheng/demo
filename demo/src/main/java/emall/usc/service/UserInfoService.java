package emall.usc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import emall.usc.beans.UserInfo;
import emall.usc.dao.UserDao;
import emall.usc.dao.UserInfoDao;
import emall.usc.http.Response;

@Service
@Transactional
public class UserInfoService {
	@Autowired
	UserInfoDao userInfoDao;
	
	@Autowired
	UserDao userDao;
	
	public List<UserInfo> getUserInfos() {
		return userInfoDao.findAll();
	}
	
	public UserInfo getUserInfo(int id) {
		return userInfoDao.findById(id).get();
	}
	
	public Response addUserInfo(UserInfo userInfo,Authentication authentication) {
		userInfo.setUser(userDao.findByUsername(authentication.getName()));
		userInfo.setId(userDao.findByUsername(authentication.getName()));
		userInfoDao.save(userInfo);
		return new Response(true);
	}
	
	public Response changeUserInfo(UserInfo userInfo, Authentication authentication) {
		UserInfo ui = userInfoDao.findByUser(userDao.findByUsername(authentication.getName()));
		ui.setAddress(userInfo.getAddress());
		ui.setCity(userInfo.getCity());
		ui.setEmail(userInfo.getEmail());
		ui.setName(userInfo.getName());
		ui.setPhone(userInfo.getPhone());
		ui.setState(userInfo.getState());
		ui.setZip(userInfo.getZip());
		userInfoDao.save(ui);
		return new Response(true);
	}
	
	public Response deleteUserInfo(int id) {
		if (userInfoDao.findById(id) != null) {
			userInfoDao.deleteById(id);
			return new Response(true);
		} else {
			return new Response(false, "User is not found!");
		}
	}
	
}

