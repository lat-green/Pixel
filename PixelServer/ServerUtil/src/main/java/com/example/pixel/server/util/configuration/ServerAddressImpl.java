package com.example.pixel.server.util.configuration;

import lombok.val;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Component
public class ServerAddressImpl implements ServerAddress {

    private final String address;

    public ServerAddressImpl() throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            this.address = socket.getLocalAddress().getHostAddress();
        }
    }

    @Override
    public String getAddress() {
        return address;
    }

}
