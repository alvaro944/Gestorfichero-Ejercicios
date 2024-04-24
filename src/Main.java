import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcion = -1;

        while(opcion != 0){
            System.out.println("Introduce una opción: ");
            opcion = sc.nextInt();

            switch (opcion){
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1:
                    System.out.println("Escribir...");

                    break;
                case 2:
                    System.out.println("Recuperar...");
                    break;
                case 3:
                    System.out.println("Modificar...");
                    break;
            }
        }
    }
}