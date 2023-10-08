import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


public class Player2Bullet {
	private double x, y;//Vị trí tọa độ của viên gạch

	//constructor gán vị trí tọa độ
	public Player2Bullet(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Hàm dùng để di chuyển viên đạn,đối số nhận vào là face chính là hướng của đạn
	//Sau này,sẽ truyền vào hàm này 1 trong 4 giá trị là right,left,up và down,từ đó di chuyển theo hướng tương đương
	public void move(String face)
	{
		if(face.equals("right"))
			x += 5;
		else if(face.equals("left"))
			x -= 5;
		else if(face.equals("up"))
			y -= 5;
		else
			y +=5;
	}
	
	//Vẽ viên đạn của người thứ 2,đạn của người thứ 2 có màu xanh
	public void draw(Graphics g)
	{
		g.setColor(Color.green);
		//Anh em tìm hiểu phương thức fillOval này nhé!!!
		g.fillOval((int) x, (int) y, 10, 10);
	}
	
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}

}
 