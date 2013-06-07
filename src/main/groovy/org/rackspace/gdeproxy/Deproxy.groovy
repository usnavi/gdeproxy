package org.rackspace.gdeproxy

/**
 * The main class.
 */
class Deproxy {

    String REQUEST_ID_HEADER_NAME = 'Deproxy-Request-ID'
    def messageChainsLock = threading.Lock()
    def messageChains = dict()
    def endpointLock = threading.Lock()
    def DeproxyEndpoint[] endpoints = []


    Deproxy() {

    }

}

//class Deproxy:
//    """The main class."""
//
//    def __init__(self, default_handler=None):
//        """
//Params:
//default_handler - An optional handler function to use for requests, if
//not specified elsewhere
//"""
//        self._message_chains_lock = threading.Lock()
//        self._message_chains = dict()
//        self._endpoint_lock = threading.Lock()
//        self._endpoints = []
//        self.default_handler = default_handler
//
//    def make_request(self, url, method='GET', headers=None, request_body='',
//                     default_handler=None, handlers=None,
//                     add_default_headers=True):
//        """
//Make an HTTP request to the given url and return a MessageChain.
//
//Parameters:
//
//url - The URL to send the client request to
//method - The HTTP method to use, default is 'GET'
//headers - A collection of request headers to send, defaults to None
//request_body - The body of the request, as a string, defaults to empty
//string
//default_handler - An optional handler function to use for requests
//related to this client request
//handlers - A mapping object that maps endpoint references or names of
//endpoints to handlers. If an endpoint or its name is a key within
//``handlers``, all requests to that endpoint will be handled by the
//associated handler
//add_default_headers - If true, the 'Host', 'Accept', 'Accept-Encoding',
//and 'User-Agent' headers will be added to the list of headers sent,
//if not already specified in the ``headers`` parameter above.
//Otherwise, those headers are not added. Defaults to True.
//"""
//        logger.debug('')
//
//        if headers is None:
//            headers = HeaderCollection()
//        else:
//            headers = HeaderCollection(headers)
//
//        request_id = str(uuid.uuid4())
//        if request_id_header_name not in headers:
//            headers.add(request_id_header_name, request_id)
//
//        message_chain = MessageChain(default_handler=default_handler,
//                                     handlers=handlers)
//        self.add_message_chain(request_id, message_chain)
//
//        urlparts = list(urlparse.urlsplit(url, 'http'))
//        scheme = urlparts[0]
//        host = urlparts[1]
//        urlparts[0] = ''
//        urlparts[1] = ''
//        path = urlparse.urlunsplit(urlparts)
//
//        logger.debug('request_body: "{0}"'.format(request_body))
//        if len(request_body) > 0:
//            headers.add('Content-Length', len(request_body))
//
//        if add_default_headers:
//            if 'Host' not in headers:
//                headers.add('Host', host)
//            if 'Accept' not in headers:
//                headers.add('Accept', '*/*')
//            if 'Accept-Encoding' not in headers:
//                headers.add('Accept-Encoding',
//                            'identity, deflate, compress, gzip')
//            if 'User-Agent' not in headers:
//                headers.add('User-Agent', version_string)
//
//        request = Request(method, path, headers, request_body)
//
//        response = self.send_request(scheme, host, request)
//
//        self.remove_message_chain(request_id)
//
//        message_chain.sent_request = request
//        message_chain.received_response = response
//
//        return message_chain
//
//    def create_ssl_connection(self, address,
//                              timeout=socket._GLOBAL_DEFAULT_TIMEOUT,
//                              source_address=None):
//        """
//Copied from the socket module and modified for ssl support.
//
//Connect to *address* and return the socket object.
//
//Convenience function. Connect to *address* (a 2-tuple ``(host,
//port)``) and return the socket object. Passing the optional
//*timeout* parameter will set the timeout on the socket instance
//before attempting to connect. If no *timeout* is supplied, the
//global default timeout setting returned by :func:`getdefaulttimeout`
//is used. If *source_address* is set it must be a tuple of (host, port)
//for the socket to bind as a source address before making the
//connection. A host of '' or port 0 tells the OS to use the default.
//"""
//
//        host, port = address
//        err = None
//        for res in socket.getaddrinfo(host, port, 0, socket.SOCK_STREAM):
//            af, socktype, proto, canonname, sa = res
//            sock = None
//            try:
//                sock = socket.socket(af, socktype, proto)
//
//                sock = ssl.wrap_socket(sock)
//
//                if timeout is not socket._GLOBAL_DEFAULT_TIMEOUT:
//                    sock.settimeout(timeout)
//                if source_address:
//                    sock.bind(source_address)
//                sock.connect(sa)
//                return sock
//
//            except socket.error as _:
//                err = _
//                if sock is not None:
//                    sock.close()
//
//        if err is not None:
//            raise err
//        else:
//            raise error("getaddrinfo returns an empty list")
//
//    def send_request(self, scheme, host, request):
//        """Send the given request to the host and return the Response."""
//        logger.debug('sending request (scheme="%s", host="%s")' %
//                     (scheme, host))
//        hostparts = host.split(':')
//        if len(hostparts) > 1:
//            port = hostparts[1]
//        else:
//            if scheme == 'https':
//                port = 443
//            else:
//                port = 80
//        hostname = hostparts[0]
//        hostip = socket.gethostbyname(hostname)
//
//        request_line = '%s %s HTTP/1.1\r\n' % (request.method, request.path)
//        lines = [request_line]
//
//        for name, value in request.headers.iteritems():
//            lines.append('%s: %s\r\n' % (name, value))
//        lines.append('\r\n')
//        if request.body is not None and len(request.body) > 0:
//            lines.append(request.body)
//
//        #for line in lines:
//        # logger.debug(' ' + line)
//
//        logger.debug('Creating connection (hostname="%s", port="%s")' %
//                     (hostname, str(port)))
//
//        address = (hostname, port)
//        if scheme == 'https':
//            s = self.create_ssl_connection(address)
//        else:
//            s = socket.create_connection(address)
//
//        s.send(''.join(lines))
//
//        rfile = s.makefile('rb', -1)
//
//        logger.debug('Reading response line')
//        response_line = rfile.readline(65537)
//        if (len(response_line) > 65536):
//            raise ValueError
//        response_line = response_line.rstrip('\r\n')
//        logger.debug('Response line is ok: %s' % response_line)
//
//        words = response_line.split()
//
//        proto = words[0]
//        code = words[1]
//        message = ' '.join(words[2:])
//
//        logger.debug('Reading headers')
//        response_headers = HeaderCollection.from_stream(rfile)
//        logger.debug('Headers ok')
//        for k,v in response_headers.iteritems():
//            logger.debug(' %s: %s', k, v)
//
//        logger.debug('Reading body')
//        body = read_body_from_stream(rfile, response_headers)
//
//        logger.debug('Creating Response object')
//        response = Response(code, message, response_headers, body)
//
//        logger.debug('Returning Response object')
//        return response
//
//    def add_endpoint(self, port, name=None, hostname=None,
//                     default_handler=None):
//        """Add a DeproxyEndpoint object to this Deproxy object's list of
//endpoints, giving it the specified server address, and then return the
//endpoint.
//
//Params:
//port - The port on which the new endpoint will listen
//name - An optional descriptive name for the new endpoint. If None, a
//suitable default will be generated
//hostname - The ``hostname`` portion of the address tuple passed to
//``socket.bind``. If not specified, it defaults to 'localhost'
//default_handler - An optional handler function to use for requests that
//the new endpoint will handle, if not specified elsewhere
//"""
//
//        logger.debug('')
//        endpoint = None
//        with self._endpoint_lock:
//            if name is None:
//                name = 'Endpoint-%i' % len(self._endpoints)
//            endpoint = DeproxyEndpoint(self, port=port, name=name,
//                                       hostname=hostname,
//                                       default_handler=default_handler)
//            self._endpoints.append(endpoint)
//            return endpoint
//
//    def _remove_endpoint(self, endpoint):
//        """Remove a DeproxyEndpoint from the list of endpoints. Returns True if
//the endpoint was removed, or False if the endpoint was not in the list.
//This method should normally not be called by user code. Instead, call
//the endpoint's shutdown method."""
//        logger.debug('')
//        with self._endpoint_lock:
//            count = len(self._endpoints)
//            self._endpoints = [e for e in self._endpoints if e != endpoint]
//            return (count != len(self._endpoints))
//
//    def shutdown_all_endpoints(self):
//        """Shutdown and remove all endpoints in use."""
//        logger.debug('')
//        endpoints = []
//        with self._endpoint_lock:
//            endpoints = list(self._endpoints)
//        # be sure we're not holding the lock when shutdown calls
//        # _remove_endpoint.
//        for e in endpoints:
//            e.shutdown()
//
//    def add_message_chain(self, request_id, message_chain):
//        """Add a MessageChain to the internal list for the given request ID."""
//        logger.debug('request_id = %s' % request_id)
//        with self._message_chains_lock:
//            self._message_chains[request_id] = message_chain
//
//    def remove_message_chain(self, request_id):
//        """Remove a particular MessageChain from the internal list."""
//        logger.debug('request_id = %s' % request_id)
//        with self._message_chains_lock:
//            del self._message_chains[request_id]
//
//    def get_message_chain(self, request_id):
//        """Return the MessageChain for the given request ID."""
//        logger.debug('request_id = %s' % request_id)
//        with self._message_chains_lock:
//            if request_id in self._message_chains:
//                return self._message_chains[request_id]
//            else:
//                #logger.debug('no message chain found for request_id %s' %
//                # request_id)
//                #for rid, mc in self._message_chains.iteritems():
//                # logger.debug(' %s - %s' % (rid, mc))
//                return None
//
//    def add_orphaned_handling(self, handling):
//        """Add the handling to all available MessageChains."""
//        logger.debug('Adding orphaned handling')
//        with self._message_chains_lock:
//            for mc in self._message_chains.itervalues():
//                mc.add_orphaned_handling(handling)




//
//def read_body_from_stream(stream, headers):
//    if ('Transfer-Encoding' in headers and
//            headers['Transfer-Encoding'] != 'identity'):
//        # 2
//        logger.debug('NotImplementedError - Transfer-Encoding != identity')
//        raise NotImplementedError
//    elif 'Content-Length' in headers:
//        # 3
//        length = int(headers['Content-Length'])
//        body = stream.read(length)
//    elif False:
//        # multipart/byteranges ?
//        logger.debug('NotImplementedError - multipart/byteranges')
//        raise NotImplementedError
//    else:
//        # there is no body
//        body = None
//    return body