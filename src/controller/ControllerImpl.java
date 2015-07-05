package controller;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import signal.TrafficLight;
import signal.TrafficLight.Color;
import traffic.Track;
import traffic.Track.Direction;

public class ControllerImpl implements TrafficController {

	private Track[] tracks=new Track[4];
	private TrafficLight[] lights=new TrafficLight[4];
	private static final int MILLIS=1000;
	public static Date now=new Date(); 
	public ControllerImpl() {
		
		lights[0]=new TrafficLight();
		lights[1]=new TrafficLight();
		lights[2]=new TrafficLight();
		lights[3]=new TrafficLight();
		
		tracks[0]=new Track(Direction.E, lights[0]);
		tracks[1]=new Track(Direction.W, lights[1]);
		tracks[2]=new Track(Direction.N, lights[2]);
		tracks[3]=new Track(Direction.S, lights[3]);
		
		initialize();
		System.out.println("N:"+tracks[2].numberOfCars()+", S:"+tracks[3].numberOfCars()+", E:"+tracks[0].numberOfCars()+", W:"+tracks[1].numberOfCars());
	}
	
	public void initialize(){
		for(int i=0;i<4;i++){
			tracks[i].initialize();
			if (i>=2) lights[i].initialize(true); 
			else lights[i].initialize(false);
			lights[i].registerContoller(this);
		}
	}
	
	@Override
	public void control(TrafficLight l) {
		//System.out.print(l.getTrack().getDirection());
		if(l.getCurrentColor().equals(Color.GREEN)){
			//System.out.println(" Starting.."+System.currentTimeMillis());
			l.getTrack().startTraffic();
		}else{
			l.getTrack().stopTraffic();
			//System.out.println(" Stopping.."+System.currentTimeMillis());			
		}
	}
	
	private void print(){
		System.out.println("N:"+tracks[2].numberOfCars()+", S:"+tracks[3].numberOfCars()+", E:"+tracks[0].numberOfCars()+", W:"+tracks[1].numberOfCars());
	}
	
	public static void main(String[] s){
		ControllerImpl c=new ControllerImpl();
		(new Timer()).scheduleAtFixedRate(new TimerTask(){public void run(){c.print();}}, 0, MILLIS);
	}
}
