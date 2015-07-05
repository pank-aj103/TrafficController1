package signal;

import java.util.Timer;
import java.util.TimerTask;

import traffic.Track;
import controller.TrafficController;


public class TrafficLight {
	public enum Color{RED, GREEN};
	private Color currentColor;
	private TrafficController controller;
	private Timer startTimer= new Timer();
	private Timer stopTimer= new Timer();
	private static final int ONE_SECOND=1000;
	private Track associatedTrack;
	
	public TrafficLight(){
		
	}
	
	public void initialize(boolean isStart){
		if(isStart){
			startTimer.scheduleAtFixedRate(new TimerTask(){public void run(){chnageSignal(Color.GREEN);}}, 0, 8*ONE_SECOND);
			stopTimer.scheduleAtFixedRate(new TimerTask(){public void run(){chnageSignal(Color.RED);}}, 3*ONE_SECOND, 8*ONE_SECOND);
		}else{
			startTimer.scheduleAtFixedRate(new TimerTask(){public void run(){chnageSignal(Color.GREEN);}}, 4*ONE_SECOND, 8*ONE_SECOND);
			stopTimer.scheduleAtFixedRate(new TimerTask(){public void run(){chnageSignal(Color.RED);}}, 7*ONE_SECOND, 8*ONE_SECOND);			
		}
	}
	
	void chnageSignal(Color color){
		this.currentColor=color;
		notifyContoller();
	}
	
	public void registerContoller(TrafficController controller){
		this.controller=controller;
	}
	
	public void notifyContoller(){
		controller.control(this);
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	public void associatedTrack(Track t){
		this.associatedTrack=t;
	}
	
	public Track getTrack(){
		return associatedTrack;
	}
}
