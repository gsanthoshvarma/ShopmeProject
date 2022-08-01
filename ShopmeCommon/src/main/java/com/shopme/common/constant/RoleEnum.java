package com.shopme.common.constant;

public enum RoleEnum {
	 ADMIN("Admin","Manage everything"),
	 SALES_PERSON("Sales Person","Manage product price,customers,shopping,order sales report"),
	 EDITOR("Editor","Manage Categories,brands,products,articles and menu"),
	 SHIPPER("Shipper","View products,view order and update order status"),
	 ASSISTANT("Assistant","Manage question and reviews");
	
	private String name;
	private String desc;
	
	RoleEnum(String name,String desc) {
		this.name = name;
		this.desc = desc;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
