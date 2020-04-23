package edu.web.board.persistance;

public interface BoardQuery {
	public static final String TABLE_NAME = "board";
	public static final String COL_BNO = "bno";
	public static final String COL_TITLE = "title";
	public static final String COL_CONTENT = "content";
	public static final String COL_USERID = "userid";
	public static final String COL_CDATE = "cdate";

	// insert into board values
	// (BOARD_PK.nextval, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD HH:MI:SS'));
	public static final String SQL_INSERT = "insert into " + TABLE_NAME
			+ " values(BOARD_PK.nextval, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD HH:MI:SS'))";

	// select * from board order by bno desc;
	public static final String SQL_SELECT_ALL = "select * from " + TABLE_NAME + " order by " + COL_BNO + " desc";

	// select * from board where bno = ?;
	public static final String SQL_SELECT = "select * from " + TABLE_NAME + " where " + COL_BNO + " = ?";

	// update board
	// set title = ?, content = ?, cdate = to_char(sysdate, 'YYYY-MM-DD HH:MI:SS')
	// where bno = ?;
	public static final String SQL_UPDATE = "update " + TABLE_NAME + " set " + COL_TITLE + " = ?, " + COL_CONTENT
			+ " = ?, " + COL_CDATE + " = to_char(sysdate, 'YYYY-MM-DD HH:MI:SS') where " + COL_BNO + " = ?";

	// delete from board where bno = ?
	public static final String SQL_DELETE = "delete from " + TABLE_NAME + " where " + COL_BNO + " = ?";

	// SELECT b.bno, b.title, b.content, b.userid, b.cdate
	// FROM (
	// SELECT ROWNUM rn, a.*
	// FROM (
	// SELECT * FROM board ORDER BY bno DESC
	// ) a
	// ) b WHERE rn BETWEEN ? AND ?;
	public static final String SQL_SELECT_PAGESCOPE = "SELECT b." + COL_BNO + ", b." + COL_TITLE + ", b." + COL_CONTENT
			+ ", b." + COL_USERID + ", b." + COL_CDATE + " FROM ( SELECT ROWNUM rn, a.* FROM ( SELECT * FROM "
			+ TABLE_NAME + " ORDER BY " + COL_BNO + " DESC ) a ) b WHERE rn BETWEEN ? AND ?";

	// select count(*) total_cnt from board
	public static final String SQL_TOTAL_CNT = "select count(*) total_cnt from " + TABLE_NAME;

} // end BoardQuery
