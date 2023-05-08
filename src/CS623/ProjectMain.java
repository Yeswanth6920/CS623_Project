package CS623;

public class ProjectMain {

	public static void main(String[] args) {
		Transaction transaction = new Transaction();
		
		Product product = new Product("p100", "cd", 5, "p100", "d2", 50);
		try {
			// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.
			// add
			transaction.addProduct(product);
			// update
			product = new Product("pp1", "tape", 5, "pp1", "d2", 50);
			transaction.addProduct(product);
			
			// The product p1 changes its name to pp1 in Product and Stock.

			// The product p1 is deleted from Product and Stock.
			transaction.deleteProduct("p1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
