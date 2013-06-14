/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class SocketsTest {

}


//class TestSocketStuff(unittest.TestCase):
//    def test_it(self):
//        listener = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
//        listener.bind(('localhost', 8888))
//        listener.listen(5)
//        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
//        def accept_thread():
//            self.server = listener.accept()[0]
//        threading.Thread(target=accept_thread).start()
//        client.connect(('localhost', 8888))
//        server = self.server
//        client.send('asdf')
//        asdf = server.recv(1024)
//        self.assertEqual(asdf, 'asdf')
//