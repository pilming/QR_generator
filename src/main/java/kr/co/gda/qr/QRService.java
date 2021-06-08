package kr.co.gda.qr;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class QRService {
	public String getUserName() {
		return "user name : choi jae hyeon";
	}
	
	public String getSystemInfo() {
		StringBuffer sf = new StringBuffer();
		String s = System.getProperty("user.name"); //컴퓨터 사용자이름
		sf.append("computer.name : "+s);
		s =System.getProperty("os.name"); //운영체제이름
		sf.append(",os.name : "+s);
		s = System.getProperty("os.version"); //운영체제 버전
		sf.append(",os.version : "+s);
		s = System.getProperty("java.version"); //자바 버전
		sf.append(",java.version : "+s);
		return sf.toString();
	}
	
	public String  getNetworkInfo() throws UnknownHostException {
		StringBuffer sf = new StringBuffer();
		InetAddress address = InetAddress.getLocalHost(); //로컬호스트 주소
		String s = address.getHostName(); //로컬호스트 이름
		sf.append("host name : "+s);
		s = address.getHostAddress();
		sf.append(",host address : "+s);
		return sf.toString();
	}
}
