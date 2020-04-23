package edu.web.member.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.web.dbcp.connmgr.ConnMgr;
import edu.web.member.domain.MemberVO;

public class MemberDAOImple implements MemberDAO {
	private static MemberDAOImple instance;

	private MemberDAOImple() {}

	public static MemberDAOImple getInstance() {
		if (instance == null) {
			instance = new MemberDAOImple();
		}
		return instance;
	}

	@Override
	public MemberVO checkIdPw(String userid, String password) {
		MemberVO vo = null;
		
		Connection conn = ConnMgr.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(SQL_CHECK_ID_PW);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String email = rs.getString("email");
				String active = rs.getString("active");
				vo = new MemberVO(userid, password, email, active);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return vo;
	} // end checkIdPw()

} // end MemberDAOImple