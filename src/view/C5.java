package view;
 class A {}
class B extends A {}
class C extends A {}
class D extends Exception {}
class E extends D {}
class F extends D {}
class G extends Exception {}
class C5 {
public static void main (String[] args) throws G {
A x = new B();
m1 (x);
}
static void m1 (A x) throws G {
try {
m2(x);
m3(0);
m4();
}
catch (D y) {
m5(y);
}
}
static void m2 (A x) {
if (x instanceof B) System.out.println("B");
else if (x instanceof C) System.out.println("C");
else System.out.println("A");
}
static void m3 (int i) throws D {
if (i > 0) System.out.println("Positive");
else if (i < 0) System.out.println("Negative");
else throw new E();
}
static void m4 () { System.out.println ("M4"); }
static void m5 (D y) throws G {
if (y instanceof E) System.out.println("E");
else if (y instanceof F) System.out.println("F");
else throw new G();
}
}