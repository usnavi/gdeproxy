/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

import org.junit.*;
import static org.junit.Assert.*;


public class DefaultHandlerTest {
  
  int _port;
  Deproxy _deproxy;
  DeproxyEndpoint _endpoint;
  
  @Before
  public void setUp() {
    _deproxy = new Deproxy();

    def finder = new PortFinder()
    _port = finder.getNextOpenPort();

    _endpoint = _deproxy.addEndpoint(_port);
  }
  
  @Test
  public void TestDefaultHandler(){
    def mc = _deproxy.makeRequest(String.format("http://localhost:%d/", _port));
    assertEquals(mc.receivedResponse.code, "200");
  }
  
  @After
  public void tearDown(){
    _deproxy.shutdown()
  }
}