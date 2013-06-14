/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author richard-sartor
 */
class HeaderCollectionTest {

}


//class TestHeaderCollection(unittest.TestCase):
//    def setUp(self):
//        self.headers = deproxy.HeaderCollection()
//
//    def test_length(self):
//        self.assertEqual(len(self.headers), 0)
//        self.headers.add('Name', 'Value')
//        self.assertEqual(len(self.headers), 1)
//
//    def test_contains(self):
//        self.headers.add('Name', 'Value')
//        self.assertTrue('Name' in self.headers)
//
//    def test_contains_case(self):
//        self.headers.add('Name', 'Value')
//        self.assertTrue('name' in self.headers)
//
//    def test_assertIn_case(self):
//        self.headers.add('Name', 'Value')
//        self.assertIn('name', self.headers)
//
//    def test_find_all(self):
//        self.headers.add('A', 'qwerty')
//        self.headers.add('B', 'asdf')
//        self.headers.add('C', 'zxcv')
//        self.headers.add('A', 'uiop')
//        self.headers.add('A', 'jkl;')
//
//        result = [value for value in self.headers.find_all('A')]
//        self.assertEqual(result, ['qwerty', 'uiop', 'jkl;'])
//
//    def test_bracket_case(self):
//        self.headers.add('Name', 'Value')
//
//        try:
//            self.assertEqual(self.headers['name'], 'Value')
//        except:
//            self.fail()
//
//    def test_get(self):
//        self.headers.add('Name', 'Value')
//        self.assertIn('name', self.headers)
//
//        self.assertEqual(self.headers.get('Name'), 'Value')
//        self.assertEqual(self.headers.get('name'), 'Value')
//        self.assertIsNone(self.headers.get('asdf'))
//        self.assertEqual(self.headers.get('name', default='zxcv'), 'Value')
//        self.assertEqual(self.headers.get('asdf', default='zxcv'), 'zxcv')
//
//