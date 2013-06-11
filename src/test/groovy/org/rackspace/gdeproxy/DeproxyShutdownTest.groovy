/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class DeproxyShutdownTest {
	
}


//class TestShutdownAllEndpoints(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port1 = get_next_deproxy_port()
//        self.deproxy_port2 = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//
//    def test_shutdown(self):
//        e1 = self.deproxy.add_endpoint(self.deproxy_port1)
//        e2 = self.deproxy.add_endpoint(self.deproxy_port2)
//
//        self.deproxy.shutdown_all_endpoints()
//
//        try:
//            e3 = self.deproxy.add_endpoint(self.deproxy_port1)
//        except socket.error as e:
//            self.fail('add_endpoint through an exception: %s' % e)
//
//        try:
//            e4 = self.deproxy.add_endpoint(self.deproxy_port2)
//        except socket.error as e:
//            self.fail('add_endpoint through an exception: %s' % e)
//
//