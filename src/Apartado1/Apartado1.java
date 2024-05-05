package Apartado1;

import java.io.*;
import java.util.Scanner;

public class Apartado1 {
    //Añadir
    //Recuperar
    //Modificar
    public static Scanner sc = new Scanner(System.in);

    private static final String nombFich = "./src/Apartado1/Apartado1.txt";
    private static final String SPLIT = "//";

    public static void crearFichero() {
        File file = new File(nombFich);
        if(!file.exists()){
            try {
                System.out.println("\n[+] Creando el fichero " + nombFich);
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else{
            System.out.println("\n[+] El fichero " + nombFich + " está creado");
        }
    }

    public static void escribirRegistro(String DNI, String nombre){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombFich, true))){
            String texto = DNI + SPLIT + nombre;
            if(campoClaveUnico(DNI)){
                bw.write(texto);
                bw.newLine();
                bw.flush();
                System.out.println("\n[+] Se ha añadido el registro DNI: " + DNI + " Nombre: " + nombre);
            } else{
                System.out.println("\n[!] El DNI " + DNI + " ya está registrado");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean campoClaveUnico(String DNI){
        try (BufferedReader br = new BufferedReader(new FileReader(nombFich))){
            String linea;
            while((linea = br.readLine()) != null) {
                if (linea.split(SPLIT)[0].equals(DNI)){
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void recuperarRegistro(String DNI){
        try(BufferedReader br = new BufferedReader(new FileReader(nombFich))) {
            String linea;
            while ((linea = br.readLine()) != null){
                if (linea.split(SPLIT)[0].equals(DNI)){
                    System.out.println("\n[+] Registro recuperado: ");
                    System.out.println("\tDNI: " + linea.split(SPLIT)[0] + " Nombre: " + linea.split(SPLIT)[1]);
                    return;
                }
            }
            System.out.println("\n[!] No hay coincidencias para el DNI " + DNI);
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo encontrar el archivo especificado: " + nombFich);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ocurrió un error al leer el archivo");
            e.printStackTrace();
        }
    }

    public static void modificarRegistro(String DNI){
        File fileOriginal = new File(nombFich);
        File fileTemp = new File("./src/Apartado1/Apartado1Temp.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(nombFich));
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            boolean cambioRealizado = false;

            while((linea = br.readLine()) != null){
                if(linea.split(SPLIT)[0].equals(DNI)) {
                    System.out.print("\n[+] Introduce el nombre para sustituir por " + linea.split(SPLIT)[1] + " :");
                    String nuevoNomb = sc.nextLine();
                    linea = linea.split(SPLIT)[0] + SPLIT + nuevoNomb;
                    cambioRealizado = true;
                }
                //Escribimos en el archivo temporal
                bw.write(linea);
                bw.newLine();
            }

            if (!cambioRealizado) {
                System.out.println("\n[!] DNI no encontrado.");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Renombrar el archivo temporal para reemplazar el original
        if (fileOriginal.delete()) {
            fileTemp.renameTo(fileOriginal);
        } else {
            System.err.println("No se pudo eliminar el archivo original.");
        }
    }

    public static void leerFichero(){
        try(BufferedReader br = new BufferedReader(new FileReader(nombFich))) {
            String linea;
            System.out.println("\n[*] El contenido del fichero es: ");
            while ((linea = br.readLine()) != null){
                System.out.println("\tDNI: " + linea.split(SPLIT)[0] + " Nombre: " + linea.split(SPLIT)[1]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    break;
            }
        }
    }
}
