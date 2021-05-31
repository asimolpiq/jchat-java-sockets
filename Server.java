
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("jChat Server uygulamasına hoş geldiniz!");
        Thread.sleep(1000);
        ServerSocket sSocket = new ServerSocket(1999); //yeni bir server socket oluşturuyoruz
        System.out.println("Sunucu 127.0.0.1 ip adresinde 4444 portunda oluşturuldu!");
        Thread.sleep(1000);
        System.out.println("Bağlantı bekleniyor...");
        Socket client = sSocket.accept();
        
        InputStreamReader nameRead = new InputStreamReader(client.getInputStream());//client'a bağlanan kişinin adını almak için yeni bir okuyucu oluşturuyoruz
        BufferedReader nameReader = new BufferedReader(nameRead);
        String name = nameReader.readLine();
        Thread.sleep(2000);
        System.out.println(name + " isimli kullanıcı bağlandı...");
        
        Thread.sleep(1000);

        while (true) {
            
                
            
            InputStreamReader in = new InputStreamReader(client.getInputStream()); //bir okuyucu oluşturuyoruz
            BufferedReader msgReader = new BufferedReader(in);// in isimli referansımız gelen veriyi yakalıyor lakin bit tipinden bu yüzden BufferedReader bu biti String satırlarına çeviriyor
            String gMesaj = msgReader.readLine();//ve mesaj satırını gMesaj(gelen mesaj) değişkenimize atıyoruz
            
            if (gMesaj == null) {//eğer karşı taraf çıkarsa null değer döneceği için bunu yakalayıp programı sonlandırıyoruz
                System.out.println(name+" ayrıldı...");
                Thread.sleep(1000);
                System.out.println("programdan çıkılıyor...");
                sSocket.close();
                break;
            }
            
            System.out.println(name + ": " + gMesaj);//eğer null dönmezse mesajı ekrana bastırıyoruz.
            
            PrintWriter msgSender = new PrintWriter(client.getOutputStream()); // mesaj yollayıcı oluşturuyoruz
            System.out.print("Server: ");
            String msg = scanner.nextLine();
            
            if (msg.equals("quit")) { //eğer çıkmak istersek close() fonksiyonu server'a null bir mesaj yolluyor
                sSocket.close();
                System.out.println("Programdan çıkılıyor...");
                Thread.sleep(1000);
                break;
            } 
            
            msgSender.println(msg);//msgSender referansımız sayesinde msg Stringimizi arabelleğe print ediyoruz
            msgSender.flush();//print işlemimizi arabellekten çıkartıp server'a yolluyoruz ve mesajımız ulaşıyor
            }
        }

    

}
