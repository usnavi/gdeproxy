/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rackspace.gdeproxy;

/**
 *
 * @author richard-sartor
 */
public class PortFinder {

  def _basePort = 9999
  def _currentPort = null
  
  def getNextOpenPort(start=null) {

    if (start != null) {
      _currentPort = start
    } else if (_currentPort == null) {
      _currentPort = _basePort
    }
  
    while (_currentPort < 65536) {
      try {
        def url = String.format("http://localhost:%d/", _currentPort)
        println "Trying " + url
        url.toURL().getText()
      } catch (java.net.ConnectException e) {
        println "Didn't connect, using this one"
        _currentPort++
        return _currentPort - 1
      } catch (Exception e) {
        throw e
      }

       Thread.sleep(1000)
      println "Connected"
      
      _currentPort++
    }
    
    throw new RuntimeException("Ran out of ports")
  }
}
