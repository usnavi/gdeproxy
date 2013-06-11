/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class CustomHandlersTest {
	
}


//class TestCustomHandlers(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//        self.end_point = self.deproxy.add_endpoint(self.deproxy_port)
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//    def test_custom_handler_function(self):
//        def custom_handler(request):
//            return deproxy.Response(code=606, message="Spoiler",
//                                    headers={"Header-Name": "Header-Value"},
//                                    body='Snape Kills Dumbledore')
//        mc = self.deproxy.make_request('http://localhost:%i/' %
//                                       self.deproxy_port,
//                                       default_handler=custom_handler)
//        self.assertEquals(int(mc.received_response.code), 606)
//
//    def handler_method(self, request):
//        return deproxy.Response(code=606, message="Spoiler",
//                                headers={"Header-Name": "Header-Value"},
//                                body='Snape Kills Dumbledore')
//
//    def test_custom_handler_method(self):
//        mc = self.deproxy.make_request('http://localhost:%i/' %
//                                       self.deproxy_port,
//                                       default_handler=self.handler_method)
//        self.assertEquals(int(mc.received_response.code), 606)
//
//