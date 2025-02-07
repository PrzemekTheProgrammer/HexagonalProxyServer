package pszerszenowicz.HexagonalProxyServer.domain;

import io.netty.channel.Channel;

public class Message {
    private String message;
    private Channel channel;
    private MessageType messageType;

    public Message(String message, Channel channel, MessageType messageType) {
        this.message = message;
        this.channel = channel;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
