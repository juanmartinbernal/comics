package com.comicsopentrends.model;

import java.util.List;

public class ResponseClans{
	private Paging paging;
	private List<ItemsItem> items;

	public void setPaging(Paging paging){
		this.paging = paging;
	}

	public Paging getPaging(){
		return paging;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"ResponseClans{" + 
			"paging = '" + paging + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}