 import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener 
{
	private brick br;
	
	private ImageIcon player1;	

	//Gán vị trí ban đầu của người chơi 1
	private int player1X = 50;
	private int player1Y = 700;	

	//4 biến dưới đây lưu hướng của người chơi 1,ban đầu,hình ảnh xe tăng là hướng lên,nên để biến up là true
	private boolean player1right = false;
	private boolean player1left = false;
	private boolean player1down = false;
	private boolean player1up = true;	

	//Điểm,máu (máu max = 5),trạng thái người 1 có đang bắn hay không,và hướng đạn bắn của người 1
	private int player1score = 0;
	private int player1lives = 5;
	private boolean player1Shoot = false;
	private String bulletShootDir1 = "";
	
	//Khai báo các biến trạng thái liên quan đến người 2
	// Tương tự như các biến trên của người 1
	private ImageIcon player2;	
	private int player2X = 900;
	private int player2Y = 700;	
	private boolean player2right = false;
	private boolean player2left = false;
	private boolean player2down = false;
	private boolean player2up = true;
	private int player2score = 0;
	private int player2lives = 5;
	private boolean player2Shoot = false;
	private String bulletShootDir2 = "";
	
	//Anh em nhớ giá trị delay này là 8,tí nữa sẽ có hàm để
	//refresh lại hình ảnh trên JFrame sau mỗi chu kỳ là 8ms
	//Anh em chỉnh 8 thành 800,8000 để thấy đạn bay bị chậm đi,người di chuyển chậm đi... nhé
	private Timer timer;
	private int delay=8;
	
	//Hai biến này tí nữa sẽ dùng để lắng nghe sự kiện khi thao tác với người 1 và người 2
	//Hai class Player1Listener và Player2Listener được viết ở dưới
	private Player1Listener player1Listener;
	private Player2Listener player2Listener;
	
	private Player1Bullet player1Bullet = null;
	private Player2Bullet player2Bullet = null;
	
	//Trạng thái chơi,tức là trạng thái chương trình,ban đầu khi bật lên nó luôn là true,nghĩa là game đang bật
	private boolean play = true;
	
	//Constructor,tí nữa add Gameplay vào JFrame,thì JFrame bật lên một phát sẽ thực hiện mấy cái sau luôn
	public Gameplay()
	{	
		//Nhét tường vào frame (đoạn này vẫn chưa hiện lên tường đâu,phải paint nó nữa)
		br = new brick();

		//Bắt đầu lắng nghe sự kiện
		player1Listener = new Player1Listener();
		player2Listener = new Player2Listener();

		setFocusable(true);//Phải có cái này thì JFrame đang hiện mới 
		//addKeyListener(this);
		addKeyListener(player1Listener);
		addKeyListener(player2Listener);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);//anh em hay nhớ this trỏ tới cái gọi tới nó,thì cái này nó cũng trỏ tới cái Frame mà dùng nó
		timer.start();//bắt đầu lặp refresh lại JFrame sau mỗi 8ms nhé
	}
	
	//Đoạn này vẽ tất cả mọi thứ hiện ra trước mắt người nhìn,anh em vào main chạy project để nó
	//hiện cái frame chính lên cho dễ hiểu nhá
	public void paint(Graphics g)
	{    		
		//Vẽ phần chơi game
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
		
		//Vẽ phần bảng điểm bên tay phải
		//g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 600);
		
		//Vẽ gạch cứng
		br.drawSolids(this, g);
		br.drawTree(this, g);
		
		//Vẽ gạch mềm
		br.draw(this, g);
		
		
		if(play)
		{
			//Vẽ người chơi thứ nhất,nếu hướng nào đang được set là true thì hiện ảnh hưởng đó =))
			if(player1up)
				player1=new ImageIcon("player1_tank_up.png");	
			else if(player1down)
				player1=new ImageIcon("player1_tank_down.png");
			else if(player1right)
				player1=new ImageIcon("player1_tank_right.png");
			else if(player1left)
				player1=new ImageIcon("player1_tank_left.png");
				
			player1.paintIcon(this, g, player1X, player1Y);
			
			// Vẽ người chơi thứ 2
			if(player2up)
				player2=new ImageIcon("player2_tank_up.png");	
			else if(player2down)
				player2=new ImageIcon("player2_tank_down.png");
			else if(player2right)
				player2=new ImageIcon("player2_tank_right.png");
			else if(player2left)
				player2=new ImageIcon("player2_tank_left.png");
						
			player2.paintIcon(this, g, player2X, player2Y);
			
			//Xử lý nếu trạng thái người 1 bắn là true
			if(player1Bullet != null && player1Shoot)
			{
				//Nếu hướng của đạn đang là rỗng,thì set cho hướng của đạn chính
				//bằng hướng của xe tăng ngay lúc đó
				if(bulletShootDir1.equals(""))
				{
					if(player1up)
					{					
						bulletShootDir1 = "up";
					}
					else if(player1down)
					{					
						bulletShootDir1 = "down";
					}
					else if(player1right)
					{				
						bulletShootDir1 = "right";
					}
					else if(player1left)
					{			
						bulletShootDir1 = "left";
					}
				}
				//Nếu hướng của đạn đã được xác định,dùng hàm move (được xây dựng trong lớp Player1Bullet) để tịnh tiến 
				//đường đạn và draw để vẽ lại đường đạn sau khi tịnh tiến
				else
				{
					player1Bullet.move(bulletShootDir1);
					player1Bullet.draw(g);
				}
				
				//Cái này để xử lý khi phát hiện người chơi 2 có trúng đạn của người chơi 1 không
				//nhìn rối vậy thôi những rất dễ hiểu,chỉ là tạo ra hai hình chữ nhật tương đương
				//vị trí của đạn và người chơi 2 rồi xem chúng là đang chạm vào nhau không
				if(new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10)
				.intersects(new Rectangle(player2X, player2Y, 50, 50)))
				{
					//Nếu chúng đạn thì tăng điểm người 1,trừ mạng người 2,hủy luôn viên đạn người 1...
					player1score += 10;
					player2lives -= 1;
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}
				
				//Đã đến lúc dùng hàm checkCollision để kiểm tra đạn dính tường hay không
				if(br.checkCollision(player1Bullet.getX(), player1Bullet.getY())
						|| br.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY()))
				{
					//nếu đạn dính tường,hủy bỏ viên đạn
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";				
				}
	
				//Cái này để xem nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
				if(player1Bullet.getY() < 1 
						|| player1Bullet.getY() > 800
						|| player1Bullet.getX() < 1
						|| player1Bullet.getX() > 1000)
				{
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}
			}
			
			//Từ đây xuống dòng 251 là xử lý các trường hợp với đường đạn của người 2,tương đương xử lý cới đường đạn người 1 ở trên
			if(player2Bullet != null && player2Shoot)
			{
				if(bulletShootDir2.equals(""))
				{
					if(player2up)
					{					
						bulletShootDir2 = "up";
					}
					else if(player2down)
					{					
						bulletShootDir2 = "down";
					}
					else if(player2right)
					{				
						bulletShootDir2 = "right";
					}
					else if(player2left)
					{			
						bulletShootDir2 = "left";
					}
				}
				else
				{
					player2Bullet.move(bulletShootDir2);
					player2Bullet.draw(g);
				}
				
				
				if(new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
				.intersects(new Rectangle(player1X, player1Y, 50, 50)))
				{
					player2score += 10;
					player1lives -= 1;
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}
				
				if(br.checkCollision(player2Bullet.getX(), player2Bullet.getY())
						|| br.checkSolidCollision(player2Bullet.getX(), player2Bullet.getY()))
				{
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";				
				}
				
				if(player2Bullet.getY() < 1 
						|| player2Bullet.getY() > 800
						|| player2Bullet.getX() < 1
						|| player2Bullet.getX() > 1000)
				{
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}
			}
		}
	
		
		//Tạo phần thông số ở bên tay phải		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 15));
		g.drawString("Scores", 1050,30);
		g.drawString("Player 1:  "+player1score, 1050,60);
		g.drawString("Player 2:  "+player2score, 1050,90);
		
		g.drawString("Lives", 1050,150);
		g.drawString("Player 1:  "+player1lives, 1050,180);
		g.drawString("Player 2:  "+player2lives, 1050,210);
		
		//Xử lý nếu mạng của người 1 hoặc người 2 chỉ còn 0 (chết)
		if(player1lives == 0)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 60));
			g.drawString("Game Over", 200,300);
			g.drawString("Player 2 Won", 180,380);
			play = false;
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("(Space to Restart)", 230,430);
		}
		else if(player2lives == 0)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 60));
			g.drawString("Game Over", 200,300);
			g.drawString("Player 1 Won", 180,380);
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("(Space to Restart)", 230,430);
			play = false;
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
	
		repaint();
	}
	//Đoạn này bắt đầu là class dùng để khai thác sự kiện trên bàn phím,toàn dùng if else,khá dễ hiểu nhá
	private class Player1Listener implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e) {	
			if(e.getKeyCode()== KeyEvent.VK_SPACE && (player1lives == 0 || player2lives == 0))
			{
				br = new brick();
				player1X = 50;
				player1Y = 700;	
				player1right = false;
				player1left = false;
				player1down = false;
				player1up = true;	
				
				player2X = 900;
				player2Y = 700;	
				player2right = false;
				player2left = false;
				player2down = false;
				player2up = true;	
				
				player1score = 0;
				player1lives = 5;
				player2score = 0;
				player2lives = 5;
				play = true;
				repaint();
			}
			if(e.getKeyCode()== KeyEvent.VK_U)
			{
				if(!player1Shoot)
				{
					if(player1up)
					{					
						player1Bullet = new Player1Bullet(player1X + 20, player1Y);
					}
					else if(player1down)
					{					
						player1Bullet = new Player1Bullet(player1X + 20, player1Y + 40);
					}
					else if(player1right)
					{				
						player1Bullet = new Player1Bullet(player1X + 40, player1Y + 20);
					}
					else if(player1left)
					{			
						player1Bullet = new Player1Bullet(player1X, player1Y + 20);
					}
					
					player1Shoot = true;
				}
			}
			if(e.getKeyCode()== KeyEvent.VK_W)
			{
				player1right = false;
				player1left = false;
				player1down = false; 
				player1up = true;		
				
				if(!(player1Y < 10)){
					player1Y-=10;}
					else player1Y-=0;

			}
			if(e.getKeyCode()== KeyEvent.VK_A)
			{
				player1right = false;
				player1left = true;
				player1down = false;
				player1up = false;
				
				if(!(player1X < 10))
					player1X-=10;
			}
			if(e.getKeyCode()== KeyEvent.VK_S)
			{
				player1right = false;
				player1left = false;
				player1down = true;
				player1up = false;
				
				if(!(player1Y > 700)){
					player1Y+=10;}
					else player1Y+=0;
			}
			if(e.getKeyCode()== KeyEvent.VK_D)
			{
				player1right = true;
				player1left = false;
				player1down = false;
				player1up = false;
				
				if(!(player1X >700))
					player1X+=10;
			}
		}
	}
	
	private class Player2Listener implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e) {	
			if(e.getKeyCode()== KeyEvent.VK_M)
			{
				if(!player2Shoot)
				{
					if(player2up)
					{					
						player2Bullet = new Player2Bullet(player2X + 20, player2Y);
					}
					else if(player2down)
					{					
						player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
					}
					else if(player2right)
					{				
						player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
					}
					else if(player2left)
					{			
						player2Bullet = new Player2Bullet(player2X, player2Y + 20);
					}
					
					player2Shoot = true;
				}
			}
			if(e.getKeyCode()== KeyEvent.VK_UP)
			{
				player2right = false;
				player2left = false;
				player2down = false;
				player2up = true;		
				
				if(!(player2Y < 10)){
					player2Y-=10;}
					else player2Y-=0;

			}
			if(e.getKeyCode()== KeyEvent.VK_LEFT)
			{
				player2right = false;
				player2left = true;
				player2down = false;
				player2up = false;
				
				if(!(player2X < 10)){
					player2X-=10;}
					else player2X-=0;
			}
			if(e.getKeyCode()== KeyEvent.VK_DOWN)
			{
				player2right = false;
				player2left = false;
				player2down = true;
				player2up = false;
				
				if(!(player2Y > 700)){
					player2Y+=10;}
					else player2Y+=0;
			}
			if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			{
				player2right = true;
				player2left = false;
				player2down = false;
				player2up = false;
				
				if(!(player2X > 948)){
					player2X+=10;}
					else player2X+=0;
					
			}
			
		}
	}
}
