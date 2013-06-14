/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class DelayHandlerTest {

}


//class TestDelayHandler(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//        self.end_point = self.deproxy.add_endpoint(self.deproxy_port)
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//    def test_delay_handler(self):
//        handler = deproxy.delay(3, deproxy.simple_handler)
//        t1 = time.time()
//        mc = self.deproxy.make_request('http://localhost:%i/' %
//                                       self.deproxy_port,
//                                       default_handler=handler)
//        t2 = time.time()
//        self.assertEquals(int(mc.received_response.code), 200)
//        self.assertGreaterEqual(t2 - t1, 3)
//        self.assertLessEqual(t2 - t1, 3.5)
//
//