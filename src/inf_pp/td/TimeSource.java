package inf_pp.td;

public class TimeSource implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705689030214128961L;
	private long startTime=0;
	private long lastTick=0;
	private long time=0;

	public TimeSource() {
		startTime=lastTick=time=System.currentTimeMillis();
	}
	
	public long getMillisSinceLastTick() {
		return time-lastTick;
	}
	
	public long getMillisSinceStart() {
		return time-startTime;
	}
	
	public void setStartNow() {
		startTime=lastTick=System.currentTimeMillis();
	}
	
	public void tick() {
		lastTick=time;
		time=System.currentTimeMillis();
	}

	public void skipTick() {
		time=System.currentTimeMillis();
		startTime+=time-lastTick;
		lastTick=time;
	}
	
}
