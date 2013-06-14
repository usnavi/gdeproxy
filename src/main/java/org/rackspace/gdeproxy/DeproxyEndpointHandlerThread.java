/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rackspace.gdeproxy;

import java.net.Socket;
import org.apache.log4j.Logger;

/**
 *
 * @author richard-sartor
 */
public class DeproxyEndpointHandlerThread extends Thread {

  Logger log = Logger.getLogger(DeproxyEndpointHandlerThread.class.getName());

  DeproxyEndpoint _parent;
  Socket _socket;


  public DeproxyEndpointHandlerThread(DeproxyEndpoint parent, Socket socket, String name) {
    super(name);

    _parent = parent;
    _socket = socket;
  }

  @Override
  public void run() {
    log.debug("Processing new connection");
    _parent.processNewConnection(_socket);
    log.debug("Connection processed");
  }
}
