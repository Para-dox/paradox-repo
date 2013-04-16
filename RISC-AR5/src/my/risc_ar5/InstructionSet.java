package my.risc_ar5;

public class InstructionSet {

    /**
     * Bitwise and operation
     *
     * @param accumulator - accumulator
     * @param reg - register operand
     * @param registerNum - register number
     * @param statusRegister - status register
     */
    public static void AND(Memory accumulator, Memory reg, int registerNum, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] regi = reg.getArray(registerNum);
        int[] stat = statusRegister.getArray(0);
        for (int i = 0; i < regi.length; i++) {
            acc[i] = acc[i] & regi[i];
        }
        checkStatus(acc, regi);
        accumulator.setArray(acc, 0);
        reg.setArray(regi, registerNum);
        statusRegister.setArray(stat, 0);
    }

    /**
     * Bitwise or operation
     *
     * @param accumulator - accumulator
     * @param reg - register operand
     * @param registerNum - register number
     * @param statusRegister - status register
     */
    public static void OR(Memory accumulator, Memory reg, int registerNum, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] regi = reg.getArray(registerNum);
        int[] stat = statusRegister.getArray(0);
        for (int i = 0; i < regi.length; i++) {
            acc[i] = acc[i] | regi[i];
        }
        checkStatus(acc, stat);
        accumulator.setArray(acc, 0);
        reg.setArray(regi, registerNum);
        statusRegister.setArray(stat, 0);
    }

    /**
     * 2's complement addition
     *
     * @param accumulator - accumulator
     * @param reg - register operand
     * @param registerNum - register number
     * @param statusRegister - status register
     */
    public static void ADDC(Memory accumulator, Memory reg, int registerNum, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] regi = reg.getArray(registerNum);
        int[] stat = statusRegister.getArray(0);
        int[] s = new int[8];
        int car = stat[1];
        for (int i=7; i>=0; i--) {
            if (car + acc[i] + regi[i] == 0) {
                car = 0;
                s[i] = 0;
            } else if (car + acc[i] + regi[i] == 1) {
                car = 0;
                s[i] = 1;
            } else if (car + acc[i] + regi[i] == 2) {
                car = 1;
                s[i] = 0;
            } else {
                car = 1;
                s[i] = 1;
            }
        }
        stat[1] = car;
        checkStatus(acc, stat);
        if (s[s.length - 1] == 1 && acc[acc.length - 1] == 0 && regi[regi.length - 1] == 0) {
            stat[3] = 1;
        }

        if (s[s.length - 1] == 0 && acc[acc.length - 1] == 1 && regi[regi.length - 1] == 1) {
            stat[3] = 1;
        }
        accumulator.setArray(acc, 0);
        reg.setArray(regi, registerNum);
        statusRegister.setArray(stat, 0);
    }

    /**
     * 2's complement subtract
     *
     * @param accumulator - accumulator
     * @param reg - register operand
     * @param registerNum - register number
     * @param statusRegister - status register
     */
    public static void SUB(Memory accumulator, Memory reg, int registerNum, Memory statusRegister) {
        int[] regi = reg.getArray(registerNum);
        twosComplement(regi);
        reg.setArray(regi, registerNum);
        ADDC(accumulator, reg, registerNum, statusRegister);
    }
    public static void MUL(int[] accumulator, int[] reg,int[] statusRegister){
	
	int[] res = new int[8];	
	int[] res1 = new int[8];	
	int[] res2 = new int[8];	
	int[] res3 = new int[8];	
	int[] n1 = new int[8];	
	int[] n2 = new int[8];	
	int[] n3 = new int[8];	
	
	//multiplication of 4 least significant bits
	for(int i =0; i < 4;i++){
	
	res[i] =reg[0]&accumulator[i];
	res1[i] =reg[1]&accumulator[i];
	res2[i] =reg[2]&accumulator[i];
	res3[i] =reg[3]&accumulator[i];
		
	}
	
	//shifts result of second multiplication
	for(int i=0;i<7; i++){
		n1[i+1] = res1[i];
			
	}
	//shifts result of third multiplication
	for(int i=0;i<6; i++){
		n2[i+2] = res2[i];
			
	}
	//shifts result of four multiplication
	for(int i=0;i<5; i++){
		n3[i+3] = res3[i];
			
	}

	//use method addc to sum res + n1 + n2 + n3

	
	/* uses the result of the multiplication  
	int t = 0;
	for(int i1=0; i1<s.length-1;i1++){
		t= t + s[i1]; 		
	}
	if(t == 0)
		statusRegister[0]=1;
	else
		statusRegister[0]=0;
	
	
	if(s[s.length-1] == 1)
		statusRegister[2]=1;
	else
		statusRegister[2]=0;
	
	if(s[s.length-1]== 1 && accumulator[accumulator.length-1] ==0 && reg[reg.length-1] ==0){
		
		statusRegister[3]=1;
		
	}

	if(s[s.length-1]== 0 &&accumulator[accumulator.length-1] ==1 && reg[reg.length-1] ==1){
		
		statusRegister[3]=1;
		
	}*/	
	
}


    /**
     * 2's complement operation
     *
     * @param arr - memory segment
     */
    private static void twosComplement(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1 && count == 0) {
                count++;
            } else if (count == 1) {
                if (arr[i] == 0) {
                    arr[i] = 1;
                } else {
                    arr[i] = 0;
                }

            }
        }
    }

    /**
     * 2's complement negation
     *
     * @param accumulator - accumulator
     * @param statusRegister - status register
     */
    public static void NEG(Memory accumulator, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] stat = statusRegister.getArray(0);
        twosComplement(acc);
        checkStatus(acc, stat);
        accumulator.setArray(acc, 0);
        statusRegister.setArray(stat, 0);
    }

    /**
     * Bitwise not operation
     *
     * @param accumulator - accumulator
     * @param statusRegister - status register
     */
    public static void NOT(Memory accumulator, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] stat = statusRegister.getArray(0);
        for (int i = 0; i < acc.length; i++) {
            if (acc[i] != 1) {
                acc[i] = 1;
            } else {
                acc[i] = 0;
            }
        }
        checkStatus(acc, stat);
        accumulator.setArray(acc, 0);
        statusRegister.setArray(stat, 0);
    }

    /**
     * Rotate left through carry
     *
     * @param accumulator - accumulator
     * @param statusRegister - status register
     */
    public static void RLC(Memory accumulator, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] stat = statusRegister.getArray(0);
        int carry = stat[1];
        stat[1] = acc[acc.length - 1];
        for (int i = (acc.length - 1); i > 0; i--) {
            acc[i] = acc[i - 1];
        }
        acc[0] = carry;
        checkStatus(acc, stat);
        accumulator.setArray(acc, 0);
        statusRegister.setArray(stat, 0);

    }
    
    /**
     * Rotate right through carry
     * @param accumulator
     * @param statusRegister 
     */
    public static void RRC(Memory accumulator, Memory statusRegister){
        int[] acc = accumulator.getArray(0);
        int[] stat = statusRegister.getArray(0);
	int [] n = new int[8];
	int car = stat[2];
	stat[2] = acc[0];
	n[7] = car;
        System.arraycopy(acc, 0, n, 1, acc.length-1);
        System.arraycopy(n, 0, acc, 0, n.length);
	checkStatus(acc,stat);
        accumulator.setArray(acc, 0);
        statusRegister.setArray(stat, 0);
	}

    /**
     * Bitwise and operation
     *
     * @param accumulator - accumulator
     * @param reg - register operand
     * @param registerNum - register number
     * @param statusRegister - status register
     */
    public static void LDA(Memory accumulator, Memory reg, int registerNum, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] regi = reg.getArray(registerNum);
        int[] stat = statusRegister.getArray(0);
        System.arraycopy(regi, 0, acc, 0, regi.length);
        checkStatus(acc, regi);
        accumulator.setArray(acc, 0);
        reg.setArray(regi, registerNum);
        statusRegister.setArray(stat, 0);
    }

    /**
     * Load into accumulator immediate operand
     *
     * @param accumulator - accumulator
     * @param value - binary string value
     * @param statusRegister - status register
     */
    public static void LDI(Memory accumulator, String value, Memory statusRegister) {
        int[] acc = accumulator.getArray(0);
        int[] stat = statusRegister.getArray(0);
        for (int i = acc.length - 1; i >= 0; i--) {
            acc[7 - i] = ((int) value.charAt(i)) - 48;
        }
        checkStatus(acc, stat);
        accumulator.setArray(acc, 0);
        statusRegister.setArray(stat, 0);
    }

    /**
     * Branch if zero 
     * @param statusRegister - status register
     * @param registers - register map
     * @param programCounter - program counter
     */
    public static void BRZ(Memory statusRegister, Memory registers, Memory programCounter) {
        int[] stat = statusRegister.getArray(0);
        int[] reg7 = registers.getArray(7);
        int[] prgc = programCounter.getArray(0);
        if (stat[3] == 1) {
            System.arraycopy(reg7, 0, prgc, 0, reg7.length);
        }
        registers.setArray(reg7, 7);
        statusRegister.setArray(stat, 0);
        programCounter.setArray(prgc, 0);
    }

    /**
     * Branch if carry
     * @param statusRegister - status register
     * @param registers - register map
     * @param programCounter - program counter
     */
    public static void BRC(Memory statusRegister, Memory registers, Memory programCounter) {
        int[] stat = statusRegister.getArray(0);
        int[] reg7 = registers.getArray(7);
        int[] prgc = programCounter.getArray(0);
        if (stat[2] == 1) {
            System.arraycopy(reg7, 0, prgc, 0, reg7.length);
        }
        registers.setArray(reg7, 7);
        statusRegister.setArray(stat, 0);
        programCounter.setArray(prgc, 0);
    }

    /**
     * Branch if negative
     * @param statusRegister - status register
     * @param registers - register map
     * @param programCounter - program counter
     */
    public static void BRN(Memory statusRegister, Memory registers, Memory programCounter) {
        int[] stat = statusRegister.getArray(0);
        int[] reg7 = registers.getArray(7);
        int[] prgc = programCounter.getArray(0);
        if (stat[1] == 1) {
            System.arraycopy(reg7, 0, prgc, 0, reg7.length);
        }
        registers.setArray(reg7, 7);
        statusRegister.setArray(stat, 0);
        programCounter.setArray(prgc, 0);
    }

    /**
     * Branch if overflow 
     * @param statusRegister - status register
     * @param registers - register map
     * @param programCounter - program counter
     */
    public static void BRO(Memory statusRegister, Memory registers, Memory programCounter) {
        int[] stat = statusRegister.getArray(0);
        int[] reg7 = registers.getArray(7);
        int[] prgc = programCounter.getArray(0);
        if (stat[0] == 1) {
            System.arraycopy(reg7, 0, prgc, 0, reg7.length);
        }
        registers.setArray(reg7, 7);
        statusRegister.setArray(stat, 0);
        programCounter.setArray(prgc, 0);
    }

    /**
     * No operation
     */
    public static void NOP() {
    }

    /**
     * Check accumulator status
     * @param accumulator - accumulator
     * @param statusRegister - status register
     */
    private static void checkStatus(int[] accumulator, int[] statusRegister) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            if (accumulator[i] == 0) {
                count++;
            }
        }
        if (count == 8) {
            statusRegister[3] = 1;
        } else {
            statusRegister[3] = 0;
        }
        if (accumulator[7] == 1) {
            statusRegister[1] = 1;
        } else {
            statusRegister[1] = 0;
        }
    }
}
