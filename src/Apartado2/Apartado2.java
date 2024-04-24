package Apartado2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Apartado2 {
    //El fichero va a consistir de 4 campos, ID (campo Clave), DNI, Nombre y Departamento
    private static final String nombFich = "./src/Apartado2/Apartado2.txt";
    private static final String SPLIT = "//";
    private static Scanner sc = new Scanner(System.in);

    public static void crearFichero(){
        File fichero = new File(nombFich);
        try{
            if(!fichero.exists()){
                fichero.createNewFile();
                System.out.println("\n[+] Fichero creado correctamente");
            } else {
                System.out.println("\n[+] El fichero ya existe");
            }

        } catch (IOException e) {
            System.out.println("\n[!] No se ha podido crear el nuevo fichero");
        }
    }

    public static void escribirRegistro(){
        
    }

    public static void menu(){
        System.out.println("\nMENÚ");
        System.out.println("0 - Salir");
        System.out.println("1 - Escribir en fichero");
        System.out.println("2 - Recuperar registro");
        System.out.println("3 - Modificar registro");
        System.out.println("4 - Leer fichero");
    }

    public static void main(String[] args) {

        int opcion = -1;

        while(opcion != 0){
            menu();
            System.out.print("\n[*] Introduce una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1:
                    System.out.println("Escribir...");
                    crearFichero();
                    escribirRegistro("48776886G", "Álvaro Cervantes");
                    escribirRegistro("12345678A", "Pepe Cervantes");
                    escribirRegistro("98765432Z", "Juan Cervantes");
                    escribirRegistro("48776886G", "Álvaro Cervantes");

                    break;
                case 2:
                    System.out.println("Recuperar...");
                    recuperarRegistro("12345678A");
                    recuperarRegistro("45685293Y");
                    break;
                case 3:
                    System.out.println("Modificar...");
                    modificarRegistro("48776886G");
                    break;
                case 4:
                    System.out.println("Leer Fichero");
                    leerFichero();
            }
        }
    }
}
