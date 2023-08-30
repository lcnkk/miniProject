package tommy.java.test;

public class Customer {
	private String name;
	private int age;
	private String phone;
	private String address;
	
	//생성자
	public Customer() {
		
	}

	public Customer(String name, int age, String phone, String address) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.address = address;
	}
	
	//메소드
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", phone=" + phone + ", address=" + address + "]";
	}
	
}
