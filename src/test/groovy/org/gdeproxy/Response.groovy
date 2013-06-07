package org.gdeproxy

import org.apache.http.Header

class Response {

    def code
    def message
    def Header[] headers
    def String body

    def getHeader(String name) {
        def String foundValue

        headers.each { header ->
            if (header.name.equals(name)) {
                foundValue = header.value
            }
        }
        return foundValue
    }

    @Override
    String toString() {
        sprintf('Response(code=%r, message=%r, headers=%r, body=%r)', code, message, headers, body)
    }
}

//class Response:
//    """A simple HTTP Response, with status code, status message, headers, and
//body."""
//    def __init__(self, code, message=None, headers=None, body=None):
//        """
//Parameters:
//
//code - A numerical status code. This doesn't have to be a valid HTTP
//status code; 600+ values are acceptable also.
//message - An optional message to go along with the status code. If
//None, a suitable default will be provided based on the given status
//.code If ``code`` is not a valid HTTP status code, then the default
//is the empty string.
//headers - An optional collection of name/value pairs, either a mapping
//object like ``dict``, or a HeaderCollection. Defaults to an empty
//collection.
//body - An optional response body. Defaults to the empty string.
//"""
//
//        if message is None:
//            if code in message_by_response_code:
//                message = message_by_response_code[code]
//            elif int(code) in message_by_response_code:
//                message = message_by_response_code[int(code)]
//            else:
//                message = ''
//
//        if headers is None:
//            headers = {}
//
//        if body is None:
//            body = ''
//
//        self.code = str(code)
//        self.message = str(message)
//        self.headers = HeaderCollection(headers)
//        self.body = str(body)
//
//    def __repr__(self):
//        return ('Response(code=%r, message=%r, headers=%r, body=%r)' %
//                (self.code, self.message, self.headers, self.body))