package com.example.demo.payment;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.register.EditForm;
import com.example.demo.service.PaymentServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class PaymentController {
    
	@Autowired
    private PaymentServiceImpl paymentService;
	
	
	// 基本のトップページ処理
	@GetMapping
	public String home(PaymentForm paymentForm,
			//HttpSession session,
			Model model) {
		List<Payment> list = paymentService.getAll();
		paymentForm.setEditMode(false);
		
		// UserオブジェクトからuserNameを取得
		//User user = (User) session.getAttribute("user");
		//String loginUserName = user.getUserName();
		
		//仮のuserIDとuserName
		int userId = 1;
		String userName = "test";
		
        
        // 日付でソート
        Collections.sort(list, Comparator.comparing(Payment::getPayDay).reversed());
		
		int difference = paymentService.getDifference(userId);
	    
	    model.addAttribute("paymentList", list);
	    model.addAttribute("difference", difference);
	    model.addAttribute("title", "割り勘アプリケーション");
	    
		return "/home";
	}
	
	
	//金額登録画面へ
	@GetMapping("/payment")
	public String getPaymentForm(
			PaymentForm paymentForm,
			Model model
			) {
		model.addAttribute("title", "金額登録");
		return "/paymentForm";
	}
	
	
	/**
	 * 金額登録処理
	 * 
	 * @param paymentForm 金額登録フォーム
	 * @param result      バリデーションエラーチェック用
	 * @param model
	 * @return
     * @throws Exception
	 * 
	 */	
	@PostMapping("/payment")
	public String post(
			@Validated PaymentForm paymentForm,
			@RequestParam("payDay") LocalDate payDay,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
			// HttpSession session
			) throws IOException {
		
		if (result.hasErrors()) {
			// resultがエラーを返してきた場合(バリデーション発動)
			List<Payment> list = paymentService.getAll();
			model.addAttribute("paymentAllList", list);
			return "/paymentForm";
			
		} else {
			// Userオブジェクトからuser情報を取得
			//User user = (User) session.getAttribute("user");
			//int userId = user.getUserId();
			
			//仮のuserIDとuserName
			int userId = 1;
			String userName = "test";
			
			Date sqlDate = Date.valueOf(payDay);
			
	    	// 登録処理
	    	Payment payment = new Payment();
	    	payment.setUserId(userId);
	    	payment.setContent(paymentForm.getContent());
	    	payment.setAmount(paymentForm.getAmount());
	    	payment.setPayDay(sqlDate);
			
			paymentService.save(payment);
			
			redirectAttributes.addFlashAttribute("complete", "登録しました");
			return "redirect:/home";
		}
	}
	
	
	
	// 清算処理
	@PostMapping("/liquidation")
	public String liquidation(PaymentForm paymentForm,
			Model model) {
			
			paymentService.liquidation();
	
			return "redirect:/home";
		}
	
	
	/**
	 * ユーザー設定画面へ
	 * 遷移とともにログイン中のユーザネームとアイコンを表示する
	 */
	@PostMapping("/home/setting")
	public String edit(SettingForm settingForm,
			Model model, 
			HttpSession session) {
		
		// セッションのUserオブジェクトからidを取得
		User user = (User) session.getAttribute("user");
		int loginId = user.getId();
		
		//ユーザー情報の表示
		List<User> list = userService.getAll(loginId);
		model.addAttribute("title", "ユーザー情報");
		model.addAttribute("userList", list);

		
		//DBからユーザー情報を取得してフォームに表示
		//Userを取得
		Optional<User> userOpt = userService.getUser(loginId);
		//nullでなければ中身とりだし
		if (userOpt.isPresent()) {
			user.getId();
			editForm.setUserId(userOpt.get().getUserId());
			editForm.setUserName(userOpt.get().getUserName());
			}

		model.addAttribute("editForm", editForm);
		return "home/edit";
	}
	
	//編集後、元の画面に戻ってくる
	@GetMapping("/home/edit")
	public String goBackEdit(EditForm editForm,
			Model model, 
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		// Userオブジェクトからidを取得
		User user = (User) session.getAttribute("user");
		int loginId = user.getId();
		
	
		//DBからユーザー情報を取得してフォームに表示
		//Userを取得
		Optional<User> userOpt = userService.getUser(loginId);
		
		//nullでなければ中身とりだし
		if (userOpt.isPresent()) {
			user.getId();
			editForm.setUserId(userOpt.get().getUserId());
			editForm.setUserName(userOpt.get().getUserName());
			}
		
		//ユーザー情報の表示
		List<User> list = userService.getAll(loginId);
		model.addAttribute("title", "ユーザー情報");
		model.addAttribute("userList", list);

		redirectAttributes.addFlashAttribute("complete","ユーザー情報を変更しました");
		model.addAttribute("editForm", editForm);
		return "home/edit";
	}
	
	
	
	
	// 削除処理
	@PostMapping("/delete")
	public String delete(PaymentForm paymentForm,
			@RequestParam("payId") int payId,
			@RequestParam("userId") int userId,
			Model model) {
		
		paymentService.deleteByPayId(payId);
		
		return "redirect:/home";
	}
	
    
   
    
}
