import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main {
    private ArrayList<String> listCars = new ArrayList<String>();
    public static void main(String[] args) {
        new Main().go();
    }
    public void go(){
        parseMainPage();

        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            while (true){
                Socket socket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(listCars);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void parseMainPage(){
        try {
            Document doc = Jsoup.connect("https://www.mashina.kg/") //Document jsoup
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            Elements listCarsElements = doc.select("div.cars-container");
            //save information in file.txt (temporarily)
            FileWriter writer = new FileWriter("Cars.txt");

            for(Element element : listCarsElements.select("a")){
               listCars.add(element.text() + "\n");
               writer.write(element.text() + "\n");
               System.out.println(element.text());
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
