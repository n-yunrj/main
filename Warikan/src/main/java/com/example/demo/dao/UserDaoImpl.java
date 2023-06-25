package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	private final JdbcTemplate jdbcTemplate;

	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public List<User> getAll() {
		String sql = "SELECT * FROM user";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<User> list = new ArrayList<User>();//List　Bean１
		for (Map<String, Object> result : resultList) {
			User user = new User();
			user.setUserId((int) result.get("userId"));
			user.setUserName((String) result.get("userName"));
			user.setPassword((String) result.get("password"));
			user.setEnabled((boolean) result.get("enabled"));
			user.setIcon((String) result.get("icon"));
			list.add(user);
		}
	return list;
	}

	
	@Override
    public User findByUserId(int userId) {
        String sql = "SELECT userName FROM user WHERE userId = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = jdbcTemplate.query(sql, rowMapper, userId);
        return users.isEmpty() ? null : users.get(0);
    }
	
	
	@Override
	public void deleteByUserId(int userId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
	//ユーザー情報の表示
	@Override
	public Optional<User> findById(int id) {
		String sql = "SELECT * FROM user WHERE userId = ?;";
		// タスクを一件取得(id=SQLの？部分に該当)
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

		User user = new User();
		user.setUserId((int) result.get("userId"));
		user.setUserName((String) result.get("userName"));
		user.setPassword((String) result.get("password"));
		user.setIcon((String) result.get("icon"));

		Optional<User> userOpt = Optional.ofNullable(user);
		return userOpt;
	}

	//投稿を編集
	@Transactional
	public void editById(User user) {
		//userテーブルの情報を更新
	    jdbcTemplate.update(
	    		"UPDATE payment SET `userName` = ?, `icon` = ? WHERE (`userId` = ?);",
	        user.getUserName(), user.getIcon(), user.getUserId());
	}
	
	
	public String getCurrentIconName(int userId) {
		String sql = "SELECT icon FROM userInfo WHERE userId = ?;";
	    String currentFileName = jdbcTemplate.queryForObject(sql, String.class, userId);
	    return currentFileName;
    }


	@Override
	public List<User> getAll(int userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	
	
	
}