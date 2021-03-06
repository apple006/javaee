package com.wpx.netty.demo07.jbossmarshalling;

import com.wpx.netty.demo07.messagepack.ClientHandler;
import com.wpx.netty.demo07.messagepack.Person;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端处理类
 * @author wangpx
 */
public class Client {

public static void main(String[] args) throws InterruptedException {
	NioEventLoopGroup workGroup = new NioEventLoopGroup();
	try {
		 Bootstrap bootstrap = new Bootstrap();
		 bootstrap.group(workGroup)
		 		  .channel(NioSocketChannel.class)
		 		  .handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						
						ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
						ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
						
						ch.pipeline().addLast(new ClientHandler());
						
					}
				});
		 ChannelFuture future = bootstrap.connect("127.0.0.1", 8888).sync();
		 for(int i=0;i<10;i++) {
			 Person person = new Person(i, "name"+i, 5+i);
			 future.channel().writeAndFlush(person);
			 System.out.println(person);
		 }
		 future.channel().closeFuture().sync();
	} finally {
		workGroup.shutdownGracefully();
	}

}
}
