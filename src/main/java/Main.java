import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private String car;
    public static void main(String[] args) {
        new Main().go();
    }
    public void go(){
        parseMainPage();
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            while (true){
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(car);
                writer.close();
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
            Elements listCars = doc.select("div.cars-container");
            //save information in file.txt (temporarily)
            FileWriter writer = new FileWriter("Cars.txt");

            for(Element element : listCars.select("a")){
                writer.write(element.text() + "\n");
                car = element.text();
                System.out.println(element.text());
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
