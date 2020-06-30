package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.UserVo;

public class UserDao {
	
	// 0. import java.sql.*;
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;

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

		private void close() {
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

		// 회원 추가
		public int insert(UserVo vo) { //결과를 확인하기 위하여 int로 count해 보려고 void 사용 대신 int로
			int count = 0;
			getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " INSERT INTO users ";
				query += " VALUES (seq_users_no.nextval, ?, ?, ?, ?) ";
				// System.out.println(query);

				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				
				pstmt.setString(1, vo.getId()); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(2, vo.getPassword()); // ?(물음표) 중 3번째, 순서중요
				pstmt.setString(3, vo.getName()); // ?(물음표) 중 3번째, 순서중요
				pstmt.setString(4, vo.getGender()); // ?(물음표) 중 3번째, 순서중요

				count = pstmt.executeUpdate(); // 쿼리문 실행

				// 4.결과처리
				// System.out.println("[" + count + "건 추가되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			return count; //첫 세팅시 오류 메세지 가리기 위해 return 0;으로 해 놓고 시작. 후에 변수 선언 받아 주어야 하므로 count로 바꾸어줌
		}
		
		//로그인한 사용자 가져오기
		public UserVo getUser(String id, String password) {
			UserVo vo = null; // 값이 없는 경우를 대비해 null로 세팅
			getConnection();
			
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " select no, name ";
				query += " from users ";
				query += " where id= ? ";
				query += " and password = ? ";
			

				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				
				pstmt.setString(1, id); //?(물음표) 중 2번째, 순서중요
				pstmt.setString(2, password);
				

				rs = pstmt.executeQuery(); // 쿼리문 실행

				// 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					
					vo = new UserVo();
					vo.setNo(no);
					vo.setName(name); //체크해 볼것!!!!
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			return vo;
		
		}
			
		//fhrmdlsgks tkdydwk rkwudhrl
		public UserVo getUser(int no) {
			UserVo vo = null;
			
			getConnection();
				
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " select no, id, password, name, gender ";
				query += " from users ";
				query += " where no= ? ";			

				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				
				pstmt.setInt(1, no); //?(물음표) 중 2번째, 순서중요
				

				rs = pstmt.executeQuery(); // 쿼리문 실행

			// 4.결과처리
			while (rs.next()) {
					int rNo = rs.getInt("no");
					String id = rs.getString("id");
					String password = rs.getString("password");
					String name = rs.getString("name");
					String gender = rs.getString("gender");
					
					vo = new UserVo(rNo, id, password, name, gender);						
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
				
			close();			
			return vo;
				
		}
		
		public int update(UserVo vo)  {
			int count = 0;
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " update users ";
				query += " set 	name = ?, ";
				query += "  	password = ?, ";
				query += "  	gender = ? ";
				query += " where no= ? ";			

				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setInt(4, vo.getNo());

				count = pstmt.executeUpdate(); // 쿼리문 실행

				
			}catch(SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			
			return 1;
		}
	}
		

