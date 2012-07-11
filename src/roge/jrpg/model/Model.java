package roge.jrpg.model;

import java.io.Serializable;

public abstract class Model implements Serializable{
	static final long serialVersionUID = 1L;
	
	
	/*Begin Constructors*/	
	public Model(){
	}
	/*End Constructors*/
	
	/*Begin Abstract Methods*/
	public abstract String getModelHeader();
	/*End Abstract Methods*/
}
