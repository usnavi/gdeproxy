/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class OrphanedHandlingsTest {
	
}


//class TestOrphanedHandlings(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//        self.end_point = self.deproxy.add_endpoint(self.deproxy_port)
//        self.other_client = deproxy.Deproxy()
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//    def test_orphaned_handling(self):
//        delayed_handler = deproxy.delay(2, deproxy.simple_handler)
//        self.long_running_mc = None
//
//        class Helper:
//            mc = None
//
//        helper = Helper()
//
//        def other_thread():
//            mc = self.deproxy.make_request('http://localhost:%i/' %
//                                           self.deproxy_port,
//                                           default_handler=delayed_handler)
//            helper.mc = mc
//
//        t = threading.Thread(target=other_thread)
//        t.daemon = True
//        t.start()
//        self.other_client.make_request('http://localhost:%i/' %
//                                       self.deproxy_port)
//        t.join()
//        self.assertEqual(len(helper.mc.orphaned_handlings), 1)
//
//