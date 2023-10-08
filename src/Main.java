import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		JFrame map=new JFrame();
        Gameplay GamePlay = new Gameplay();
		map.setBounds(0, 0, 1200, 787);//kích cỡ
		map.setTitle("Tank 2D");	// tiêu đề
		map.getContentPane().setBackground(Color.gray);//nền
		map.setResizable(false);//thu phóng
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//nút tắt
		map.setVisible(true);//thu phóng
        map.setLocationRelativeTo(null);// frame luôn ở chính giữa
        map.add(GamePlay);
        ImageIcon image= new ImageIcon("logo.png", null);// creat an ImageIcon
        map.setIconImage(image.getImage());//set logo
		
	}

}
