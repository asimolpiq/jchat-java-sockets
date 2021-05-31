
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("jChat uygulamasına hoş geldiniz!");
        Socket cSocket = new Socket("127.0.0.1", 1999); //bağlanacağı sunucu socketinin ip adresini ve portunu giriyoruz | csocket = clientSocket
        System.out.println("Sunucuya bağlanıldı...");//eğer yukarıdaki kodumuz hata vermezse bu kod bağlandığımızı ekrana bastırıyor
        
        PrintWriter name = new PrintWriter(cSocket.getOutputStream()); //isim yollamak için yeni bir yazıcı oluşturuyoruz ve adına name diyoruz
        System.out.print("Adınızı giriniz: ");
        String gname = scanner.nextLine();
        name.println(gname);//name isimli yazıcımızla bunu server'a print etmesini söylüyoruz
        name.flush();//print işlemimizi server'a yolluyoruz
        
        PrintWriter msgSender = new PrintWriter(cSocket.getOutputStream()); // mesaj yollayıcı oluşturuyoruz
        Thread.sleep(1000);

        while (true) {

            System.out.print(gname + ": ");
            String msg = scanner.nextLine();//ismimizle beraber mesajımızı alıyor
            
            if (msg.equals("quit")) { //eğer çıkmak istersek close() fonksiyonu server'a null bir mesaj yolluyor
                cSocket.close();
                System.out.println("Programdan çıkılıyor...");
                Thread.sleep(1000);
                break;
            } 
               else{ 
                msgSender.println(msg);//msgSender referansımız sayesinde msg Stringimizi arabelleğe print ediyoruz
                msgSender.flush();//print işlemimizi arabellekten çıkartıp server'a yolluyoruz ve mesajımız ulaşıyor
                
                InputStreamReader in = new InputStreamReader(cSocket.getInputStream()); //bir okuyucu oluşturuyoruz
                BufferedReader msgReader = new BufferedReader(in); // in isimli referansımız gelen veriyi yakalıyor lakin bit tipinden bu yüzden BufferedReader bu biti String satırlarına çeviriyor
                String gMesaj = msgReader.readLine(); //ve mesaj satırını gMesaj(gelen mesaj) değişkenimize atıyoruz
                
                if (gMesaj == null) {//eğer karşı taraf çıkarsa null değer döneceği için bunu yakalayıp programı sonlandırıyoruz
                System.out.println("Server ayrıldı...");
                Thread.sleep(1000);
                System.out.println("programdan çıkılıyor...");
                cSocket.close();
                break;
            }
                
                System.out.println("Server: " + gMesaj);//eğer null dönmezse mesajı ekrana bastırıyoruz.

            
        }
        }
    }
}
