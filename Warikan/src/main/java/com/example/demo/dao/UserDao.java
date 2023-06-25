package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;

public interface UserDao {
	
	//全権取得
	List<User> getAll();
	
	//該当ユーザーのユーザー情報取得
	List<User> getAll(int userId);
	//
	User findByUserId(int userId);
	//ユーザー削除
	void deleteByUserId(int userId);
	//ユーザー情報の表示
	Optional<User> findById(int id);
	//ユーザー情報の編集
	void editById(User user);
	//現在のアイコン名取得
	String getCurrentIconName(int id);
	
}
