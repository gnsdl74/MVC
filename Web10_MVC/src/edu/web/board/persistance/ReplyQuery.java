package edu.web.board.persistance;

public interface ReplyQuery {
	public static final String TABLE_NAME = "reply";
	public static final String COL_RNO = "rno";
	public static final String COL_BNO = "bno";
	public static final String COL_CONTENT = "content";
	public static final String COL_REPLYID = "replyid";
	public static final String COL_CDATE = "cdate";

	// 게시글 하나당 댓글테이블을 사용하면 DB부담이 크고 비효율적 게시글 100개에 댓글 DB 100개 필요.
	// 댓글 DB 하나를 만든 후 아래의 방법으로 사용
	// bno(게시글 번호)로 댓글을 검색한 후 rno(댓글번호)로 정렬시켜 댓글을 순서대로 검색

	// insert into reply values(reply_pk.nextval, ?, ?, ?, to_char(sysdate,
	// 'YYYY-MM-DD HH:MI:SS'));
	public static final String SQL_REPLY_INSERT = "insert into " + TABLE_NAME
			+ " values(reply_pk.nextval, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD HH:MI:SS'))";

	// select * from reply where bno = ? order by rno asc;
	public static final String SQL_REPLY_SELECT = "select * from " + TABLE_NAME + " where " + COL_BNO + " = ? order by "
			+ COL_RNO + " asc";

	// update reply set content = ?, cdate = to_char(sysdate, 'YYYY-MM-DD HH:MI:SS')
	// where rno = ? and bno = ?;
	public static final String SQL_REPLY_UPDATE = "update " + TABLE_NAME + " set " + COL_CONTENT + " = ?, " + COL_CDATE
			+ " = to_char(sysdate, 'YYYY-MM-DD HH:MI:SS') where " + COL_RNO + " = ? and " + COL_BNO + " = ?";

	// delete from reply where rno = ? and bno = ?;
	public static final String SQL_REPLY_DELETE = "delete from " + TABLE_NAME + " where " + COL_RNO + " = ? and "
			+ COL_BNO + " = ?";

} // end ReplyQuery