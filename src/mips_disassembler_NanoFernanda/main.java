package mips_disassembler_NanoFernanda;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		
	int addr = 0x9A040;
		
	List <Integer> inputList = new ArrayList <>();
	
	inputList.add(0x032BA020);
    inputList.add(0x8CE90014);
    inputList.add(0x12A90003);
    inputList.add(0x022DA822);
    inputList.add(0xADB30020);
    inputList.add(0x02697824);
    inputList.add(0xAE8FFFF4);
    inputList.add(0x018C6020);
    inputList.add(0x02A4A825);
    inputList.add(0x158FFFF7);
    inputList.add(0x8ECDFFF0);
    inputList.add(0x1107000A);
    inputList.add(0x0345E822);
    
    	
    	for (int input:inputList) { //int i; i<=inputList.length; i++ checking every item on the list until the last
    		String type = findInstructionType(input); //Saves function results into the variable type
    	    String function;
    	    int rt;
    	    int rs;
    	    int maskRT = 0x1F0000;
    	    int maskRS = 0x3E00000;
    	    String finalResult;
    	    
    	    
    	    if (type.equals("rType")) {
    	    	
    	    int maskRTypeFunc = 0x3F; 
    	    function = returnFunction(input, maskRTypeFunc); // Saves mips function operation in function variable 
    	    if (!function.equals("ignore")) {
    	    	 
        	    int maskRD = 0xF800;
        	    
        	    int rd = (input&maskRD) >> 11;
        	    rt = (input&maskRT) >> 16;
        	    rs = (input&maskRS) >> 21;
        	    
        	    finalResult = Integer.toHexString(addr) + " " + function + " $" + rd + ", $" + rs + ", $";
    	    } else {
    	    	finalResult = " ";
    	    }
    	    
    	    } else {
    	    int maskITypeFunc = 0x3F;
    	    function = returnFunction(input >> 26, maskITypeFunc);
    	    
    	    int maskAddress = 0xFFFF;
    	    int address = maskAddress&input;
    	    rt = (input&maskRT) >> 16;
    	    rs = (input&maskRS) >> 21;
    	     
    	    if (function.equals("beq")||function.equals("bne")) {
    	    	finalResult = Integer.toHexString(addr) + " " + function + rs + ", $" + rt + ", address " + Integer.toHexString(addr + 4 + (address << 2));
    	    	
    	    } else if (!function.equals("ignore")) {
    	    	finalResult = Integer.toHexString(addr)+ " " + function + "$" + rt + ", " + address + "($" + rs + ")";
    	    }
    	    else {
    	    	finalResult = " ";    	    }
    	    } 
    	    System.out.println(finalResult);
    	    addr += 4;
    	}
    
	}
	
	public static String findInstructionType(int input) { // function that returns MIPS instruction type
		
		int maskOperation = 0xFC000000;
	    int resultOperation=input&maskOperation; 
	    
	    String type;
	    
	    if (resultOperation == 0b000000 ) {
	    	type = "rType";
	    } else {
	    	type = "other";
	    }
	    return type;
		
	
	}
	
	public static String returnFunction(int input, int maskFunction) { //function that returns function operation
		
    	int result = input&maskFunction;
    	String function;
    	
    	// System.out.println(Integer.toBinaryString(result)); //printar tudo que esta passando
    	
    	switch(result) {
    		case(0b100000):
    			function = "add";
    			break;
    		case(0b100010):
    			function = "sub";
    			break; 
            case (0b100100):
                function = "and";
                break;
            case (0b100101):
                function = "or";
                break;
            case (0b101010):
                function = "slt";
                break;
            case (0b100011):
                function = "lw";
                break;
            case (0b101011):
                function = "sw";
                break;
            case (0b000100):
                function = "beq";
                break;
            case (0b000101):
                function = "bne";
                break;
            default:
                function = "ignore";
                break;
		
			}
    	return function; 
	}
}