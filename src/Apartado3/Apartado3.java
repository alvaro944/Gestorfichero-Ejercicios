package Apartado3;

import java.io.*;
import java.util.Scanner;

public class Apartado3 {
    //El fichero va a consistir de 4 campos, ID (campo Clave), DNI, Nombre y Departamento
    private static final String nombFich = "./src/Apartado3/Apartado3.txt";
    private static final String SPLIT = "//";

    private static String formato = "";
    private static int longFormato;
    private static Scanner sc = new Scanner(System.in);

    public static void crearFichero() {
        File fichero = new File(nombFich);
        try {
            if (!fichero.exists()) {
                fichero.createNewFile();
                System.out.println("\n[+] Fichero creado correctamente");
            } else {
                System.out.println("\n[+] El fichero ya existe");
            }

        } catch (IOException e) {
            System.out.println("\n[!] No se ha podido crear el nuevo fichero");
        }
    }

    public static void crearFormato() {

        String opcion = "";
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(nombFich));
             BufferedWriter bw = new BufferedWriter(new FileWriter(nombFich, true))) {
            if ((linea = br.readLine()) == null) {
                do {
                    System.out.print("\n[+] Introduce un campo para el formato del fichero (<Enter> para finalizar): ");
                    opcion = sc.nextLine();
                    if (opcion != "") {
                        formato += opcion + SPLIT;
                    }
                } while (!opcion.equals(""));

                longFormato = formato.split(SPLIT).length;
                bw.write(formato);
                bw.newLine();
                bw.flush();

                System.out.println("\n[+] El formato se ha registrado correctamente");


            } else {
                System.out.println("\n[+] Formato del fichero recuperado");
                longFormato = linea.split(SPLIT).length;
                for (int i = 0; i < longFormato; i++) {
                    formato += linea.split(SPLIT)[i] + SPLIT;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribirRegistro() {
        String escritura = "";
        String dato = "";
        for (int i = 0; i < longFormato; i++) {
            System.out.print("\n[+] Introduce un " + formato.split(SPLIT)[i] + ": ");
            dato = sc.nextLine();
            escritura += dato + SPLIT;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombFich, true))) {
            bw.write(escritura);
            bw.newLine();
            bw.flush();

            System.out.println("\n[+] El registro se ha escrito correctamente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leerFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(nombFich))) {
            String linea = br.readLine(); //Saltamos la línea del formato

            System.out.println("\n[+] Contenido del fichero:");
            while ((linea = br.readLine()) != null) {
                String coma = ", ";
                for (int i = 0; i < longFormato; i++) {
                    if (i == longFormato - 1) {
                        coma = "";
                    }
                    System.out.print(formato.split(SPLIT)[i] + ": " + linea.split(SPLIT)[i] + coma);
                }
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void recuperarRegistro() {
        System.out.print("\n[+] Introduce un " + formato.split(SPLIT)[0] + " a buscar: ");
        String textoBusqueda = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(nombFich))) {
            String linea = br.readLine(); //Saltamos la línea del formato

            while ((linea = br.readLine()) != null) {
                String coma = ", ";
                for (int i = 0; i < longFormato; i++) {
                    if ((linea.split(SPLIT)[0].equals(textoBusqueda))) {
                        System.out.println("\n[+] Coincidencia encontrada: ");
                        for (int f = 0; f < longFormato; f++) {
                            if (f == longFormato - 1) {
                                coma = "";
                            }
                            System.out.print(formato.split(SPLIT)[f] + ": " + linea.split(SPLIT)[f] + coma);
                        }
                        System.out.println();
                        return;
                    }
                }
            }
            System.out.println("\n[!] No se han encontrado coincidencias para la busqueda");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modificarRegistro() {
        File fileOriginal = new File(nombFich);
        File fileTemp = new File(nombFich + ".temp");
        String dato;
        String escritura = "";

        System.out.print("\n[+] Introduce un " + formato.split(SPLIT)[0] + " a modificar: ");
        String textoBusqueda = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(nombFich));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp, true))) {
            String linea = br.readLine(); //Saltamos la línea del formato
            bw.write(linea);
            bw.newLine();

            while ((linea = br.readLine()) != null) {
                String coma = ", ";

                if ((linea.split(SPLIT)[0].equals(textoBusqueda))) {
                    escritura += linea.split(SPLIT)[0] + SPLIT;
                    System.out.println("\n[+] Coincidencia encontrada, vamos a modificar el registro: ");
                    for (int f = 1; f < longFormato; f++) {
                        System.out.print("\n[+] Introduce un " + formato.split(SPLIT)[f] + ": ");
                        dato = sc.nextLine();
                        escritura += dato + SPLIT;
                    }
                    bw.write(escritura);
                    bw.newLine();
                    bw.flush();

                } else {
                    bw.write(linea);
                    bw.newLine();
                    bw.flush();
                }
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

    public static void borrarRegsitro() {
        File fileOriginal = new File(nombFich);
        File fileTemp = new File(nombFich + ".temp");

        System.out.print("\n[+] Introduce un " + formato.split(SPLIT)[0] + " para borrar: ");
        String textoBusqueda = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(nombFich));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp, true))) {
            String linea = br.readLine(); //Saltamos la línea del formato
            bw.write(linea);
            bw.newLine();

            while ((linea = br.readLine()) != null) {

                if ((linea.split(SPLIT)[0].equals(textoBusqueda))) {
                    linea = "!!" + linea;
                    System.out.println("\n[+] Coincidencia encontrada, se marca el registro como borrado ");
                    bw.write(linea);
                    bw.newLine();
                    bw.flush();

                } else {
                    bw.write(linea);
                    bw.newLine();
                    bw.flush();
                }
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


    public static void menu() {
        System.out.println("\nMENÚ");
        System.out.println("0 - Salir");
        System.out.println("1 - Escribir en fichero");
        System.out.println("2 - Recuperar registro");
        System.out.println("3 - Modificar registro");
        System.out.println("4 - Leer fichero");
        System.out.println("5 - Borrar registro");
    }

    public static void main(String[] args) {

        int opcion = -1;
        crearFichero();
        crearFormato();

        while (opcion != 0) {
            menu();
            System.out.print("\n[*] Introduce una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1: //Escribir
                    escribirRegistro();
                    break;
                case 2:
                    recuperarRegistro(); //Recuperar registro
                    break;
                case 3:
                    modificarRegistro(); //Modificar registro
                    break;
                case 4:
                    leerFichero(); //Leer fichero
                    break;
                case 5:
                    borrarRegsitro();
                    break;
            }
        }
    }
}
