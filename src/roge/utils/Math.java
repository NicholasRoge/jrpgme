package roge.utils;

import java.util.ArrayList;
import java.util.List;

public class Math{
	public static class Expression{
		public static class InvalidExpressionException extends RuntimeException{
			/*Begin Constructors*/
			/**
			 * Constructs the exception.
			 */
			public InvalidExpressionException(){
				super();
			}
			
			/**
			 * Constructs the exception with the specified message.
			 * 
			 * @param message Exception 
			 */
			public InvalidExpressionException(String message){
				super(message);
			}
			/*End Constructors*/
		}
		
		public static enum Operation{
			/*Begin Enumerations*/
			ADDITION("+",0),
			SUBTRACTION("-",0),
			MULTIPLICATION("*",1),
			DIVISION("/",1),
			POWER("^",2);
			/*End Enumerations*/
			
			private static Operation[] __operation_orders;
			
			private int    __operation_order;
			private String __operation_string;
			
			
			/*Begin Constructors*/
			private Operation(String operation_string,int operation_order){
				this.__operation_string=operation_string;
				this.__operation_order=operation_order;
			}
			/*End Constructors*/
			
			/*Begin Other Essential Methods*/
			public String toString(){
				return this.__operation_string;
			}
			
			public static Operation fromString(String string){			
				for(Operation operation:Operation.values()){
					if(operation.toString().equals(string)){
						return operation;
					}
				}
				
				return null;
			}
			
			public static Operation[] getOperationOrder(){
				List<Operation> operations=null;
				
				
				//Make this a singleton method.
				if(Operation.__operation_orders==null){
					operations=new ArrayList<Operation>();
					for(Operation operation:Operation.values()){
						if(operations.size()==0){
							operations.add(operation);
						}else{
							for(int index=0;index<operations.size();index++){
								if(operation.__operation_order>=operations.get(index).__operation_order){
									operations.add(index,operation);
									
									break;
								}
							}
						}
					}
					
					Operation.__operation_orders=operations.toArray(new Operation[operations.size()]);
				}
				
				
				return Operation.__operation_orders;
			}
			/*End Other Essential Methods*/
		}
		
		public static enum Constants{			
			/*Begin Enumerations*/
			PI(java.lang.Math.PI,"pi");
			/*End Enumerations*/
						
			private String __string_representation;
			private double __value;
			
			
			/*Begin Constructors*/
			private Constants(double value,String string_representation){
				this.__value=value;
				this.__string_representation=string_representation;
			}
			/*End Constructors*/
			
			/*Begin Getter/Setter Methods*/
			public double getValue(){
				return this.__value;
			}
			/*End Getter/Setter Methods*/
			
			/*Begin Other Essential Methods*/			
			public static Constants fromString(String string){
				for(Constants constant:Constants.values()){
					if(constant.toString().equals(string)){
						return constant;
					}
				}
				
				return null;
			}
			
			public String toString(){
				return this.__string_representation;
			}
			/*End Other Essential Methods*/
		}
			
		public Expression left_expression;
		public double       left_param;
		public Expression right_expression;
		public double       right_param;
		public Operation    operation;
		
		
		/*Begin Constructors*/
		public Expression(){
			this.left_expression=null;
			this.right_expression=null;
			this.left_param=0;
			this.right_param=0;
			this.operation=null;
		}
		/*End Constructors*/
		
		/*Begin Other Essential Methods*/
		public double evaluate(){
			if(this.left_expression!=null){
				this.left_param=this.left_expression.evaluate();
			}
			
			if(this.right_expression!=null){
				this.right_param=this.right_expression.evaluate();
			}
			
			
			return this._evaluate();
		}
		
		protected double _evaluate(){;
			switch(this.operation){
				case ADDITION:
					return this.left_param+this.right_param;
				
				case SUBTRACTION:
					return this.left_param-this.right_param;
					
				case MULTIPLICATION:
					return this.left_param*this.right_param;
					
				case DIVISION:
					return this.left_param/this.right_param;
					
				case POWER:
					return java.lang.Math.pow(this.left_param,this.right_param);
					
				default:
					return 0;
			}
		}
		
		public static Expression fromString(String expression){
			final Operation[] operation_order = Operation.getOperationOrder();
			
			Expression e = null;
			List<Expression> expressions = null;
			int[]      expression_indices = null;
			String     expression_string = null;
			int        sub_start = -1;
			int        sub_end = -1;
			int        parenthesis_open = 0;
			
			
			expression=Expression._validateStringExpression(expression);
			
			expressions = new ArrayList<Expression>();
			while((sub_start=expression.indexOf('('))!=-1){
				for(sub_end=sub_start+1,parenthesis_open=0;sub_end<expression.length();sub_end++){
					if(expression.charAt(sub_end)=='('){
						parenthesis_open++;
					}else if(expression.charAt(sub_end)==')'){
						if(parenthesis_open==0){
							break;
						}else{
							parenthesis_open--;
						}
					}
				}
				
				if(sub_start-sub_end==0){
					throw new InvalidExpressionException("Empty parenthesis set found while parsing expression.");
				}

				expressions.add(Expression.fromString(expression.substring(sub_start+1,sub_end)));
				expression=expression.substring(0,sub_start)+"{"+(expressions.size()-1)+"}"+expression.substring(sub_end+1);
			}
			
			
			for(Operation operation:operation_order){
				while((sub_start=expression.indexOf(operation.toString()))!=-1){ 
					expression_indices = Expression.getExpressionIndices(expression, sub_start,operation.toString());
					
					e = new Expression();
					expression_string = expression.substring(expression_indices[0],expression_indices[1]+1);
					if(expression_string.charAt(0)=='{'){
						e.left_expression = expressions.get(Integer.parseInt(expression_string.substring(1,expression_string.length()-1)));
					}else{
						e.left_param = Double.parseDouble(expression_string);
					}
					
					e.operation = operation;
					
					expression_string = expression.substring(expression_indices[2],expression_indices[3]+1);
					if(expression_string.charAt(0)=='{'){
						e.right_expression = expressions.get(Integer.parseInt(expression_string.substring(1,expression_string.length()-1)));
					}else{
						e.right_param = Double.parseDouble(expression_string);
					}
					
					expressions.add(e);
					expression=expression.substring(0,expression_indices[0])+"{"+(expressions.size()-1)+"}"+expression.substring(expression_indices[3]+1);
				}
			}	
			
			return e;
		}
		
		/**
		 * 
		 * 
		 * @param expression
		 * @param operator_start_index
		 * @param operator
		 * 
		 * @return
		 */
		protected static int[] getExpressionIndices(String expression,int operator_start_index,String operator){
			int     index  = -1;
			int[]   indices={-1,operator_start_index-1,operator_start_index+operator.length(),-1};
			boolean period_found=false;
			
			
			//Get the left bounds
			period_found=false;
			index=indices[1];
			while(index>0){
				if(!Math.isNumeric(""+expression.charAt(index))&&!(expression.charAt(index)=='{'||expression.charAt(index)=='}')){
					if(expression.charAt(index)=='.'){
						if(period_found){
							throw new InvalidExpressionException("Multiple decimal seperators found while parsing expression.");
						}else{
							period_found=true;
						}
					}else{
						index++;
						
						break;
					}
				}
				
				index--;
			}
			indices[0]=index;
			
			//Get teh right bounds
			period_found=false;
			index=indices[2];
			while(index<(expression.length()-1)){
				if(!Math.isNumeric(""+expression.charAt(index))&&!(expression.charAt(index)=='{'||expression.charAt(index)=='}')){
					if(expression.charAt(index)=='.'){
						if(period_found){
							throw new InvalidExpressionException("Multiple decimal seperators found while parsing expression.");
						}else{
							period_found=true;
						}
					}else{
						index--;
						
						break;
					}
				}
				
				index++;
			}
			indices[3]=index;
			
			
			return indices;
		}
		
		public String toString(){
			String expression="";
			
			
			if(this.left_expression!=null){
				expression+="("+this.left_expression.toString()+")";
			}else{
				expression+=Double.toString(this.left_param);
			}
			
			expression+=this.operation.toString();
			
			if(this.right_expression!=null){
				expression+="("+this.right_expression.toString()+")";
			}else{
				expression+=Double.toString(this.right_param);
			}
			
			
			return expression;
		}
		
		protected static String _validateStringExpression(String expression){
			Constants constant = null;
			char current_character = 0x00;
			int parenthesis_open=0;	
			int sub_start = -1;
			int sub_end = -1;
			
			
			if(expression.length()==0){
				throw new InvalidExpressionException("Expression is empty.");
			}
			
			while((sub_start=expression.indexOf('['))!=-1){
				sub_end = expression.indexOf(']');
				if(sub_end==-1){
					throw new InvalidExpressionException("Unclosed constant declarator found in expression, \""+expression+"\".");
				}
				
				constant = Constants.fromString(expression.substring(sub_start+1,sub_end));
				if(constant==null){
					throw new InvalidExpressionException("Requested constant \""+expression.substring(sub_start+1,sub_end)+"\" could not be found.");
				}
				
				expression=expression.substring(0,sub_start)+constant.getValue()+expression.substring(sub_end+1);
			}
			
			for(int index=0;index<expression.length();index++){
				current_character=expression.charAt(index);
				
				if(current_character=='('){
					parenthesis_open++;
				}else if(current_character==')'){
					parenthesis_open--;
					
					if(parenthesis_open<0){
						throw new InvalidExpressionException("Too many closing parenthesis found in expression, \""+expression+"\"");
					}
				}else if(!(Math.isNumeric(""+current_character)||current_character=='.')){
					if(current_character<=' '){
						expression=expression.substring(0,index)+expression.substring(index+1);
						
						index--;
					}else if(Operation.fromString(""+current_character)==null){
						throw new InvalidExpressionException("Invalid character \""+current_character+"\" found in expression, \""+expression+"\"");
					}
				}
			}
			
			if(parenthesis_open>0){
				throw new InvalidExpressionException("Too many opening parenthesis found in expression, \""+expression+"\"");
			}
			
			
			return expression;
		}
		/*End Other Essential Methods*/
	}
	
	/*protected static Expression _toExpression(String string_expression){
		final Expression.Operation[] operation_order=Expression.Operation.getOperationOrder();
		final String[]                 valid_functions={"sqrt","sin","cos","tan"};
		
		int[]  adjacent=null;
		Expression expression;
		String string_to_convert=null;
		Expression return_expression;
		int    sub_start=-1;
		int    sub_end=-1;
		int    parenthesis_open=0;
		int[]  pow_start=null;
		int[]  pow_end={-1,-1};
		
		
		string_expression=new String(string_expression);
		while((sub_start=string_expression.indexOf('('))!=-1){
			for(sub_end=sub_start+1,parenthesis_open=0;sub_end<string_expression.length();sub_end++){
				if(string_expression.charAt(sub_end)=='('){
					parenthesis_open++;
				}else if(string_expression.charAt(sub_end)==')'){
					if(parenthesis_open==0){
						break;
					}else{
						parenthesis_open--;
					}
				}
			}

			string_expression=string_expression.substring(0,sub_start)+Math.__evaluate(string_expression.substring(sub_start+1,sub_end))+string_expression.substring(sub_end+1);
		}
		
		string_expression=Math.__evaluateExponents(string_expression);
		
		System.out.println("Result of evaluation:  \""+string_expression+"\".");
		
		return return_expression;
	}*/
	
	private static String __evaluateExponents(String expression){
		double d_result=0;
		String result=null;
		int    sign_index=-1;
		int    expression_start=-1;
		int    expression_end=-1;
		
		
		result=new String(expression);
		while((sign_index=result.indexOf('^'))!=-1){
			//Get the location of the start of the expression
			expression_start=sign_index;
			while(expression_start>0){
				expression_start--;
				
				if(!(Math.isNumeric(""+result.charAt(expression_start))||result.charAt(expression_start)=='.')){
					
				}
			}
			if(expression_start<0){
				expression_start++;
			}else if(result.charAt(expression_start)!='-'){
				expression_start++;
			}
			
			if(sign_index-expression_start<=0){
				throw new NumberFormatException();
			}
			
			//Get the location of the end of the expression
			expression_end=sign_index;
			do{
				expression_end++;
				if(result.charAt(expression_end)=='-'){
					expression_end++;
				}
			}while(expression_end<result.length()&&(Math.isNumeric(""+result.charAt(expression_end))||result.charAt(expression_end)=='.'));
			
			if(expression_end-sign_index<=0){
				throw new NumberFormatException();
			}
			
			//Evaluate the expression
			d_result=java.lang.Math.pow(
				Double.parseDouble(result.substring(expression_start,sign_index)),
				Double.parseDouble(result.substring(sign_index+1,expression_end))
			);
						
			//Replace the evaluated expression in the string.
			result=
				result.substring(0,expression_start)
				+Double.toString(d_result)
				+result.substring(expression_end);
		}

		return result;
	}
	
	/**
	 * 
	 * 
	 * @param expression
	 * @return
	 */
	private static int[] getFirstExpressionIndices(String expression){  //(4+5*3)
		boolean dot_found=false;
		int[]   indices=null;
		
		
		
		
		
		return indices;
	}
	
	public static boolean isNumeric(String string){
		return Math.isNumeric(string,10);
	}
	
	public static boolean isNumeric(String string,int base){
		switch(base){
			case 2:
				return Math.isNumeric(string,"01");
			case 8:
				return Math.isNumeric(string,"01234567");
			case 10:
				return Math.isNumeric(string,"0123456789");
			case 16:
				return Math.isNumeric(string,"0123456789ABCDEF");
			default:
				return false;
		}
	}
	
	public static boolean isNumeric(String string,String pool){
		for(int index=0;index<string.length();index++){
			if(pool.indexOf(string.charAt(index))==-1){
				return false;
			}
		}
		
		return true;
	}
}
