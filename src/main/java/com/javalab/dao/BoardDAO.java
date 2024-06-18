package com.javalab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("게시물 등록중 오류가 발생했습니다.");
		}finally{
			closeResource();	// 자원해제(반납)
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
