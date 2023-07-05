import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

//C:\Users\Drew\Desktop
//Qwertyuiop[123$4$567]
public class Driver {
	public static void main(String args[]) throws IOException {
		Scanner sc = new Scanner(System.in);
		String path = null;
		File f = null;
		String docName;
		File doc;
		String key;
		while (true) {
			System.out.println();
			if(path == null) {
				System.out.println("no directory selected");
			}else {
				System.out.println(path);
			}
			System.out.println("enter a number");
			System.out.println("0 – Exit\r\n" + "1 – Select directory\r\n"
					+ "2 – List directory content (first level)\r\n" + "3 – List directory content (all levels)\r\n"
					+ "4 – Delete file\r\n" + "5 – Display file (hexadecimal view)\r\n"
					+ "6 – Encrypt file (XOR with password)\r\n" + "7 – Decrypt file (XOR with password)");

			int choice = sc.nextInt();

			switch (choice) {
			case 0:
				System.exit(0);
				break;
			case 1:
				System.out.println("input absolute path of directory");
				path = sc.next();
				f = new File(path);
				if (!f.exists()) {
					System.out.println("directory does not exist. please input a correct directory");
					path = null;
				} else if (!f.isDirectory()) {
					System.out.println("file is not a directory. please input a directory");
					path = null;
				}
				break;
			case 2:
				if (f.equals(null)) {
					System.out.println("no path has been select. please select a path");
				} else {
					System.out.println("showing level one contents of " + f.getName());
					showFilesOne(f);
				}
				break;
			case 3:
				if (f.equals(null)) {
					System.out.println("no path has been select. please select a path");
				} else {
					System.out.println("showing all contents of " + f.getName());
					showFilesAll(f, 0);
				}
				break;
			case 4:
				System.out.println("please input a filename within the selected directory");
				docName = sc.next();
				doc = new File(path + "\\" + docName);
				if (!doc.exists()) {
					System.out.println("document doesn't exists");
				} else {
					doc.delete();
				}

				break;
			case 5:
				System.out.println("please input a filename within the selected directory");
				docName = sc.next();
				doc = new File(path + "\\" + docName);
				if (!doc.exists()) {
					System.out.println("document doesn't exists");
				} else {
					FileInputStream iStream = new FileInputStream(doc);
					byte data[] = data = new byte[iStream.available()];
					iStream.read(data);
					
					int i = 0;
					for (byte b : data) {
						if(i%16 == 0) {
							System.out.println();
							System.out.print(String.format("%08X", i) + ": ");
						}
						System.out.print(String.format("%02X", b) + " ");
						i++;
					}
				}
				break;
			case 6:
				System.out.println("enter key for encryption");
				key = sc.next();
				System.out.println("select file for encryption");
				docName = sc.next();
				String edocPath = path + "\\" + docName;
				encryptDecrypt(edocPath, key);
				System.out.println("encryption done");
				break;
			case 7:
				System.out.println("enter key for decryption");
				key = sc.next();
				System.out.println("select file for decryption");
				docName = sc.next();
				String ddocPath = path + "\\" + docName;
				encryptDecrypt(ddocPath, key);
				System.out.println("decryption done");
				break;
			default:
				System.out.println("your choice was invalid please try again");
			}
		}
	}
	public static void encryptDecrypt(String streamPath, String key) throws IOException {
		FileInputStream iStream = new FileInputStream(streamPath);
		
		byte data[] = data = new byte[iStream.available()];
		iStream.read(data);
		byte[] byteKey = key.getBytes();
		
		int i = 0;
		int j = 0;
		for (byte b : data) {
            data[i] = (byte)(b ^ byteKey[j]);
            i++;
            j++;
            if(j > byteKey.length-1) {
            	j = 0;
            }
        }
		
		FileOutputStream oStream = new FileOutputStream(
	            streamPath);
		
		oStream.write(data);
		
		oStream.close();
		iStream.close();
	}
	public static void showFilesOne(File f) {
		File[] fileList = f.listFiles();
		for (File file : fileList) {
			System.out.println("\t" + file.getName());
		}
	}

	public static void showFilesAll(File f, int level) {
		File[] fileList = f.listFiles();
		for (File file : fileList) {
			for (int i = 0; i < level; i++) {
				System.out.print("\t");
			}
			System.out.println(file.getName());
			if (file.isDirectory()) {
				
				showFilesAll(file, level + 1);
			}
		}
	}
}
