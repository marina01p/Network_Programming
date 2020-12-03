package server;



public class Main {

    public static void main(String[] args) {
        System.out.println("-------------------");
        System.out.println("Server starting... ");
        System.out.println("-------------------");

        Server server = new Server();
        server.newServer();

        System.out.println("\n--------------------");
        System.out.println("... End of session");
        System.out.println("--------------------");

    }
}
