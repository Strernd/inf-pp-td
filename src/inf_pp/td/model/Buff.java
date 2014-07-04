package inf_pp.td.model;

import inf_pp.td.TimeSource;

public interface Buff extends java.io.Serializable{
	
	enum Type{
		MOVE_SPEED,DOT
	}
	
	Object apply (Object arg0, Type type, TimeSource time);
	
	boolean canRemove(TimeSource time);
}
