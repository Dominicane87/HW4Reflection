package vladimir.gorin;

public class Person {
    byte b=1;
    short s=1;
    int i=1;
    long l=1;
    double d=1;
    float f=1;
    boolean aBoolean=true;
    char aChar=12;
    String string="wer";


    @Override
    public String toString() {
        return "b: "+b+" s: "+s+" i: "+i+" l: "+l+" d: "+d+" f: "+f+" aBoolean "+aBoolean+" aChar "+aChar+" String: "+string;
    }
}
