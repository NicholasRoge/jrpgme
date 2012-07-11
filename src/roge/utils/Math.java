package roge.utils;

import java.text.NumberFormat;
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
			/**NOP is a special case operator only intended for use when an expression only has one parameter.*/
			NOP("nop",-1,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return left_param;
				}
			}),
			SIN("sin",-1,true,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return java.lang.Math.sin(left_param);
				}
			}),
			COS("cos",-1,true,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return java.lang.Math.cos(left_param);
				}
			}),
			TAN("tan",-1,true,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return java.lang.Math.tan(left_param);
				}
			}),
			SQRT("sqrt",-1,true,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return java.lang.Math.sqrt(left_param);
				}
			}),
			ADDITION("+",0,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return left_param+right_param;
				}
			}),
			SUBTRACTION("-",0,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return left_param-right_param;
				}
			}),
			MULTIPLICATION("*",1,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return left_param*right_param;
				}
			}),
			DIVISION("/",1,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return left_param/right_param;
				}
			}),
			POWER("^",2,false,new OperationExecutor(){
				public double performOperation(double left_param,double right_param){
					return java.lang.Math.pow(left_param,right_param);
				}
			});
			/*End Enumerations*/
			
			private interface OperationExecutor{
				public double performOperation(double left_param,double right_param);
			}
			
			
			private static Operation[] __operation_orders;
			
			private boolean           __is_function;
			private OperationExecutor __operation_executor;
			private int               __operation_order;
			private String            __operation_string;
			
			
			/*Begin Constructors*/			
			private Operation(String operation_string,int operation_order,boolean is_function,OperationExecutor operation_executor){
				this.__operation_string=operation_string;
				this.__operation_order=operation_order;
				this.__is_function=is_function;
				this.__operation_executor=operation_executor;
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
			
			public String toSymbol(){
				return "{"+(char)this.ordinal()+"}";
			}
			
			public static Operation fromSymbol(String symbol){
				char raw_symbol = symbol.substring(1,symbol.length()-2).charAt(0);  //This should only ever wind up as one character.
				
				return Operation.values()[raw_symbol];
			}
			
			/**
			 * 
			 * 
			 * @return
			 */
			public static Operation[] getOperationOrder(){
				List<Operation> operations=null;
				
				
				//Make this a singleton method.
				if(Operation.__operation_orders==null){
					operations=new ArrayList<Operation>();
					for(Operation operation:Operation.values()){
						if(operation.__operation_order<0){
							continue;
						}
						
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
			
			public static Operation getFunction(String query){
				for(Operation operation:Operation.values()){
					if(operation.__is_function&&operation.__operation_string.equals(query)){
						return operation;
					}
				}
				
				return null;
			}
			
			public static boolean isOperator(String query){
				for(Operation operation:Operation.values()){
					if(!operation.__is_function&&operation.toString().equals(query)){
						return true;
					}
				}
				
				return false;
			}
			
			protected double _performOperation(double left_param,double right_param){
				return this.__operation_executor.performOperation(left_param,right_param);
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
			return this.operation._performOperation(this.left_param,this.right_param);
		}
		
		public static Expression fromString(String expression){
			return Expression._fromString(Expression._validateStringExpression(expression));
		}
		
		protected static Expression _fromString(String expression){
			final Operation[] operation_order = Operation.getOperationOrder();
			
			Expression e = null;
			List<Expression> expressions = null;
			int[]      expression_indices = null;
			String     expression_string = null;
			NumberFormat number_format = null;
			Operation  function = null;
			int        sub_start = -1;
			int        sub_end = -1;
			int        parenthesis_open = 0;
			
			
			expressions = new ArrayList<Expression>();
			
			number_format = NumberFormat.getInstance();
			number_format.setGroupingUsed(false);
			number_format.setMaximumFractionDigits(100);
			
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

				
				/*Perform any mathmatic functions and add in multiplication signs when you have something right next to a returned expression that isn't an operator.*/
				function=null;
				
				if(sub_start>0){
					if(!Operation.isOperator(""+expression.charAt(sub_start-1))){
						expression_string="";
						while((sub_start-1-expression_string.length())>-1){  //Essentially, while the current character (as we move to the left) isn't an operator or a closing parenthesis
							if(Operation.isOperator(""+expression.charAt(sub_start-1-expression_string.length()))||expression.charAt(sub_start-1-expression_string.length())==')'){
								break;
							}
							
							expression_string=expression.charAt(sub_start-1-expression_string.length())+expression_string;
						}
						
						function=Operation.getFunction(expression_string);
						if(function==null){  //We have to perform a check for functions
							expression=expression.substring(0,sub_start)+"*"+expression.substring(sub_start);
							sub_start++;
							sub_end++;
						}
					}
				}
				
				if(sub_end<(expression.length()-1)){
					if(!Operation.isOperator(""+expression.charAt(sub_end+1))){
						expression=expression.substring(0,sub_end+1)+"*"+expression.substring(sub_end+1);
						//We added this after the subexpression, so we don't need to increment their values
					}
				}
				
				/*Replace the the substring with its expression*/
				e=Expression._fromString(expression.substring(sub_start+1,sub_end));
				if(function!=null){
					e.left_param = function._performOperation(e.evaluate(),0);
					e.operation = Operation.NOP;
					
					sub_start-=function.toString().length();
				}
				
				if(e.operation==Operation.NOP){
					if(e.left_param<0){
						expression=expression.substring(0,sub_start)+"0"+number_format.format(e.left_param)+expression.substring(sub_end+1);
					}else{
						expression=expression.substring(0,sub_start)+number_format.format(e.left_param)+expression.substring(sub_end+1);
					}
				}else{
					expressions.add(e);
					expression=expression.substring(0,sub_start)+"["+(expressions.size()-1)+"]"+expression.substring(sub_end+1);
				}
			}
			
			
			for(Operation operation:operation_order){						
				while((sub_start=expression.indexOf(operation.toString()))!=-1){			
					expression_indices = Expression.getExpressionIndices(expression, sub_start,operation.toString());
					
					e = new Expression();
					expression_string = expression.substring(expression_indices[0],expression_indices[1]+1);
					if(expression_string.charAt(0)=='['){
						e.left_expression = expressions.get(Integer.parseInt(expression_string.substring(1,expression_string.length()-1)));
					}else{
						e.left_param = Double.parseDouble(expression_string);
					}
					
					e.operation = operation;
					
					expression_string = expression.substring(expression_indices[2],expression_indices[3]+1);
					if(expression_string.charAt(0)=='['){
						e.right_expression = expressions.get(Integer.parseInt(expression_string.substring(1,expression_string.length()-1)));
					}else{
						e.right_param = Double.parseDouble(expression_string);
					}
					
					expressions.add(e);
					expression=expression.substring(0,expression_indices[0])+"["+(expressions.size()-1)+"]"+expression.substring(expression_indices[3]+1);
				}
			}	
			
			if(e==null){
				e = new Expression();
				e.left_param=Double.parseDouble(expression);
				e.operation=Operation.NOP;
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
			while(index>0){  //We won't process the first character.  The reason behind this is that if we've reached the first character, then that first character can only either be a digit or the sign character.  In either event, the program will be allowed to execute normally.
				if(!Math.isNumeric(""+expression.charAt(index))&&!(expression.charAt(index)=='['||expression.charAt(index)==']')){
					if(expression.charAt(index)=='-'){
						break;
					}else if(expression.charAt(index)=='.'){
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
					if(expression.charAt(index)=='-'&&index==indices[2]){
						continue;
					}else if(expression.charAt(index)=='.'){
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
			
			if(this.operation!=Operation.NOP){
				expression+=this.operation.toString();
				
				if(this.right_expression!=null){
					expression+="("+this.right_expression.toString()+")";
				}else{
					expression+=Double.toString(this.right_param);
				}
			}
			
			
			return expression;
		}
		
		/**
		 * Performs error checking on the given expression, and prepares it to be processed by {@link #_fromString(string)}
		 * 
		 * @param expression Expression to be validated and processed
		 * 
		 * @return Returns the processed and validated expression. 
		 */
		protected static String _validateStringExpression(String expression){
			Constants       constant = null;
			char            current_character = 0x00;
			int             nearest_symbol_closing = 0;
			int             nearest_symbol_opening = 0;
			int             parenthesis_open=0;
			boolean         post_clear=false;
			boolean         pre_clear=false;
			Operation[]     operations = null;
			int             sub_start = -1;
			int             sub_end = -1;
			Operation       tmp_operation = null;
			
			
			if(expression.length()==0){
				throw new InvalidExpressionException("Expression is empty.");
			}
			
			/*Go through and replace the constants*/
			while((sub_start=expression.indexOf('['))!=-1){
				sub_end = expression.indexOf(']');
				if(sub_end==-1){
					throw new InvalidExpressionException("Unclosed constant declarator found in expression, \""+expression+"\".");
				}
				
				constant = Constants.fromString(expression.substring(sub_start+1,sub_end));
				if(constant==null){
					throw new InvalidExpressionException("Requested constant \""+expression.substring(sub_start+1,sub_end)+"\" could not be found.");
				}
				
				expression=expression.substring(0,sub_start)+"("+constant.getValue()+")"+expression.substring(sub_end+1);
			}
			
			/*Go through and:
			 *  Ensure there are no mismatched parenthesis
			 *  Replace any negative values with "0-value"
			 */
			for(int index=0;index<expression.length();index++){
				current_character=expression.charAt(index);
				
				if(current_character=='('){
					parenthesis_open++;
				}else if(current_character==')'){
					parenthesis_open--;
					
					if(parenthesis_open<0){
						throw new InvalidExpressionException("Too many closing parenthesis found in expression, \""+expression+"\"");
					}
				}else if(expression.charAt(index)=='-'){
					if(index==0){
						expression = "0"+expression;
					}else{
						if(expression.charAt(index-1)=='('){
							expression = expression.substring(0,index)+"0"+expression.substring(index);
						}
					}
					
					index++;
				}else if(!(Math.isNumeric(""+current_character)||current_character=='.')){
					if(current_character<=' '){
						expression=expression.substring(0,index)+expression.substring(index+1);
						
						index--;
					}else if(!Operation.isOperator(""+current_character)){
						//throw new InvalidExpressionException("Invalid character \""+current_character+"\" found in expression, \""+expression+"\"");
					}
				}
			}
			
			if(parenthesis_open>0){
				throw new InvalidExpressionException("Too many opening parenthesis found in expression, \""+expression+"\"");
			}
			
			/*Replace any operations with their symbol equivalents.*/
			operations = Operation.values();  
			for(int index=0;index<operations.length;index++){  //Order the operations by their length;  Bubble Sort
				for(int bubble=1;bubble<operations.length;bubble++){
					if(operations[bubble].toString().length()>operations[bubble-1].toString().length()){
						tmp_operation = operations[bubble];
						operations[bubble] = operations[bubble-1];
						operations[bubble-1] = tmp_operation;
					}
				}
			}
			
			for(Operation operation:Operation.values()){
				while((sub_start=expression.indexOf(operation.toString()))!=-1){
					sub_end = sub_start+operation.toString().length();
					pre_clear = false;
					post_clear = false;
					
					//TODO_HIGH:  Make sure we haven't found an operation within a symbol before we continue.
					//TODO_HIGH:  Write some sort of error checking for this.  If a user types in (mistakenly) cosine rather than cos, it will mistakenly pick up the sin portion of that, leaving the expression as co{raw_symbol}e.
					expression = expression.substring(0,sub_start) + operation.toSymbol() + expression.substring(sub_end);
				}
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
