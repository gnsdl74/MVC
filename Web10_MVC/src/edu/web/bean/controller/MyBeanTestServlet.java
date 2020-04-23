package edu.web.bean.controller;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.web.bean.model.MyBean;

@WebServlet("/MyBeanTestServlet")
public class MyBeanTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyBeanTestServlet() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyBean bean1 = new MyBean();
		System.out.println("num = " + bean1.getMyNumber());
		System.out.println("name = " + bean1.getMyName());
		
		// Tomcat 서버가 미리 생성한 MyBean 객체를 얻어옴
		// JNDI : Java Naming and Directory Interface
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			MyBean bean2 = (MyBean) envContext.lookup("bean/MyBeanFactory");
			System.out.println("bean2 = " + bean2);
			System.out.println("bean2.num = " + bean2.getMyNumber());
			System.out.println("bean2.name = " + bean2.getMyName());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
