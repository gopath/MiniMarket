package ac.id.itb.d4.minimart.costumer.model;

public class Transaction {

	private String transactionId;
	private String employeeId;
	private String goodsName;
	private String goodsBarcode;
	private String goodsQty;
	private String status;	
	
	public String getTransactionId(){
		return transactionId;
	}
	
	public void setTransactionId(String id){
		this.transactionId = id;
	}
	
	public String getEmployeeId(){
		return employeeId;
	}
	
	public void setEmployeeId(String id){
		this.employeeId = id;
	}
	
	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String name){
		this.goodsName = name;
	}
	
	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String barcode){
		this.goodsBarcode = barcode;
	}
	
	public String getGoodsQty(){
		return goodsQty;
	}
	
	public void setGoodsQty(String qty){
		this.goodsQty = qty;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String sts){
		this.status = sts;
	}
}