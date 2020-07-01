package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;


public class BoardDao {
		
		// 0. import java.sql.*;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			private String driver = "oracle.jdbc.driver.OracleDriver";
			private String url = "jdbc:oracle:thin:@localhost:1521:xe";
			private String id = "webdb";
			private String pw = "webdb";

			private void getConnection() {
				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName(driver);

					// 2. Connection 얻어오기
					conn = DriverManager.getConnection(url, id, pw);
					// System.out.println("접속성공");

				} catch (ClassNotFoundException e) {
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
			}

			public void close() {
				// 5. 자원정리
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
			}

			// 게시글 추가
			public int boardInsert(BoardVo vo) {
				int count = 0;
				getConnection();

				try {

					// 3. SQL문 준비 / 바인딩 / 실행
					String query = ""; // 쿼리문 문자열만들기, ? 주의
					query += " INSERT INTO board ";
					query += " VALUES (seq_no.nextval, ?, ?, ?, ?, ?, ?) ";
					// System.out.println(query);

					pstmt = conn.prepareStatement(query); // 물음표 값 넣기 전 쿼리로 만들기

					pstmt.setString(1, vo.getTitle()); // ?(물음표) 중 1번째, 순서중요
					pstmt.setString(2, vo.getContent()); 
					pstmt.setString(3, vo.getName()); 
					pstmt.setInt(4, vo.getHit());
					pstmt.setString(5, vo.getReg_date());
					pstmt.setInt(5, vo.getUser_no());
					count = pstmt.executeUpdate(); // 쿼리문 실행

					// 4.결과처리
					// System.out.println("[" + count + "건 추가되었습니다.]");

				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();
				return count;
			}
			
			//게시글 리스트(검색안할때)
			public List<BoardVo> getBoardList() {
				return getBoardList("");
			}

			//게시글 리스트(검색할때)
			public List<BoardVo> getBoardList(String keyword) {
				List<BoardVo> boardList = new ArrayList<BoardVo>();

				getConnection();

				try {

					// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
					String query = "";
					query += " select  no, ";
					query += "         title, ";
					query += "         content, ";
					query += "         name, ";
					query += "         hit, ";
					query += "         to_char(sysdate, 'YYYY-MM-DD') as date, ";
					query += "         user_no ";
					query += " from board ";

					if (keyword != "" || keyword == null) {
						query += " where title like ? ";
						query += " or content like ? ";
						query += " or name like ? ";
						query += " or date like ? ";
						pstmt = conn.prepareStatement(query); // 쿼리로 만들기

						pstmt.setString(1, '%' + keyword + '%'); 
						pstmt.setString(2, '%' + keyword + '%'); 
						pstmt.setString(3, '%' + keyword + '%'); 
						pstmt.setString(5, '%' + keyword + '%'); 
					} else {
						pstmt = conn.prepareStatement(query); // 쿼리로 만들기
					}

					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String name = rs.getString("name");
						int hit = rs.getInt("hit");
						String reg_date = rs.getString("reg_date");
						int user_no = rs.getInt("user_no");
						

						BoardVo boardVo = new BoardVo(no, title, content, name, hit, reg_date, user_no);
						boardList.add(boardVo);
					}

				} catch(SQLException e) {
					System.out.println("error:" + e);
				}

				close();

				return boardList;

			}
			
			// 게시글 삭제
			public int boardDelete(int no) {
				int count = 0;
				getConnection();

				try {
					// 3. SQL문 준비 / 바인딩 / 실행
					String query = ""; // 쿼리문 문자열만들기, ? 주의
					query += " delete from board ";
					query += " where no = ? ";
					pstmt = conn.prepareStatement(query); // 물음표 처리 전 쿼리로 만들기

					pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

					count = pstmt.executeUpdate(); // 쿼리문 실행

					// 4.결과처리
					// System.out.println(count + "건 삭제되었습니다.");

				} catch(SQLException e) {
					System.out.println("error:" + e);
				}

				close();
				return count;
			}
			
			//게시글 정보
			public BoardVo getBoard(int no) {
					
				BoardVo boardVo = null;
					
				getConnection();

				try {

					// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
					String query = "";
					query += " select  no, ";
					query += "         title, ";
					query += "         content, ";
					query += "         name, ";
					query += "         hit, ";
					query += "         to_char(sysdate, 'YYYY-MM-DD') as reg_date, ";
					query += "         user_no ";
					query += " from board ";
					query += " where no = ? ";
					
					pstmt = conn.prepareStatement(query); //  물음표 처리 전 쿼리로 만들기
					pstmt.setInt(1, no); // ?(물음표) 중 1번째 , 순서중요
			

					rs = pstmt.executeQuery();

					// 4.결과처리
					while(rs.next()) {
						int num = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");;
						String name = rs.getString("name");
						int hit = rs.getInt("hit");
						String reg_date = rs.getString("reg_date");
						int user_no = rs.getInt("user_no");

						boardVo = new BoardVo(no, title, content, name, hit, reg_date, user_no);
					}

				} catch(SQLException e) {
						System.out.println("error:" + e);
				}

				close();
				return boardVo;
			}

}
