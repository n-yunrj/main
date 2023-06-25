package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Payment;

@Repository
public interface PaymentDao  {
    
	//全件取得
	List<Payment> findAll();
	//支払登録
	void save(Payment payment);
	//清算処理
	public void truncate();
	//支払を１件取得
	Optional<Payment> findByPayId(int payId);
	// 支払情報の編集
	void updatePayment(Payment payment);
	//削除処理
	void deleteByPayId(int payId);
	//合計額取得
	int getAmountSum();
	//ユーザー別の支払いの合計値を取得
    int getAmountSumByUserId(int userId);

}
