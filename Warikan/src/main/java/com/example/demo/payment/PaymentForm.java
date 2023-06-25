package com.example.demo.payment;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PaymentForm {

	@NotBlank(message="内容が未入力です")
	@Size(max = 20, message="20文字以内で入力してください")
	private String content;
	
	@NotBlank(message="金額が未入力です")
	@Size(max = 20, message="20文字以内で入力してください")
	private int amount;
	
	@NotBlank
	private LocalDate payDay;	

	//新規登録or編集モードの判定
	private boolean editMode;
	
	
	public PaymentForm() {}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getPayDay() {
		return payDay;
	}

	public void setPayDay(LocalDate payDay) {
		this.payDay = payDay;
	}
	
	public boolean getEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}