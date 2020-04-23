package edu.web.member.persistance;

import static edu.web.member.domain.MemberVO.*;
import edu.web.member.domain.MemberVO;

public interface MemberDAO {
	public static final String SQL_CHECK_ID_PW = "select * from " + TABLE_MEMBER + " where " + COL_USERID + " = ? and " + COL_PASSWORD + " = ?";
	
	public abstract MemberVO checkIdPw(String userid, String password);
} // end MemberDAO