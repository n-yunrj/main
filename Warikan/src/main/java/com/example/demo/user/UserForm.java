package com.example.demo.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserForm {

	@NotBlank(message="お名前が未入力です")
	@Size(max = 20, message="20文字以内で入力してください")
	private String userName;
	
	@NotBlank(message="パスワードが未入力です")
	@Size(max = 20, message="20文字以内で入力してください")
	private String password;
	
	@NotBlank
	private boolean enabled;
	
	private String icon;

	public UserForm() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}