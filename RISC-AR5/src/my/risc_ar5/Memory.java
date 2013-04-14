package my.risc_ar5;

/**
 * Class designed for bit and memory segment
 * structure, with auxiliary methods.
 * @author José G. Gonzalez Torres
 */
public class Memory {
    private int arrayLength;    //Number of bits per array.
    private int numArrays;      //Number of memory segments.
    private int[][] memory;     //Memory array.
    

    /**
     * Creates a number of memory segments of bit length (arrayLength)
     * and initializes values to 0.
     * @param arrayLength number of bits
     * @param numArrays number of memory segments
     */
    public Memory(int arrayLength, int numArrays) {
        this.arrayLength = arrayLength;
        this.numArrays = numArrays;
        this.memory = new int[numArrays][arrayLength];
        for(int i = 0;i<numArrays;i++){
            for(int j=0;j<arrayLength;j++){
                memory[i][j]=0;
            }
        }
    }
    
    /**
     * Override toString and output simple bit memory string.
     * @param arrayNum memory segment to output
     * @return memory segment string
     */
    public String toString(int arrayNum){
        String out = "";
        for(int i=0;i < arrayLength; i++){
            out = memory[arrayNum][i]+out;
        }
        return out;
    }
    
    /**
     * Outputs the memory segment into its hexadecimal equivalent
     * @param arrayNum number of memory segment
     * @return hexadecimal string value
     */
    public String toHex(int arrayNum){
        String out = "";
        String bina = this.toString(arrayNum);
        int total = Integer.parseInt(bina, 2);
        out = Integer.toHexString(total);
        out = out.toUpperCase();
        int initialSize = out.length();
        if(out.length()<(int)arrayLength/4){
            for(int i=0;i<(((int) arrayLength/4)-initialSize);i++){
                out = "0"+out;
            }
        }
        return out;
    }
    
    /**
     * Extract a select group of bits from a memory segment.
     * @param arrayNum memory segment
     * @param initial least significant bit
     * @param fini most significant bit
     * @return string bit subsegment
     */
    public String getSub(int arrayNum, int initial, int fini){
        String out = "";
        for(int i=initial; i<=fini; i++){
            out = memory[arrayNum][i] + out;
        }
        return out;
    }
    
    /**
     * Resets internal values of entire memory segment
     * to 0.
     */
    public void reset(){
        for(int i=0; i<numArrays;i++){
            for(int j=0; j<arrayLength;j++){
                memory[i][j]=0;
            }
        }
    }
    
    /**
     * Insert hexadecimal value into memory segment
     * @param hexaIn hexadecimal string
     * @param numArray memory segment index
     */
    public void inputHex(String hexaIn, int numArray){
        int i  = Integer.parseInt(hexaIn, 16);
        String binaStri = Integer.toBinaryString(i);
        for(int j=0;j<binaStri.length();j++){
            memory[numArray][j]= Integer.parseInt(""+binaStri.charAt(binaStri.length()-1-j));
        }
    }
    
    /**
     * Used to print out memory in GUI. DO NOT USE FOR REGISTERS
     * @return memory print out String.
     */
    public String printMemory(){
        String out = "";
        for(int i=0;i<numArrays;i=i+2){
            String index = Integer.toHexString(i);
            if(index.length()<2){
                index = "0"+index;
            }
            index = index.toUpperCase();
            out = out + index +": "+this.toHex(i)+this.toHex(i+1)+"\n";
        }
        return out;
    }
    
    /**
     * Function to return the integer representation
     * of a memory cell or register.
     * @param numArray number of array
     * @return the integer representation of the cell
     */
    public int toInt(){
        int temp;
        temp = Integer.parseInt(this.toString(0), 2);
        return temp;
    }
    
    /**
     * Obtain memory segment
     * @param numArray - segment location
     * @return memory segment in an integer array
     */
    public int[] getArray(int numArray){
        return memory[numArray];
    }
    
    /**
     * Inject array into memory segment
     * @param array - new array to inject
     * @param numArray - memory location
     */
    public void setArray(int[] array, int numArray){
        memory[numArray] = array;
    }
}
