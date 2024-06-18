package com.javalab.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javalab.dao.BoardDAO;
import com.javalab.dao.MemberDAO;
import com.javalab.vo.BoardVO;
import com.javalab.vo.MemberVO;

/**
 * 게시물 등록폼 제공 및 등록 처리
 * - doGet : 게시물 등록 jsp 화면 제공
 * - doPost : 게시물 등록(저장) 처리
 */
@WebServlet("/board")	// /board : url pattern
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BoardServlet() {
        super();
    }
	/**
	 * 게시물 등록 화면 제공
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardServlet doGet");
		// 게시물 등록 화면으로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/boardInsertForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * 회원 가입 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardServlet doPost");
		
		// message body 파라미터 인코딩
		request.setCharacterEncoding("utf-8");

		// 게시물 작성폼에서 파라미터 전달
	    String title = request.getParameter("title");		// 제목
	    String content = request.getParameter("content");	// 내용
	    
	    // 세션에서 로그인 사용자 아이디 갖고오기
	    HttpSession ses = request.getSession();
	    MemberVO memberVO = (MemberVO) ses.getAttribute("member");
	    String memberId = memberVO.getMemberId(); 			// 사용자Id
	    
	    BoardVO board = new BoardVO(title, content, memberId);
	    
	    // BoardDAO 호출
	    BoardDAO boardDAO = new BoardDAO();
	    int row = boardDAO.insertBoard(board);
	    
	    if(row > 0) {	// 게시물 정상 등록
	    	String contextPath = request.getContextPath();
	    	response.sendRedirect(contextPath + "/main.jsp");	// 임시 이동
	    }else {
	    	request.setAttribute("error", "게시물 작성에 실패했습니다.");
	    	RequestDispatcher rd = request.getRequestDispatcher("/boardInsertForm.jsp");
	    	rd.forward(request, response);	    	
	    }
	}	// end post	
}
