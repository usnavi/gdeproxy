/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class RouteHandlerTest {
	
}


//class TestRoute(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//        self.end_point = self.deproxy.add_endpoint(self.deproxy_port)
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//    def test_route(self):
//        handler = deproxy.route('http', 'httpbin.org', self.deproxy)
//        mc = self.deproxy.make_request('http://localhost:%i/' %
//                                       self.deproxy_port,
//                                       default_handler=handler)
//        self.assertEquals(int(mc.received_response.code), 200)
//
//