package com.example.assistant.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assistant.domain.member.dto.MemberResponse;
import com.example.assistant.domain.member.dto.SigninRequest;
import com.example.assistant.domain.member.dto.SignupRequest;
import com.example.assistant.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	/**
	 * 회원 가입 요청 정보를 수신하고 그 결과를 반환
	 * POST http://localhost:8080/api/members/singup
	 *
	 * Contents-type: application/json
	 *
	 * {
	 *     "email" : "hong-gd@gmail.com",
	 *     "password" : "password",
	 *     "name" : "홍길동",
	 *     "nickname" : "동에번쩍 서에번쩍"
	 * }
	 */
	@PostMapping("/signup")
	ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
		memberService.signup(
			signupRequest.getEmail(),
			signupRequest.getPassword(),
			signupRequest.getName(),
			signupRequest.getNickname()
		);
		return ResponseEntity.ok().build();
	}

	/**
	 * POST http://localhost:8080/api/members/singin
	 *
	 * Contents-type: application/json
	 *
	 * {
	 *     "email" : "hong-gd@gmail.com",
	 *     "password" : "password"
	 * }
	 */
	@PostMapping("/signin")
	ResponseEntity<String> signin(@RequestBody SigninRequest signinRequest) {
		String token = memberService.singin(signinRequest.getEmail(), signinRequest.getPassword());
		return ResponseEntity.ok().body(token);
	}

	@GetMapping("/my-page")
	ResponseEntity<MemberResponse> myPage(
		@AuthenticationPrincipal Long loginUserId
	) {
		MemberResponse memberResponse = memberService.findById(loginUserId);
		return ResponseEntity.ok().body(memberResponse);
	}
}
