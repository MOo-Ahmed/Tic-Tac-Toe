public class TestClass {

	public static void main(String[] args) {
		TestClass t = new TestClass();
		t.start();
		
	}
	
	void start() {
		Game g = new Game('X');
		g.run();
	}
	
	

}
