package com.quinn.app.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final ServerHandler SERVER_HANDLER = new ServerHandler();


    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Add the text line codec combination first,
//        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//        // the encoder and decoder are static as these are sharable
//        pipeline.addLast(DECODER);
//        pipeline.addLast(ENCODER);
//        // and then business logic.
//        pipeline.addLast(SERVER_HANDLER);
        //处理http服务的关键handler
        pipeline.addLast("encoder",new HttpResponseEncoder());
        pipeline.addLast("decoder",new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        pipeline.addLast("handler", new ServerHandler());// 服务端业务逻辑
    }
}