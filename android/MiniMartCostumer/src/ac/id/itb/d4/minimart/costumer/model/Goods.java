package ac.id.itb.d4.minimart.costumer.model;

public class Goods {

	private int goodsNo;
	private String goodsBarcode;
	private String goodsName;
	private String goodsCategory;
	
	public int getGoodsNo(){
		return goodsNo;
	}
	
	public void setGoodsNo(int goodsNo){
		this.goodsNo = goodsNo;
	}
	
	public String getGoodsBarcode(){
		return goodsBarcode;
	}
	
	public void setGoodsBarcode(String goodsBarcode){
		this.goodsBarcode = goodsBarcode;
	}
	
	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	
	public String getGoodsCategory(){
		return goodsCategory;
	}
	
	public void setGoodsCategory(String goodsCategory){
		this.goodsCategory = goodsCategory;
	}
}
