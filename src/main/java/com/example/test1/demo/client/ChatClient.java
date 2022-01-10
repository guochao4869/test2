package com.example.test1.demo.client;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

//客户端
public class ChatClient {

    //启动客户端方法
    public void startClient(String name, String meg) throws IOException {
        //连接服务端
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));

        //接收服务端响应数据
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ, meg);

        //创建线程
        new Thread(new com.example.test1.demo.client.ClientThread(selector)).start();

        //向服务器端发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            if(msg.length()>0) {
                socketChannel.write(Charset.forName("UTF-8").encode(name +" : " +msg));
            }
        }

       /* if (!StringUtils.isEmpty(meg)) {
            if (meg.length() > 0) {
                socketChannel.write(Charset.forName("UTF-8").encode(name +" : " + meg));
            }
        }*/

       /* try {
            lt(meg, socketChannel, name);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送错误");
        }
*/
    }

    public void lt(String meg, SocketChannel socketChannel, String name) throws Exception{
        if(meg.length()>0) {
            socketChannel.write(Charset.forName("UTF-8").encode(name +" : " +meg));
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        try {
            client.startClient("启动", "meg");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误");
        }
    }
}
