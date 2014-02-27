package ac.id.itb.d4.minimart.inventory.model;

public class StockIn {
	
	private String productName;
	private String detailsDescription;
	private float defaultQty;
	
	public String getProductName(){
		return productName;
	}
	
	public void setProductName(String name){
		this.productName = name;
	}
	
	public String getDetailsDescription(){
		return detailsDescription;
	}
	
	public void setDetailsDescription(String desc){
		this.detailsDescription = desc;
	}
	
	public float getDefaultQty(){
		return defaultQty;
	}
	
	public void setDefaultQty(float qty){
		this.defaultQty = qty;
	}
	
}
