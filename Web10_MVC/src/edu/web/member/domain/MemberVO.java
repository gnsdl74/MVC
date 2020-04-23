package edu.web.member.domain;

public class MemberVO {
	public static final String TABLE_MEMBER = "test_member";
	public static final String COL_USERID = "userid";
	public static final String COL_PASSWORD = "password";
	public static final String COL_EMAIL = "email";
	public static final String COL_ACTIVE = "active";
	
	private String userid;
	private String password;
	private String email;
	private String active; // 계정의 활성화 유무 - 휴면계정처리
	
	// 생성자
	public MemberVO() {}
	public MemberVO(String userid, String password, String email, String active) {
		super();
		this.userid = userid;
		this.password = password;
		this.email = email;
		this.active = active;
	}
	// end 생성자
	
	// getter, setter
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	// end getter, setter
	
} // end MemberVO