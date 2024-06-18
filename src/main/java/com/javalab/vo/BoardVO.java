package com.javalab.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시물 자바 빈즈 클래스
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class BoardVO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private int bno;	// 게시물번호	
	private String title;	// 게시물 제목
	private String content;	// 게시물 내용
	private String memberId;// 게시물 작성자ID
	private int hitNo;		// 조회수
	private Date regDate ;	// 게시물 작성일자
	
	// 필요에 의해서 만든 생성자
	public BoardVO(String title, String content, String memberId) {
		this.title = title;
		this.content = content;
		this.memberId = memberId;
	}	
}
