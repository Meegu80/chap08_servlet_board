package com.javalab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.javalab.vo.BoardVO;
import com.javalab.vo.MemberVO;

/**
 * 게시물 관련 DAO
 * - 게시물 등록
 * - 게시물 목록/상세조회
 * - 게시물 수정/삭제
 */
public class BoardDAO {
	// 멤버 변수로 데이터베이스 관련 객체를 정의
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String DB_USER = "mboard";
	private static final String DB_PASSWORD = "1234";

	private Connection conn = null; // 커넥션 객체
	private PreparedStatement pstmt = null; // 쿼리문 생성 및 실행 객체
	private ResultSet rs = null; // 쿼리 실행 결과 반환 객체

	/**
	 * JDBC 드라이버 로딩과 커넥션 객체 생성
	 */
	public void connectDB() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(JDBC_DRIVER); // jdbc 드라이버 로딩
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 커넥션 객체 획득
			System.out.println("커넥션 객체 획득");			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 게시물 등록 처리 메소드
	 */
	public int insertBoard(BoardVO boardVO) {
		int row = 0;
		
		try{
			connectDB();
			
			String sql = "insert into board(bno, title, content, member_id, reg_date) ";
			sql += 		" values(seq_board.nextval, ?, ?, ?, sysdate) ";
			// PreparedStatment 객체 얻기
			pstmt = conn.prepareStatement(sql);
			// 쿼리문의 ? 에 파라미터 세팅
			pstmt.setString(1, boardVO.getTitle());		// 제목
			pstmt.setString(2, boardVO.getContent());	// 내용
			pstmt.setString(3, boardVO.getMemberId());	// 작성자			
			row = pstmt.executeUpdate();	// 저장처리
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("게시물 등록중 오류가 발생했습니다.");
		}finally{
			closeResource();	// 자원해제(반납)
		}		
		return row;
	}
	
	/**
	 * 게시물 목록 조회 메소드
	 */
	public List<BoardVO> getBoardList(){
		List<BoardVO> boardList = new ArrayList<>();
		try {
			connectDB();
			
			String sql = "select bno, title, content, member_id, reg_date, hit_no from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
                BoardVO boardVO = new BoardVO();
                boardVO.setBno(rs.getInt("bno"));
                boardVO.setTitle(rs.getString("title"));
                boardVO.setContent(rs.getString("content"));
                boardVO.setMemberId(rs.getString("member_id"));
                boardVO.setRegDate(rs.getDate("reg_date"));
                boardVO.setHitNo(rs.getInt("hit_no"));
                boardList.add(boardVO);			
			}
		}catch (SQLException | ClassNotFoundException e) {
			System.out.println("getBoardList ERR : " + e.getMessage());
			e.printStackTrace();	// 콘솔에 오류
		}finally {
			closeResource();
		}
		return boardList;
	}
	
	/**
	 * 게시물 내용 보기
	 */
	public BoardVO getBoard(int bno) {
		System.out.println("getBoard");
		BoardVO boardVO = null;
		
		try {
			connectDB();
	        // 게시물 조회 쿼리
	        String sql = "select bno, title, content, member_id, reg_date, hit_no from board where bno=? " ;        
	        pstmt = conn.prepareStatement(sql);	// PreparedStatement 객체 얻기(쿼리문 전달)
	        pstmt.setInt(1, bno);
	        rs = pstmt.executeQuery(); // 게시물 1건 반환
	        
			if(rs.next()){
				boardVO = new BoardVO();
				boardVO.setBno(rs.getInt("bno"));
				boardVO.setTitle(rs.getString("title"));
				boardVO.setContent(rs.getString("content"));		// 게시물 내용
				boardVO.setMemberId(rs.getString("member_id"));
				boardVO.setRegDate(rs.getDate("reg_date"));
				boardVO.setHitNo(rs.getInt("hit_no"));	// 조회수
			}        
	    } catch (SQLException | ClassNotFoundException e) {
	    	System.out.println("getBoard() ERR => " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        closeResource();
	    }  	
		return boardVO;
	}
	
	/**
	 * 게시물 조회수 증가 메소드
	 */
	public void incrementHitNo(int bno) {
		try {
			connectDB();
	        // 게시물의 조회수 증가 쿼리
	        // 조회수 증가 쿼리문 실행
	        String updateHitSql = "UPDATE board SET hit_no = hit_no + 1 WHERE bno = ?";
	        pstmt = conn.prepareStatement(updateHitSql);
	        pstmt.setInt(1, bno);
	        pstmt.executeUpdate();
	        pstmt.close();
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			closeResource();
		}
	}
	
	/**
	 * 게시물 수정
	 */
	public int updateBoard(BoardVO boardVO) {
		int row = 0;
		try {
			connectDB();
			String sql = "update board set title=?, content=? where bno=?" ;
	        
	        pstmt = conn.prepareStatement(sql);	// PreparedStatement 객체 얻기(쿼리문 전달)
	        pstmt.setString(1, boardVO.getTitle());		// title
	        pstmt.setString(2, boardVO.getContent());	// content
	        pstmt.setInt(3, boardVO.getBno());			// bno	
	        
	        row = pstmt.executeUpdate();	// 쿼리문 실행 영향 받은 행수 반환			
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			closeResource();
		}
		return row;
	}
	
	/**
	 * 게시물 삭제
	 */
	public int deleteBoard(int bno) {
		int row = 0;
		try {
			connectDB();
	       String sql = "delete board where bno=?" ;	// ? 동적파라미터
	        pstmt = conn.prepareStatement(sql);	// PreparedStatement 객체 얻기(쿼리문 전달)
	        pstmt.setInt(1, bno);			// bno	
	        row = pstmt.executeUpdate();	// 쿼리문 실행 영향 받은 행수 반환
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			closeResource();
		}
		return row;
	}
	
	
	/**
	 * 데이터베이스 관련 자원 해제(반납) 메소드
	 */
	private void closeResource() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
