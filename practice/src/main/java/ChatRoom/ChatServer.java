package ChatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天室的服务器端
 */
public class ChatServer {
    public ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT = 10086;

    public ChatServer(){
        try{
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listening(){
        try {
            while (true) {
                int num = selector.select(3000);
                if(num > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){ // 处理客户端的连接请求
                            // 连接到服务器的客户端
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "连接成功");
                        }
                        if(key.isReadable()){// 处理客户端的通信请求


                        }
                        iterator.remove();
                    }
                }else{
                    System.out.println("server: waiting......");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handleReadData(SelectionKey key){
        SocketChannel socketChannel=null;
        try {
            //服务器接收来自客户端的消息
            socketChannel= (SocketChannel) key.channel();
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            int len=socketChannel.read(buffer);
            if(len>0){
                String msg=new String(buffer.array());
                System.out.println("来自客户端的消息是:"+msg);
                //转发消息给其他的客户端
                transferMessage(msg,socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离开了聊天室!");
                key.cancel();//取消注册
                socketChannel.close();//关闭通道
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
                //e.printStackTrace();
        }
    }

    //服务器端转发消息
    public void transferMessage(String msg,SocketChannel socketChannel) throws IOException {
        System.out.println("server转发消息ing.......");
        for (SelectionKey selectionKey : selector.keys()) {
            SelectableChannel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && channel != socketChannel) {
                SocketChannel client= (SocketChannel) channel;
                ByteBuffer buffer1=ByteBuffer.wrap(msg.getBytes());
                client.write(buffer1);
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer=new ChatServer();
        chatServer.listening();
    }


}
