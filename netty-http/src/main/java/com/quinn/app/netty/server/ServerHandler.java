package com.quinn.app.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.util.Date;


@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 为新连接发送庆祝
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    /**
     * 发送的返回值
     * @param ctx	  返回
     * @param context 消息
     * @param status 状态
     */
    private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object request) throws Exception {
        if(! (request instanceof FullHttpRequest)){
            String result="未知请求!";
            send(ctx,result, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        FullHttpRequest httpRequest = (FullHttpRequest)request;
        try {
            // Generate and write a response.
            String response;
            boolean close = false;
            if (null==request) {
                response = "Please type something.\r\n";
            } else if ("bye".equals(request.toString().toLowerCase())) {
                response = "Have a good day!\r\n";
                close = true;
            } else if(httpRequest.method()!=null){
                String result=httpRequest.method()+"请求";
                send(ctx,result,HttpResponseStatus.OK);
                return;
            } else {
                response = "Did you say '" + request + "'?\r\n";
            }

            ChannelFuture future = ctx.write(response);

            if (close) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }finally {
            httpRequest.release();
        }



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
