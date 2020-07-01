package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;


@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/guestbook");
		
		String action = request.getParameter("action");
		
		if("addList".equals(action)) {
			System.out.println("addList");
			
			GuestBookDao guestBookDao = new GuestBookDao();
			List<GuestVo> gList = guestBookDao.getGuestList();
			
			//데이터 리퀘스트에 추가
			request.setAttribute("guestList", gList);//인스턴스 gList의 이름을 guestList로 하라는 의미 

			System.out.println(gList.toString());
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp"); //webUtil이 Static 클래스여서  WebUtil을 대문자로 인스턴스 생성 없이 바로시작
		
	
		}else if ("add".equals(action)) {
			System.out.println("게스트 저장");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			
			GuestVo vo = new GuestVo(name, password, content);
			
			GuestBookDao dao = new GuestBookDao();
			dao.guestInsert(vo);
			
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
			
		}else if ("deleteForm".equals(action)) {
			System.out.println("게스트 삭제폼");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
					
		}else if ("delete".equals(action)) {
			System.out.println("게스트 삭제");
			
			int no = Integer.parseInt(request.getParameter("password"));
			
			GuestBookDao dao = new GuestBookDao();
			dao.guestDelete(no);
						
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=add");
			
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
