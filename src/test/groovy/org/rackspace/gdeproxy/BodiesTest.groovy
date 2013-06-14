/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class BodiesTest {

}


//class TestBodies(unittest.TestCase):
//    def setUp(self):
//        self.deproxy = deproxy.Deproxy()
//        self.port = get_next_deproxy_port()
//        self.deproxy.add_endpoint(self.port)
//        self.url = 'http://localhost:{0}/'.format(self.port)
//
//    def test_request_body(self):
//        body = """ This is the body
//
//        This is the next paragraph.
//        """
//        mc = self.deproxy.make_request(url=self.url, method='POST',
//                                       request_body=body)
//        self.assertEqual(mc.sent_request.body, body)
//        self.assertEqual(len(mc.handlings), 1)
//        self.assertEqual(mc.handlings[0].request.body, body)
//
//    def test_response_body(self):
//        body = """ This is another body
//
//        This is the next paragraph.
//        """
//
//        def custom_handler(request):
//            return deproxy.Response(code=200, message='OK', headers=None,
//                                    body=body)
//        mc = self.deproxy.make_request(url=self.url,
//                                       default_handler=custom_handler)
//        self.assertEqual(mc.received_response.body, body)
//        self.assertEqual(len(mc.handlings), 1)
//        self.assertEqual(mc.handlings[0].response.body, body)
//
//    @unittest.expectedFailure
//    def test_request_body_chunked(self):
//        self.fail()
//
//    @unittest.expectedFailure
//    def test_response_body_chunked(self):
//        self.fail()
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//