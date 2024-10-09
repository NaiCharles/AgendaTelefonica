package AddressBook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    private HashMap<String, String> contactos;

    public AddressBook() {
        contactos = new HashMap<>();
    }

    public void cargarContactos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    contactos.put(datos[0], datos[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar contactos: " + e.getMessage());
        }
    }

    public void guardarContactos(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Map.Entry<String, String> entrada : contactos.entrySet()) {
                bw.write(entrada.getKey() + "," + entrada.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    public void mostrarContactos() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entrada : contactos.entrySet()) {
            System.out.println(entrada.getKey() + " : " + entrada.getValue());
        }
    }

    public void agregarContacto(String numero, String nombre) {
        contactos.put(numero, nombre);
        System.out.println("Contacto agregado: " + numero + " - " + nombre);
    }

    public void eliminarContacto(String numero) {
        if (contactos.containsKey(numero)) {
            contactos.remove(numero);
            System.out.println("Contacto eliminado: " + numero);
        } else {
            System.out.println("El número no existe en la agenda.");
        }
    }

    public static void main(String[] args) {
        AddressBook agenda = new AddressBook();
        Scanner scanner = new Scanner(System.in);
        String archivoContactos = "contactos.txt";
        agenda.cargarContactos(archivoContactos);

        int opcion;
        do {
            System.out.println("\nMenú de la Agenda Telefónica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    agenda.mostrarContactos();
                    break;
                case 2:
                    System.out.print("Ingrese el número: ");
                    String numero = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    agenda.agregarContacto(numero, nombre);
                    break;
                case 3:
                    System.out.print("Ingrese el número del contacto a eliminar: ");
                    String numeroEliminar = scanner.nextLine();
                    agenda.eliminarContacto(numeroEliminar);
                    break;
                case 4:
                    agenda.guardarContactos(archivoContactos);
                    System.out.println("Contactos guardados. Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);

        scanner.close();
    }
}
