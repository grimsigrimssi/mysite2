package com.javaex.vo;

public class BoardVo {
	
	private int no;
	private String name;
	private String password;
	private String title;
	private String content;
	private String date;
	private int count;
	private String status;
	
	public BoardVo() {
		
	}
	
	public BoardVo(int no, String password, String title, String content) {
		this.no = no;
		this.password = password;
		this.title = title;
		this.content = content;
	}
	
	public BoardVo(int no, String title, String name, int count, String date, String status) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.count = count;
		this.date = date;
		this.status = status;
	}
		
	public BoardVo(int no, String name, String password, String title, String content, String date, int count, String Status) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.title = title;
		this.content = content;
		this.date = date;
		this.count = count;
		this.status = status;
	}


	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", name=" + name + ", password=" + password + ", title=" + title + ", content="
				+ content + ", date=" + date + ", count=" + count + ", status=" + status + "]";
	}
	
	
	

}
