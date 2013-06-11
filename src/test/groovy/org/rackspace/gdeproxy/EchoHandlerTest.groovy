/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author richard-sartor
 */
class EchoHandlerTest {
	
}

//class TestEchoHandler(unittest.TestCase):
//    def setUp(self):
//        self.deproxy_port = get_next_deproxy_port()
//        self.deproxy = deproxy.Deproxy()
//        self.end_point = self.deproxy.add_endpoint(self.deproxy_port)
//
//    def tearDown(self):
//        self.deproxy.shutdown_all_endpoints()
//
//    def test_echo_handler(self):
//        headers = {'x-header': '12345'}
//        mc = self.deproxy.make_request('http://localhost:%i/' %
//                                       self.deproxy_port, headers=headers,
//                                       request_body='this is the body',
//                                       default_handler=deproxy.echo_handler)
//        self.assertEquals(int(mc.received_response.code), 200)
//        self.assertIn('x-header', mc.received_response.headers)
//        self.assertEquals(mc.received_response.headers['x-header'], '12345')
//        self.assertEquals(mc.received_response.body, 'this is the body')
//
//