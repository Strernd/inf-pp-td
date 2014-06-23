package inf_pp.td.model;

public interface Buff {
	
	enum Type{
		MOVE_SPEED
	}
	
	Object apply (Object arg0, Type type);
	
	boolean canRemove();
}
