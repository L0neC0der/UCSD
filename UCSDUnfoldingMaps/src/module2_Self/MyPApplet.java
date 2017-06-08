package module2_Self;

import processing.core.*;

public class MyPApplet extends PApplet {
	
	private PImage backgroundImg;
	
	public void setup()
	{size(800,800);
	backgroundImg = loadImage("palmTrees.jpg");
	}
	public void draw()
	{ backgroundImg.resize(0,height);
		image(backgroundImg,0,0);
		int[] cr=colour(hour());
		
		fill(cr[0],cr[1],0);
		ellipse(width/4,height/5,width/8,height/8);
	}
	public int[] colour(int hr){
	int[] arr= new int[2];
	if(hr>18 || hr<6)
	{arr[0]=0;
	arr[1]=0;
	}
	else{int b=hr-6;
	     int c=18-hr;
		int a=(b<=c?b:c);
		float ratio=a/6;
		arr[0]=255;
		arr[1]=(int)(ratio*255);
		
	}
	//System.out.println(hr);
	
	return arr;
	
	
	}
	

}