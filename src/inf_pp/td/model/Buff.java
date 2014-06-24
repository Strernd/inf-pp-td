package inf_pp.td.model;

public interface Buff {
	
	enum Type{
		MOVE_SPEED,DOT
	}
	
	Object apply (Object arg0, Type type, long time);
	
	boolean canRemove();
}
