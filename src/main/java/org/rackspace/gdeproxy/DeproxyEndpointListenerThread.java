/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rackspace.gdeproxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;

/**
 *
 * @author richard-sartor
 */
public class DeproxyEndpointListenerThread extends Thread {

  DeproxyEndpoint _parent;
  ServerSocket _socket;
  Logger log = Logger.getLogger(DeproxyEndpointListenerThread.class.getName());

  public DeproxyEndpointListenerThread(DeproxyEndpoint parent, ServerSocket socket, String name) {
    super(name);

    _parent = parent;
    _socket = socket;
  }

  @Override
  public void run() {

    Integer connection = 0;
    while (!_socket.isClosed()) {
      try {

        Socket conn;
        try {
          conn = _socket.accept();
        } catch (SocketException e) {
          if (_socket.isClosed()) {
            break;
          } else {
            throw e;
          }
        }
        log.debug("Accepted a new connection");

//          conn.setSoTimeout(1000);
        log.debug("Creating the handler thread");
        DeproxyEndpointHandlerThread handlerThread = new DeproxyEndpointHandlerThread(_parent, conn, this.getName() + "-connection-" + connection.toString());
        log.debug("Starting the handler thread");
        handlerThread.start();
        log.debug("Handler thread started");
        connection++;

      } catch (IOException ex) {
        log.error(null, ex);
      }
    }
  }
}
