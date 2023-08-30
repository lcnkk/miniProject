package tommy.java.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CustomerManager {
	// 멤버필드
	private boolean isStop;
	private BufferedReader br;
	private ArrayList<Customer> data;
	private int position;
	private File file;

	// 생성자
	public CustomerManager() {
		isStop = false;
		br = new BufferedReader(new InputStreamReader(System.in));
		data = new ArrayList<Customer>();
		position = -1;
		file = null;
	}

	// 메서드
	public void start() throws IOException {
		int menu = -1;

		while (!isStop) {
			System.out.println("1. 고객정보등록");
			System.out.println("2. 고객정보검색");
			System.out.println("3. 고객정보수정");
			System.out.println("4. 고객정보삭제");
			System.out.println("5. 전체목록보기");
			System.out.println("6. 새파일");
			System.out.println("7. 불러오기");
			System.out.println("8. 저장하기");
			System.out.println("9. 새 이름으로 저장하기");
			System.out.println("0. 프로그램종료");
			System.out.print("메뉴선택 : ");

			try {
				menu = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				menu = -1;
			}

			switch (menu) {
			case 1:
				addCustomer();
				break;
			case 2:
				searchCustomer();
				break;
			case 3:
				updateCustomer();
				break;
			case 4:
				deleteCustomer();
				break;
			case 5:
				display();
				break;
			case 6:
				newFile();
				break;
			case 7:
				openFile();
				break;
			case 8:
				saveFile();
				break;
			case 9:
				saveAsFile();
				break;
			case 0:
				stop();
				break;
			default:
				System.out.println("\n메뉴입력오류 : 확인하시고 다시 입력해주세요.");
				break;
			}
			System.out.println();
		} // end while
	}

	public void saveAsFile() throws IOException {
		System.out.println();
		System.out.print("저장할 파일명 = ");
		String fileName = br.readLine();
		if (fileName == "" || fileName.length() == 0) {
			System.err.println("파일명은 반드시 입력해야 합니다.");
			return;
		}
		file = new File(fileName);
//	      saveData();
		saveObject();
	}

	public void saveFile() throws IOException {
		System.out.println();
		if (file == null) {
			System.out.print("저장할 파일명 = ");
			String fileName = br.readLine();
			if (fileName.length() == 0) {
				System.err.println("파일명은 반드시 입력해야 합니다.");
				file = new File(fileName);
				return;
			}
		}

		// saveData();
		saveObject();
	}

	private void saveObject() throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			for (int i = 0; i < data.size(); i++) {
				Customer myCustomer = data.get(i);
				oos.writeObject(myCustomer);
			}
			System.out.println("성공적으로 파일에 저장하였습니다.");
		} catch (FileNotFoundException fnfe) {
			System.err.println("경로입력이 잘못되었습니다.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException ioe) {
			}
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
			}
		}
	}

	public void saveData() {

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;

		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw, true);
			for (int i = 0; i < data.size(); i++) {
				Customer myCustomer = data.get(i);

				StringBuffer sb = new StringBuffer();

				sb.append(myCustomer.getName());
				sb.append("/");
				sb.append(myCustomer.getAge());
				sb.append("/");
				sb.append(myCustomer.getPhone());
				sb.append("/");
				sb.append(myCustomer.getAddress());

				pw.println(sb.toString());
			} // end for

			System.out.println("성공적으로 파일에 저장하였습니다.");
		} catch (FileNotFoundException fnfe) {
			System.out.println("경로입력이 잘못되었습니다.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();

			try {
				if (bw != null)
					bw.close();
			} catch (IOException ioe) {
			}

			try {
				if (fw != null)
					fw.close();
			} catch (IOException ioe) {
			}
		} // end try
	}// end saveData()

	// 파일 열기
	public void openFile() throws IOException {
		System.out.println();
		System.out.print("파일 이름을 엽니다:");
		String fileName = br.readLine();
		if (fileName.length() == 0) {
			System.err.println("파일 이름을 입력해주세요!!");
			return;
		}

		file = new File(fileName);

		// 3) 파일 불러오기
		// openData();
		loadObject();
	}

	// 4) 파일 불러오기 메소드()
	private void loadObject() {

		data.clear();
		position = -1;

		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (true) {

				try {
					Customer myCustomer = (Customer) ois.readObject();
					data.add(myCustomer);

					// 5) exception 추가
				} catch (EOFException eofe) {
					System.out.println("Loading file end");
					return;
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Not Found myCustomer");
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error : directory or filename");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException ioe) {
			}
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
			}
		}
	}

	// 새 파일_파일 초기화
	public void newFile() {
		System.out.println();

		data.clear();
		position = -1;
		file = null;
		System.out.println("Update new File");
	}

	// 데이터 열기
	public void openData() {
		data.clear();
		position = -1;

		FileReader fr = null;
		BufferedReader brr = null;

		try {
			fr = new FileReader(file);
			brr = new BufferedReader(fr);

			String mydata = "";

			while ((mydata = brr.readLine()) != null) {

				String[] imsi = mydata.split("/");

				Customer myCustomer = new Customer(imsi[0], Integer.parseInt(imsi[1]), imsi[2], imsi[3]);

				data.add(myCustomer);
			}

			System.out.println("성공적으로 파일을 로딩했습니다.");
		} catch (FileNotFoundException fnfe) {
			System.err.println("에러:디렉토리 또는 파일이름");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (IOException ioe) {
			}
		}
	}

	public void addCustomer() throws IOException {
		System.out.println();

		System.out.print("고객이름 = ");
		String name = br.readLine();
		System.out.print("나이 = ");
		int age = Integer.parseInt(br.readLine());
		System.out.print("전화번호 = ");
		String phone = br.readLine();
		System.out.print("주소 = ");
		String address = br.readLine();

		Customer myCustomer = new Customer(name, age, phone, address);
		data.add(myCustomer);
		System.out.println("성공적으로 고객정보를 저장하였습니다.");
	}

	private void searchCustomer() throws IOException {
		System.out.println();
		position = -1;
		System.out.print("찾으시는 고객 이름 = ");
		String name = br.readLine();
		for (int i = 0; i < data.size(); i++) {
			Customer myCustomer = data.get(i);
			if (myCustomer.getName().equals(name)) {
				System.out.println(name + "님의 고객정보 검색 성공");
				position = i;
			}
		}
		if (position < 0) {
			System.out.println(name + "님의 저의 고객이 아닙니다. 고객정보를 등록하세요.");
		}
	}

	public void updateCustomer() throws IOException {
		System.out.println();
		if (position < 0) {
			System.out.println("고객정보검색을 먼저 수행하셔야 합니다.");
			return;
		}
		Customer myCustomer = data.get(position);

		boolean isLoop = true;
		int menu = -1;
		while (isLoop) {
			System.out.println(myCustomer.getName() + "님의 고객정보수정");
			System.out.println("1. 전화번호수정");
			System.out.println("2. 주소수정");
			System.out.println("0. 수정취소");
			System.out.print("메뉴선택 : ");

			try {
				menu = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				menu = -1;
			}

			System.out.println();
			switch (menu) {
			case 1:
				System.out.print("수정할 전화번호 = ");
				String imsiPhone = br.readLine();
				myCustomer.setPhone(imsiPhone);
				System.out.println("성공적으로 전화번호를 수정하였습니다.");
				isLoop = false;
				break;
			case 2:
				System.out.print("수정할 주소 = ");
				String imsiAddresss = br.readLine();
				myCustomer.setAddress(imsiAddresss);
				System.out.println("성공적으로 주소를 수정하였습니다.");
				isLoop = false;
				break;
			case 0:
				System.out.print(myCustomer.getName() + "님의 정보수정을 취소하시겠습니까? (y/n) : ");
				String result = br.readLine();
				if (result.equals("Y") || result.equals("y")) {
					System.out.println(myCustomer.getName() + "님의 고객정보수정을 취소합니다.");
					isLoop = false;
				}
				break;
			default:
				System.out.println("\n메뉴입력오류 : 확인하시고 다시 입력해주세요.");
				break;
			}
			System.out.println();
		} // end while
	}

	public void deleteCustomer() throws IOException {
		System.out.println();
		if (position < 0) {
			System.out.println("고객정보검색을 먼저 수행하셔야 합니다.");
			return;
		}

		Customer myCustomer = data.get(position);
		System.out.print(myCustomer.getName() + "님의 정보를 정말로 삭제하시겠습니까? (y/n) : ");
		String result = br.readLine();
		if (result.equals("Y") || result.equals("y")) {
			System.out.println(myCustomer.getName() + "님의 고객정보 삭제 성공.");
			data.remove(position);
			position = -1;
		} else {
			System.out.println(myCustomer.getName() + "님의 고객정보 삭제 취소.");
		}
	}

	public void display() {
		System.out.println();

		for (int i = 0; i < data.size(); i++) {
			Customer myCustomer = data.get(i);
			System.out.println(myCustomer.toString());
		}
	}

	public void stop() throws IOException {
		System.out.println();
		System.out.print("프로그램을 종료하시겠습니까? (y/n)");

		String result = br.readLine();

		// 종료
		if (result.equals("Y") || result.equals("y")) {
			System.out.println("안녕");

			for (int i = 0; i < 5; i++) {
				try {
					// 0.5초 멈춤
					Thread.sleep(500);
					System.out.print(".");
				} catch (InterruptedException e) {
				}
			}
			System.out.println();

			isStop = true;
		}
	}

	// main
	public static void main(String[] args) {

		CustomerManager manager = new CustomerManager();

		try {
			manager.start();
		} catch (IOException e) {
		} // end try
	}// end main
}
