package inf_pp.td;

/**
 * A class to hold the current ingame time.
 * It can be modified to support pausing and saving/loading the game without actually handle these cases in the Game class.
 * Might support time-lapse/slow-motion in the future.
 */
public class TimeSource implements java.io.Serializable {
	private static final long serialVersionUID = -3705689030214128961L;
	
	/**
	 * The time the game was started (in ms since Jan 1st 1970 UTC)
	 */
	private long startTime=0;
	/**
	 * The time we ticked last (in ms since Jan 1st 1970 UTC)
	 */
	private long lastTick=0;
	/**
	 * The current tick's time (in ms since Jan 1st 1970 UTC)
	 */
	private long time=0;

	/**
	 * Create a new TimeSource Object and initialize the start time to the current time
	 */
	public TimeSource() {
		startTime=lastTick=time=System.currentTimeMillis();
	}
	
	/**
	 * @return time in ms since the current and the previous call to {@link #tick}
	 */
	public long getMillisSinceLastTick() {
		return time-lastTick;
	}
	
	/**
	 * @return time in ms since the game was started
	 */
	public long getMillisSinceStart() {
		return time-startTime;
	}
	
	/**
	 * Sets the start time to the current time.
	 */
	public void setStartNow() {
		startTime=lastTick=System.currentTimeMillis();
	}
	
	/**
	 * Sets the time of current tick and last tick.
	 * Call this before each Game's tick.
	 */
	public void tick() {
		lastTick=time;
		time=System.currentTimeMillis();
	}

	/**
	 * This skips a tick and advances the start time.
	 * This can be used to resume the game after pause.
	 */
	public void skipTick() {
		time=System.currentTimeMillis();
		startTime+=time-lastTick;
		lastTick=time;
	}
	
}
