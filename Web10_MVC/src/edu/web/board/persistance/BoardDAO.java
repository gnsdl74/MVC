package edu.web.board.persistance;

import java.util.List;

import edu.web.board.domain.BoardVO;
import edu.web.board.util.PageCriteria;

public interface BoardDAO {
	public abstract int insert(BoardVO vo);
	public abstract List<BoardVO> select();
	public abstract BoardVO select(int bno);
	public abstract int update(BoardVO vo);
	public abstract int delete(int bno);
	public abstract List<BoardVO> select(PageCriteria c); // 페이지 범위에 따른 게시글 선택 메소드
	public abstract int getTotalNums();
} // end BoardDAO
