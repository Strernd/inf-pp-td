package inf_pp.td.model;

import inf_pp.td.TimeSource;

/**
 * Every buff has to implement this interface
 */
public interface Buff extends java.io.Serializable{
	
	/**
	 * the type of the buff, ie. what attribute does a buff affect
	 */
	enum Type{
		MOVE_SPEED,DOT
	}
	
	/**
	 * Apply a buff to a value.
	 * Call {@link #canRemove} before calling apply. apply's behaviour is undefined if canRemove returns false.
	 * @param arg0 the buff's argument, usually the previous value of the attribute, but can be anything like a creep itself
	 * @param type the buff type
	 * @param time the current time
	 * @return the buffed value if the buff changes it or the unmodified argument if the buff does not treat a type of buff
	 */
	Object apply(Object arg0, Type type, TimeSource time);
	
	/**
	 * Checks if a buff is finished and can be removed.
	 * @param time the current time
	 * @return true if the buff has expired and can be removed, false otherwise
	 */
	boolean canRemove(TimeSource time);
}
