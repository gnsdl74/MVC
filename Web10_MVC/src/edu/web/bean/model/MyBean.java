package edu.web.bean.model;

public class MyBean {
	private int myNumber;
	private String myName;
	
	// 생성자
	public MyBean() {
		this.myNumber = 100;
		this.myName = "Go";
	}
	public MyBean(int myNumber, String myName) {
		super();
		this.myNumber = myNumber;
		this.myName = myName;
	}
	// end 생성자

	// getter, setter
	public int getMyNumber() {
		return myNumber;
	}
	public void setMyNumber(int myNumber) {
		this.myNumber = myNumber;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	// end getter, setter
	
	
} // end MyBean
