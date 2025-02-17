package com.dnd.backend.support.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.backend.support.response.code.ServerErrorCode;
import com.dnd.backend.support.response.code.SuccessCode;

@RestController
@RequestMapping("/testResponse")
public class TestResponseApi {

	@GetMapping("/normal")
	public Map<String, String> normal() {
		var returnMap = new HashMap<String, String>();
		returnMap.put("greeting", "hi, there");
		return returnMap;
	}

	@GetMapping("/exception")
	public void exception() {
		throw new RuntimeException("처리 중 에러가 발생했습니다.");
	}

	@GetMapping("/type/success")
	public Response.Success success() {
		return new Response.Success(
			SuccessCode.OK,
			new MockResult("matthew", 23));
	}

	@GetMapping("/type/fail")
	public Response.Fail fail() {
		return new Response.Fail(
			ServerErrorCode.INTERNAL_ERROR,
			"thif is fail message",
			new MockResult("matthew", 23),
			List.of(new Response.ErrorDetail("maa", "에러필드테스트")));
	}

	@GetMapping("/string")
	public String string() {
		return "this is String";
	}

	@GetMapping("/responseEntity/string")
	public ResponseEntity<String> entityString() {
		return ResponseEntity.ok().body("entity string");
	}

	@GetMapping("/responseEntity/object")
	public ResponseEntity<MockResult> entityObject() {
		return ResponseEntity.ok().body(new MockResult("matthew", 23));
	}

	record MockResult(String name, Integer age) {
	}
}


















