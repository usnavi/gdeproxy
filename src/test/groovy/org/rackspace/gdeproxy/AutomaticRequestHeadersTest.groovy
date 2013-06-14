/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */

import org.junit.*;
import static org.junit.Assert.*;

class AutomaticRequestHeadersTest {

  def _port
  def _deproxy
  def _endpoint
  def _url

  @Before
  def setUp() {
//    def setUp(self):
//        self.port = get_next_deproxy_port()
PortFinder pf = new PortFinder()
_port = pf.getNextOpenPort()
//        self.deproxy = deproxy.Deproxy()
_deproxy = new Deproxy()
//        self.endpoint = self.deproxy.add_endpoint(self.port)
_endpoint = _deproxy.addEndpoint(_port)
//        self.url = 'http://localhost:{}/'.format(self.port)
_url = String.format("http://localhost:%d/", _port)
//
  }
//    def tearDown(self):
//        if self.deproxy is not None:
//            self.deproxy.shutdown_all_endpoints()
//
//    def test_not_specified(self):
//        mc = self.deproxy.make_request(url=self.url)
//        self.assertIn('Host', mc.sent_request.headers)
//        #self.assertIn('host', mc.sent_request.headers)
//        self.assertIn('Accept', mc.sent_request.headers)
//        self.assertIn('Accept-Encoding', mc.sent_request.headers)
//        self.assertIn('User-Agent', mc.sent_request.headers)
//
//    def test_explicit_on(self):
//        mc = self.deproxy.make_request(url=self.url, add_default_headers=True)
//        self.assertIn('Host', mc.sent_request.headers)
//        #self.assertIn('host', mc.sent_request.headers)
//        self.assertIn('Accept', mc.sent_request.headers)
//        self.assertIn('Accept-Encoding', mc.sent_request.headers)
//        self.assertIn('User-Agent', mc.sent_request.headers)
//
//    def test_explicit_off(self):
//        mc = self.deproxy.make_request(url=self.url, add_default_headers=False)
//        self.assertNotIn('Host', mc.sent_request.headers)
//        #self.assertNotIn('host', mc.sent_request.headers)
//        self.assertNotIn('Accept', mc.sent_request.headers)
//        self.assertNotIn('Accept-Encoding', mc.sent_request.headers)
//        self.assertNotIn('User-Agent', mc.sent_request.headers)
//
//
}