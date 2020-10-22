public class Main {

    public static void main(String[] args) {

        System.out.println("-------------------- E X T R A C T I N G -- D A T A --------------------\n");

        DataReader dataReader = new DataReader();
        int server = Integer.parseInt(dataReader.getProperty("port"));

        if (new URLConnection().initiate()) {
            System.out.println("-------------------- D A T A -- E X T R A C T E D --------------------");
        }

        Server thisServer = new Server();
        thisServer.start(server);
    }
}
