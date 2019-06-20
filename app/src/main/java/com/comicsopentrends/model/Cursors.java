package com.comicsopentrends.model;

public class Cursors{
	private String after;
	private String before;

	public void setAfter(String after){
		this.after = after;
	}

	public String getAfter(){
		return after;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	@Override
 	public String toString(){
		return 
			"Cursors{" + 
			"after = '" + after + '\'' + 
			"}";
		}
}
