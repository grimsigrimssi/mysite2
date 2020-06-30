package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user") //1
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //3
		String action = request.getParameter("action"); //2
		
		if( "joinForm".equals(action) ) { //4
			System.out.println("joinForm"); //요청을 받을 수 있는 지 확인
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if ( "join".equals(action) ) {//회원가입
			System.out.println("join");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo(id, password, name, gender);
			System.out.println(vo.toString());
			
			UserDao userDao = new UserDao();
			userDao.insert(vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if ( "loginForm".equals(action) ) { //로그인 폼
			System.out.println("loginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if ( "login".equals(action) ) {
			System.out.println("login");
			
			String id = request.getParameter("id"); //파라미터로 온 값을 꺼내써야 하므로
			String password = request.getParameter("password");
			
			UserDao userDao =new UserDao();
			UserVo authVo = userDao.getUser(id, password); //로그인 되어서 authVo로 바꿈
			
			//System.out.println(userVo.toString()); 테스트용
			
			if(authVo == null) { //로그인실패
				System.out.println("로그인실패");
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
				
			}else {	//로그인 성공							
				//세션영역에 값을 추가 
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo); //id pw가 동시에 일치하는 유저의 정보가 들어간 메모리 공간 형성
				
				
				WebUtil.redirect(request, response, "/mysite2/main");
			}
			
		}else if( "logout".equals(action) ) {//로그아웃일 때
			System.out.println("logout");
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}else if ( "modifyForm".equals(action) ) { //로그인 폼
			System.out.println("modifyForm");
			
			HttpSession session = request.getSession();
			//UserVo vo = (UserVo)session.getAttribute("authUser");
			//vo.getNo();
			int no = ((UserVo)session.getAttribute("authUser")).getNo(); //위의 두줄과 동일
			//controller 먼저 만들고 --> Dao 만들기
			
			UserDao userDao = new UserDao();
			UserVo vo = userDao.getUser(no); //no정보를 주면 User 가져오기!
			System.out.println(vo.toString());
			
			request.setAttribute("userVo", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		}else if ( "modify".equals(action) ) {
			System.out.println("modify");
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			System.out.println(no);
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			
			UserVo vo = new UserVo(no, "", password, name, gender); //id 값은 안 엏으므로.. (setter로 값을 넣어주거나 클래스에서 생성자를 만들어 주거나의 방법 등이 있음)
			System.out.println(vo.toString());
			
			UserDao userDao = new UserDao();
			userDao.update(vo);
			
			//세션값 업데이트
			//필요없는 값도 같이 세션에 올라감
			//session.setAttribute(name, vo);
			
			//세션에 이름만 수정하기
			UserVo sVo = (UserVo)session.getAttribute("authUser");
			sVo.setName(name);
			
			WebUtil.redirect(request, response, "/mysite2/main");
			
			
		}
			 
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
