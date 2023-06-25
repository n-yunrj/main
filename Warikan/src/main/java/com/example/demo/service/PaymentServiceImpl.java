package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PaymentDao;
import com.example.demo.entity.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentDao paymentDao;
		
	@Autowired
	public PaymentServiceImpl(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}
	
	@Override
	public List<Payment> getAll() {
		return paymentDao.findAll();
	}

	@Override
	public void save(Payment payment) {
		paymentDao.save(payment);
	}
	
	//清算処理
	@Override
	public void liquidation() {
		paymentDao.truncate();
	}

	@Override
	public Optional<Payment> findByPayId(int payId) {
		return paymentDao.findByPayId(payId);
	}

	@Override
	public void updatePayment(Payment payment) {
		paymentDao.updatePayment(payment);
	}

	@Override
	public void deleteByPayId(int payId) {
		paymentDao.deleteByPayId(payId);

	}

	@Override
	public int getAmountSum() {
		return paymentDao.getAmountSum();
	}

	@Override
	public int getAmountSumByUserId(int userId) {
		return paymentDao.getAmountSumByUserId(userId);
	}
	
	public int getDifference(int userId) {
		int total = (paymentDao.getAmountSum() / 2 );
		int i_total = paymentDao.getAmountSumByUserId(userId);
		int difference = total - i_total;
		return difference;
	}

}
