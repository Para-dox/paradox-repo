/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.risc_ar5;

/**
 *
 * @author JoséGerardo
 */
public class Test {
    public static void main(String[] params){
        String bin = Integer.toBinaryString(Integer.parseInt(("1000"),16));
        bin = "000"+bin;
        System.out.println(Integer.parseInt(bin.substring(0, 5),2));
    }
}
