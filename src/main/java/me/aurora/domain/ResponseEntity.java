package me.aurora.domain;

import java.util.HashMap;

/**
 * @author 郑杰
 * @date 2018/08/23 9:49:44
 */
public class ResponseEntity extends HashMap<String, Object>{
	private static final long serialVersionUID = 1L;

	public ResponseEntity() {
		put("code", 200);
		put("msg", "操作成功");
	}

	public static ResponseEntity error() {
		return error(1, "操作失败");
	}

	public static ResponseEntity error(String msg) {
		return error(500, msg);
	}

	public static ResponseEntity error(int code, String msg) {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.put("code", code);
		responseEntity.put("msg", msg);
		return responseEntity;
	}

	public static ResponseEntity ok(String msg) {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.put("msg", msg);
		return responseEntity;
	}

	public static ResponseEntity ok() {
		return new ResponseEntity();
	}
}
