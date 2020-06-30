package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.javaex.vo.GuestVo;


public class GuestBookDao {
	
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

		// 사람 추가
		public int guestInsert(GuestVo vo) {
			int count = 0;
			getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " INSERT INTO guestbook ";
				query += " VALUES (seq_no.nextval, ?, ?, ?, ?) ";
				// System.out.println(query);

				pstmt = conn.prepareStatement(query); // 물음표 값 넣기 전 쿼리로 만들기

				pstmt.setString(1, vo.getName()); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, vo.getPassword()); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, vo.getContent()); // ?(물음표) 중 3번째, 순서중요
				pstmt.setString(4, vo.getRegDate()); // ?(물음표) 중 3번째, 순서중요
				count = pstmt.executeUpdate(); // 쿼리문 실행

				// 4.결과처리
				// System.out.println("[" + count + "건 추가되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			return count;
		}
		
		//사람 리스트(검색안할때)
		public List<GuestVo> getGuestList() {
			return getGuestList("");
		}

		// 사람 리스트(검색할때)
		public List<GuestVo> getGuestList(String keyword) {
			List<GuestVo> guestList = new ArrayList<GuestVo>();

			getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
				String query = "";
				query += " select  no, ";
				query += "         name, ";
				query += "         replace(password, '*') as password, ";
				query += "         content, ";
				query += "         to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') as reg_date ";				
				query += " from guestbook";

				if (keyword != "" || keyword == null) {
					query += " where name like ? ";
					query += " or reg_date like ? ";
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기

					pstmt.setString(1, '%' + keyword + '%'); // ?(물음표) 중 1번째, 순서중요
					pstmt.setString(2, '%' + keyword + '%'); // ?(물음표) 중 2번째, 순서중요
					pstmt.setString(3, '%' + keyword + '%'); // ?(물음표) 중 3번째, 순서중요
				} else {
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				}

				rs = pstmt.executeQuery();

				// 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");

					GuestVo guestVo = new GuestVo(no, name, password, content, regDate);
					guestList.add(guestVo);
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();

			return guestList;

		}
		
		// 사람 삭제
		public int guestDelete(int no) {
			int count = 0;
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " delete from guestbook ";
				query += " where no = ? ";
				pstmt = conn.prepareStatement(query); // 물음표 처리 전 리로 만들기

				pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

				count = pstmt.executeUpdate(); // 쿼리문 실행

				// 4.결과처리
				// System.out.println(count + "건 삭제되었습니다.");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
			return count;
		}
		
		//사람정보
			public GuestVo getGuest (int no) {
				
				GuestVo guestVo = null;
				
				getConnection();

				try {

					// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
					String query = "";
					query += " select  no, ";
					query += "         name, ";
					query += "         password, ";
					query += "         content, ";
					query += "         to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') as reg_date ";				
					query += " from guestbook";
					query += " where no = ? ";
					
					pstmt = conn.prepareStatement(query); //  물음표 처리 전 리로 만들기
					pstmt.setInt(1, no); // ?(물음표) 중 1번째 , 순서중요
			

					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						int num = rs.getInt("no");
						String name = rs.getString("name");
						String password = rs.getString("password");
						String content = rs.getString("content");
						String regDate = rs.getString("reg_date");

						guestVo = new GuestVo(no, name, password, content, regDate);
					}

				} catch (SQLException e) {
					System.out.println("error:" + e);
				}

				close();
				return guestVo;
			}
		

}
