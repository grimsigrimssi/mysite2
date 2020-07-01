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
					query += " INSERT INTO board_list ";
					query += " VALUES (seq_no.nextval, ?, ?, ?, ?, ?) ";
					// System.out.println(query);

					pstmt = conn.prepareStatement(query); // 물음표 값 넣기 전 쿼리로 만들기

					pstmt.setString(1, vo.getTitle()); // ?(물음표) 중 1번째, 순서중요
					pstmt.setString(2, vo.getName()); 
					pstmt.setInt(3, vo.getCount()); 
					pstmt.setString(4, vo.getDate());
					pstmt.setString(5, vo.getStatus());
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
					query += "         name, ";
					query += "         count, ";
					query += "         to_char(sysdate, 'YYYY-MM-DD') as date, ";
					query += "         status ";
					query += " from board_list";

					if (keyword != "" || keyword == null) {
						query += " where tilte like ? ";
						query += " or name like ? ";
						query += " or date like ? ";
						pstmt = conn.prepareStatement(query); // 쿼리로 만들기

						pstmt.setString(1, '%' + keyword + '%'); // ?(물음표) 중 1번째, 순서중요
						pstmt.setString(2, '%' + keyword + '%'); // ?(물음표) 중 2번째, 순서중요
						pstmt.setString(4, '%' + keyword + '%'); // ?(물음표) 중 3번째, 순서중요
					} else {
						pstmt = conn.prepareStatement(query); // 쿼리로 만들기
					}

					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String name = rs.getString("name");
						int count = rs.getInt("count");
						String date = rs.getString("date");
						String status = rs.getString("status");

						BoardVo boardVo = new BoardVo(no, title, name, count, date, status);
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
					query += " delete from board_list ";
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
					query += "         name, ";
					query += "         count, ";
					query += "         to_char(sysdate, 'YYYY-MM-DD') as date, ";
					query += "         status ";
					query += " from board_list";
					query += " where no = ? ";
					
					pstmt = conn.prepareStatement(query); //  물음표 처리 전 쿼리로 만들기
					pstmt.setInt(1, no); // ?(물음표) 중 1번째 , 순서중요
			

					rs = pstmt.executeQuery();

					// 4.결과처리
					while(rs.next()) {
						int num = rs.getInt("no");
						String title = rs.getString("title");
						String name = rs.getString("name");
						int count = rs.getInt("count");
						String date = rs.getString("date");
						String status = rs.getString("status");

						boardVo = new BoardVo(no, title, name, count, date, status);
					}

				} catch(SQLException e) {
						System.out.println("error:" + e);
				}

				close();
				return boardVo;
			}

}
