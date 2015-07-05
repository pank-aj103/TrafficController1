package traffic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import controller.ControllerImpl;
import signal.TrafficLight;


public class Track {
	public enum Direction{E,W,N,S};
	private Queue<String> cars=new LinkedList<>();
	private volatile boolean trackRunningStatus;
	private static final int TWO_SECONDS=2000;
	private static final int ONE_SECOND=1000;
	private Direction direction;


	private boolean firstCar;
	private TrafficLight light;
	
	public Track(Direction direction, TrafficLight light){
		this.direction=direction;
		this.light=light;
		light.associatedTrack(this);
	}
	
	public void initialize(){
		Timer timer= new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){public void run(){carArrived("C"+cars.size()+1);}}, 0 ,ONE_SECOND);
	}
	
	public void carArrived(String car){
		//System.out.println("Car Arrived:"+direction+", Running Status:"+trackRunningStatus);
		cars.add(car);
	}
	public void moveCars(){
		while(trackRunningStatus){
			if(cars.size()>0) cars.remove();
			else continue;
			try {
				if(firstCar){
					Thread.sleep(TWO_SECONDS);
					//System.out.println("moveCars:"+direction+", Running Status:"+trackRunningStatus+" Queue Size"+cars.size()+" Sleeping 2");
					firstCar=false;
				}else{
					Thread.sleep(ONE_SECOND);
					//System.out.println("moveCars:"+direction+", Running Status:"+trackRunningStatus+" Queue Size"+cars.size()+" Sleeping 1");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("moveCars:"+direction+", Running Status:"+trackRunningStatus+" Queue Size"+cars.size());
		}
		
	}
	
	public void startTraffic(){
		trackRunningStatus=true;
		//System.out.println("Starting:"+direction+", Running Status:"+trackRunningStatus);
		moveCars();
	}
	
	public void stopTraffic(){
		trackRunningStatus=false;
		firstCar=true;
		//System.out.println("Stopping:"+direction+", Running Status:"+trackRunningStatus);
	}
	
	public int numberOfCars(){
		return cars.size();
	}

	public Direction getDirection() {
		return direction;
	}
}
