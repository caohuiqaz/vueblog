package com.markerhub.common.lang;

import lombok.Data;
import java.io.Serializable;

@Data
public class Result implements Serializable{
	private int code; //200 is ok
	private String msg;
	private Object data;
	
	public static Result succ(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		
		return r;
	}
	
}
