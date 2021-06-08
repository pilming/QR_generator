package kr.co.gda.qr;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class App extends JFrame {
	static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws WriterException, IOException{
        log.info("hello world!");
        
        //1.qr에 어떤 컨텐츠를 추가할 것인가? -> 다른 api를 통해서 획득
        //QRservice 객체 생성
        QRService qr = new QRService();
        String userName = qr.getUserName(); //QRservice클래스의 getUserName메소드로 유저이름 가져오기
        log.info(userName);
        
        StringBuffer contents = new StringBuffer(); //정보를 담을 스트링 버퍼
        contents.append(userName+"\n"); //스트링 버퍼에 정보 추가
        
        String systemInfo = qr.getSystemInfo();
        log.info(systemInfo);
        contents.append(systemInfo.replace(",", "\n")+"\n");
        
        String networkInfo = qr.getNetworkInfo();
        log.info(networkInfo);
        contents.append(networkInfo.replace(",", "\n")+"\n");
        
        //2.QR생성 zxing라이브러리의 QRCodeWriter객체는 QR 코드를 회색조 값의 BitMatrix 2D 배열로 렌더링해준다.
        QRCodeWriter qrWriter = new QRCodeWriter(); 
        
        //)QRCodeWriter의 encode메소드로 정보들을 BitMatrix 2D 배열로 렌더링 (정보, 포맷, 너비, 높이)
        BitMatrix matrix = qrWriter.encode(contents.toString(), BarcodeFormat.QR_CODE, 300, 300);
        
        //qr설정관련(색상)
        //MatrixToImageConfig config = new MatrixToImageConfig(0xFFFFFFFF, 0xFF000000); //배경이 검은색이니까 인식이 잘 안돼서 기본값으로 사용하기위함
        
        //MatrixToImageWriter이용하여 이미지 생성 -> 버퍼이미지에 저장
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
        
        //3.QR저장 
        String imageFileName = "myQr.png"; //이미지파일 이름
        ImageIO.write(qrImage, "png", new File(imageFileName)); //(버퍼안의이미지, 확장자, 파일생성) ImageIO.write로 파일 생성
        
        //4.qr출력 -> 웹이면 뷰로, pc앱이면 swing frame , 안드로이드면 activity
        App app = new App(); //피씨앱이므로 스윙사용 앱객체 생성
        app.setTitle("QR"); //객체에 타이틀 설정
        app.setLayout(new FlowLayout()); //한칸한칸 넣는 레이아웃
        
        ImageIcon icon = new ImageIcon(imageFileName); //이미지파일을 이미지아이콘 객체에 저장
        JLabel imageLabel = new JLabel(icon); //말그대로 라벨에 이미지를 넣음
        app.add(imageLabel); //앱에 라벨 추가
        
        app.setSize(350,350); // 앱사이즈 조절
        app.setVisible(true); // 실행하면 앱이 보이게끔
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x(닫기)를 누르면 앱이 실행되면서 생긴 스택을 닫아준다
    }
}
