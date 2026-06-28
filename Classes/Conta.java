package Classes;

public class Conta {
    private int pin;
    private int numeroConta;
    private float saldo;
    private boolean bloqueada;

    public Conta(int pin, int numeroConta, float saldo, boolean bloqueada){
        this.pin = pin;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.bloqueada = false;
    }
}
