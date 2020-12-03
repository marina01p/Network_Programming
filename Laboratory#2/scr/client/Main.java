package client;

public class Main {

    public static void main(String[] args){

        System.out.println("-------------------");
        System.out.println("Client starting...");
        System.out.println("-------------------");

        Client client = new Client();
        client.newClient();

        System.out.println("\n--------------------");
        System.out.println("... End of session");
        System.out.println("--------------------");
    }
}
