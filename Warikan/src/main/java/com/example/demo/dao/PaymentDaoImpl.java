package com.example.demo.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Payment;


@Repository
public class PaymentDaoImpl implements PaymentDao {
	
	// JdbcTemplateクラスをDIコンテナから呼び出す
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PaymentDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//全件取得
	@Override
	public List<Payment> findAll() {
		String sql = "SELECT * FROM warikan.payment;";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		List<Payment> list = new ArrayList<Payment>();
		for (Map<String, Object> result : resultList) {
			Payment payment = new Payment();
			payment.setPayId((int) result.get("payId"));
			payment.setUserId((int) result.get("userId"));
			payment.setAmount((int) result.get("amount"));
			payment.setContent((String) result.get("content"));
			payment.setPayDay((Date) result.get("payDay"));

			list.add(payment);
		}
		return list;
	}
	
	//支払登録
	@Override
	public void save(Payment payment) {
		jdbcTemplate.update(
				"INSERT INTO warikan.payment (`userId`, `content`, `amount`, `payDay`) VALUES(?, ?, ?, ?);",
				payment.getUserId(), payment.getContent(), payment.getAmount(), payment.getPayDay());
	}
	
	//清算処理
	@Override
	public void truncate() {
		jdbcTemplate.update("TRUNCATE table payment");
	}

	//支払を１件取得
	@Override
	public Optional<Payment> findByPayId(int payId) {
		String sql = "SELECT * FROM warikan.payment WHERE payId = ?;";
		// 支払情報を一件取得(id=SQLの？部分に該当)
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, payId);
		
		Payment payment = new Payment();
		payment.setPayId((int) result.get("payId"));
		payment.setUserId((int) result.get("userId"));
		payment.setAmount((int) result.get("amount"));
		payment.setContent((String) result.get("content"));
		payment.setPayDay((Date) result.get("payDay"));
		
		Optional<Payment> paymentOptional = Optional.ofNullable(payment);
				return paymentOptional;
	}

	// 支払情報の編集
	@Override
	public void updatePayment(Payment payment) {
		jdbcTemplate.update(
				"UPDATE warikan.payment SET `userId` = ?, `content` = ?, `amount` = ?, `payDay` = ? WHERE (`payId` = ?);",
				payment.getUserId(), payment.getContent(), payment.getAmount(), payment.getPayDay(),  payment.getPayId());
	}

	//削除処理
	@Override
	public void deleteByPayId(int payId) {
		jdbcTemplate.update("DELETE FROM warikan.payment WHERE (`payId` = ?);", payId);
		//payIdの連番リセット
		jdbcTemplate.update("ALTER table payment drop column payId;");
		jdbcTemplate.update("ALTER table payment add payId int(10) primary key not null auto_increment first;");
		jdbcTemplate.update("ALTER TABLE payment AUTO_INCREMENT = 1;");
	}

	
	//合計額取得
	@Override
	public int getAmountSum() {
		String sql = "SELECT SUM(amount) FROM payment";
        return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	//ユーザー別の支払いの合計値を取得
	@Override
	public int getAmountSumByUserId(int userId) {
	     String sql = "SELECT SUM(amount) FROM payment WHERE userId = ?";
	        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

}