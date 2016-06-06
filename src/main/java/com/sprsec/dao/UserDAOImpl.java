package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sprsec.model.Role;
import com.sprsec.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	public User getUser(String login) {
		List<User> userList = new ArrayList<User>();
		Query query = openSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", login);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;	
	}
	
	public void store(User user) {
		List<Role> roles = new ArrayList<Role>();
		Query query = openSession().createQuery("from Role r where r.id = 1");
		roles = query.list();
		if (roles.size() > 0)
			user.setRole(roles.get(0));
		openSession().persist(user);
	}
	
	public void update(int id, String login, String password, Role role) {
		List<User> userList = new ArrayList<User>();
		User user = new User();
		Query query = openSession().createQuery("from User u where u.id = :id");
		query.setParameter("id", id);
		userList = query.list();
		if(userList.size() > 0) {
			user = userList.get(0);
			user.setLogin(login);
			user.setPassword(password);
			user.setRole(role);
		}
		openSession().update(user);
			
	}

}
