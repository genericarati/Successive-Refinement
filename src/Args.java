import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Args {

	private String schema;
	private String[] args;
	private boolean valid = true;
	private Set<Character> unexpectedArguments = new TreeSet<Character>();
	private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();
	private Map<Character, String> stringArgs = new HashMap<Character, String>();
	private Map<Character, Integer> intArgs = new HashMap<Character, Integer>();
	private Set<Character> argsFound = new HashSet<Character>();
	private int currentArgument;
	private char errorArgumentId = '\0';
	private String errorParameter = "TILT";
	private ErrorCode errorCode = ErrorCode.OK;
	
	private enum ErrorCode {
		OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT;
	}
	
	public Args(String schema, String[] args){
		this.schema = schema;
		this.args = args;
		valid = parse();
	}

	private boolean parse() throws ParseException {
		if (schema.length() == 0 && args.length ==0){
			return true;
			parseSchema();
			try{
				parseArguments();
			}catch (ArgsException e){
				
			}
			return valid;
		}
	}

	private boolean parseSchema() throws ParseException {
	  for (String element : schema.split(",")){
		  if (element.length() > 0){
			  String trimmedElement = element.trim();
			  parseSchemaElement(trimmedElement);
		  }
	  }
	  return true;
	}

	private void parseSchemaElement(String element) throws ParseException{
	 char elementId = element.charAt(0);
	 String elementTail = element.substring(1);
	 validateSchemaElementId(elementId);
	 if(isBooleanSchemaElement(elementTail)){
		 parseBooleanSchemaElement(elementId);
	 }else if (isStringSchemaElement(elementTail)){
		 parseStringSchemaElement(elementId);
	 }else if (isIntegerSchemaElement(elementTail)){
		 parseIntegerSchemaElement(elementId);
	 }else {
		 throw new ParseException(String.format("Argument: %c has invalid format: %s.", elementId, elementTail),0);
	 }
	}
	
	
}
