import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Ball {
	public int x, y, width = 25, height = 25;
	
	public int motionX, motionY;
	
	public Random random;
	
	private Pong pong;
	
	public int amountOfHits;
	
	public Ball(Pong pong){
		this.pong = pong;
		
		this.random = new Random();
		
		spawn();
	}
	
	public void update(Paddle paddle1, Paddle paddle2){
		System.out.println(this.motionX + ", " + this.motionY);
		int speed = 5;
	
		this.x += motionX * speed;
		this.y += motionY * speed;
		
		if(this.y + height - motionY > pong.height || this.y < 0){
			if(this.motionY < 0){
				this.y = 0;
				this.motionY = random.nextInt(4);
				
				if(motionY == 0){
					motionY = 1;
				}
			}
			else{
				this.motionY = -random.nextInt(4);
				this.y = pong.height - height;
				if(motionY == 0){
					motionY = -1;
				}
			}
		}
	
		if(checkCollision(paddle1) == 1){
			this.motionX = 1 + (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);
			if(motionY ==  0){
				motionY = 1;
			}
			
			amountOfHits++;
		}
		else if(checkCollision(paddle2) == 1){
			this.motionX = -1 - (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			if(motionY == 0){
				motionY = 1;
			}
			amountOfHits++;
		}
		
		if(checkCollision(paddle1) == 2){
			spawn();
			paddle2.score++;
		}
		else if(checkCollision(paddle2) == 2){
			spawn();
			paddle1.score++;
		}
	}
	
	 public void spawn(){
		this.amountOfHits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height/ 2;
		this.motionY = -2 + random.nextInt(4);
		if(motionY == 0){
			motionY = 1;
		}
		
		if(random.nextBoolean()){
			motionX = 1;
		}
		else{
			motionX = -1;
		}
	 }
	
	public int checkCollision(Paddle paddle){
		if(this.x < paddle.x + paddle.width && this.x + width > paddle.x 
		&& this.y < paddle.y + paddle.height && this.y + height > paddle.y){
			return 1;//hit
		}
		else if((paddle.x > x && paddle.paddleNumber == 1) 
		|| (paddle.x < x - width && paddle.paddleNumber == 2)){
			return 2;//miss
		}
		
		return 0; //nothing
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}

}