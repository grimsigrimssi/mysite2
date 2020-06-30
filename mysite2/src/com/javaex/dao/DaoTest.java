package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		UserDao userDao = new UserDao();  	//1
		
		UserVo vo = new UserVo("hi", "1234", "이정재", "male");			//3.  --> //4. UserVo에서 seg없는 생성자 만들기 --> //5.인스턴스 vo 의 변수 채우기
		userDao.insert(vo);  					                        //2. 	//6.
	}

}
