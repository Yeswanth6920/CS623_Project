package CS623;

public class Product {
	private String prod;
	private String pname;
	private int price;
	private String stockProdId;
	private String dep;
	private int quantity;
	
	public Product(String prod, String pname, int price, String stockProdId, String dep, int quantity) {
		super();
		this.prod = prod;
		this.pname = pname;
		this.price = price;
		this.stockProdId = stockProdId;
		this.dep = dep;
		this.quantity = quantity;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStockProdId() {
		return stockProdId;
	}

	public void setStockProdId(String stockProdId) {
		this.stockProdId = stockProdId;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
