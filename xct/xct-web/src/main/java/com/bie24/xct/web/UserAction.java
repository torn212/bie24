package com.bie24.xct.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/user")
public class UserAction{

	@RequestMapping(value="/add")
	public void add(HttpServletRequest request,HttpServletResponse response) {
		System.out.println(request.getRequestURL());
		try {
			response.getWriter().print("lk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
