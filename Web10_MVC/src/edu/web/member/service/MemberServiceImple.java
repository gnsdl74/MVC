package edu.web.member.service;

import edu.web.member.domain.MemberVO;
import edu.web.member.persistance.MemberDAO;
import edu.web.member.persistance.MemberDAOImple;

public class MemberServiceImple implements MemberService {
	private static MemberServiceImple instance;
	private MemberDAO dao;
	
	private MemberServiceImple() {
		dao = MemberDAOImple.getInstance();
	}
	
	public static MemberServiceImple getInstance() {
		if(instance == null) {
			instance = new MemberServiceImple();
		}
		return instance;
	}
	
	// 매개변수로 받아온 userid와 password로 DB에 접근하는 메소드인 dao.checkIdPw() 호출 
	@Override
	public boolean loginCheck(String userid, String password) {
		MemberVO vo = dao.checkIdPw(userid, password);
		if(vo != null) {
			return true;
		} else {
			return false;
		}
		
	} // end loginCheck()

} // end MemberServiceImple