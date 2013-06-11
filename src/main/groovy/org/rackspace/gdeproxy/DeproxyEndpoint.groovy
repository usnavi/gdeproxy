package org.rackspace.gdeproxy

import groovy.util.logging.Log
import org.apache.log4j.Level

/**
 * A class that acts as a mock HTTP server.
 //    # The default request version. This only affects responses up until
 //    # the point where the request line is parsed, so it mainly decides what
 //    # the client gets back when sending a malformed request line.
 //    # Most web servers default to HTTP 0.9, i.e. don't send a status line.
 //    default_request_version = "HTTP/0.9"
 //
 //    # The version of the HTTP protocol we support.
 //    # Set this to HTTP/1.1 to enable automatic keepalive
 //    protocol_version = "HTTP/1.1"
 //
 //    # Disable nagle algoritm for this socket, if True.
 //    # Use only when wbufsize != 0, to avoid small packets.
 //    disable_nagle_algorithm = False
 */
@Log
class DeproxyEndpoint {

    def port
    GDeproxy deproxy
    def name
    int request_queue_size = 5
    int donn_number = 1
    def defaultHandler
    def hostname

    Thread serverThread

    //Initialize a DeproxyEndpoint
    //
    //Params:
    //deproxy - The parent Deproxy object that contains this endpoint
    //port - The port on which this endpoint will listen
    //name - A descriptive name for this endpoint
    //hostname - The ``hostname`` portion of the address tuple passed to
    //``socket.bind``. If not specified, it defaults to 'localhost'
    //default_handler - An optional handler function to use for requests that
    //this endpoint services, if not specified elsewhere
    //"""
    //
    DeproxyEndpoint(deproxy, port, name, hostname, defaultHandler) {
        this.deproxy = deproxy
        this.port = port
        this.name = name

        if (hostname) {
            this.hostname = hostname
        }

        if (defaultHandler) {
            this.defaultHandler = defaultHandler
        }

//        logger.debug('port=%s, name=%s, hostname=%s', port, name, hostname)

        ServerSocket serverSocket = new ServerSocket(port)
        serverSocket.setReuseAddress(true)
        serverSocket.setSoTimeout()

        String threadName = "Thread-${name}"

        def serverThread = new Thread()
        serverThread.setName(threadName)
        serverThread.setDaemon(true)

        serverThread.start {
            while (!Thread.currentThread().isInterrupted()) {
                serverSocket.accept({ socket ->
                    println "processing new connection..."
                    socket.withStreams { input, output ->
                        def reader = input.newReader()
                        def buffer = reader.readLine()
                        println "server received: $buffer"
                        output << buffer + "\n"
                    }
                    println "processing/thread complete."
                })
            }
        }
    }

    //Stops the serve_forever loop.
    //Blocks until the loop has finished. This must be called while
    //serve_forever() is running in another thread, or it will
    //deadlock.
    def shutdown() {
        log.log(Level.DEBUG, "Shutting down ${name}")
        serverThread.interrupt()
        log.log(Level.DEBUG, "Finished shutting down ${name}")
    }


}

//
//    def process_new_connection(self, request, client_address):
//        logger.debug('received request from %s' % str(client_address))
//        try:
//            connection = request
//            if self.disable_nagle_algorithm:
//                connection.setsockopt(socket.IPPROTO_TCP,
//                                      socket.TCP_NODELAY, True)
//            rfile = connection.makefile('rb', -1)
//            wfile = connection.makefile('wb', 0)
//
//            try:
//                close = self.handle_one_request(rfile, wfile)
//                while not close:
//                    close = self.handle_one_request(rfile, wfile)
//            finally:
//                if not wfile.closed:
//                    wfile.flush()
//                wfile.close()
//                rfile.close()
//        except:
//            self.handle_error(request, client_address)
//        finally:
//            self.shutdown_request(request)
//
//    def shutdown_request(self, request):
//        """Called to shutdown and close an individual request."""
//        logger.debug('')
//        try:
//            #explicitly shutdown. socket.close() merely releases
//            #the socket and waits for GC to perform the actual close.
//            request.shutdown(socket.SHUT_WR)
//        except socket.error:
//            pass # some platforms may raise ENOTCONN here
//        request.close()
//
//    def serve_forever(self, poll_interval=0.5):
//        """Handle one request at a time until shutdown.
//
//Polls for shutdown every poll_interval seconds. Ignores
//self.timeout. If you need to do periodic tasks, do them in
//another thread.
//"""
//        logger.debug('')
//        self.__is_shut_down.clear()
//        try:
//            while not self.__shutdown_request:
//                # XXX: Consider using another file descriptor or
//                # connecting to the socket to wake this up instead of
//                # polling. Polling reduces our responsiveness to a
//                # shutdown request and wastes cpu at all other times.
//                r, w, e = select.select([self.socket], [], [], poll_interval)
//                if self.socket in r:
//                    try:
//                        request, client_address = self.socket.accept()
//                    except socket.error:
//                        return
//
//                    try:
//                        with self._conn_number_lock:
//                            t = threading.Thread(
//                                target=self.process_new_connection,
//                                name=("Thread - Connection %i on %s" %
//                                      (self._conn_number, self.name)),
//                                args=(request, client_address))
//                            self._conn_number += 1
//                        t.daemon = True
//                        t.start()
//
//                    except:
//                        self.handle_error(request, client_address)
//                        self.shutdown_request(request)
//
//        finally:
//            self.socket.close()
//            self.__shutdown_request = False
//            self.__is_shut_down.set()
//
//    def handle_error(self, request, client_address):
//        """Handle an error gracefully. May be overridden.
//
//The default is to print a traceback and continue.
//
//"""
//        logger.debug('')
//        print '-' * 40
//        print 'Exception happened during processing of request from',
//        print client_address
//        import traceback
//        traceback.print_exc() # XXX But this goes to stderr!
//        print '-' * 40
//
//    def handle_one_request(self, rfile, wfile):
//        logger.debug('')
//        close_connection = True
//        try:
//            logger.debug('calling parse_request')
//            ret = self.parse_request(rfile, wfile)
//            logger.debug('returned from parse_request')
//            if not ret:
//                return 1
//
//            (incoming_request, persistent_connection) = ret
//
//            if persistent_connection:
//                close_connection = False
//                conn_value = incoming_request.headers.get('connection')
//                if conn_value:
//                    if conn_value.lower() == 'close':
//                        close_connection = True
//            else:
//                close_connection = True
//            close_connection = True
//
//            message_chain = None
//            request_id = incoming_request.headers.get(request_id_header_name)
//            if request_id:
//                logger.debug('The request has a request id: %s=%s' %
//                             (request_id_header_name, request_id))
//                message_chain = self.deproxy.get_message_chain(request_id)
//            else:
//                logger.debug('The request does not have a request id')
//
//            # Handler resolution:
//            # 1. Check the handlers mapping specified to ``make_request``
//            # a. By reference
//            # b. By name
//            # 2. Check the default_handler specified to ``make_request``
//            # 3. Check the default for this endpoint
//            # 4. Check the default for the parent Deproxy
//            # 5. Fallback to simple_handler
//            if (message_chain and message_chain.handlers is not None and
//                    self in message_chain.handlers):
//                handler = message_chain.handlers[self]
//            elif (message_chain and message_chain.handlers is not None and
//                  self.name in message_chain.handlers):
//                handler = message_chain.handlers[self.name]
//            elif message_chain and message_chain.default_handler is not None:
//                handler = message_chain.default_handler
//            elif self.default_handler is not None:
//                handler = self.default_handler
//            elif self.deproxy.default_handler is not None:
//                handler = self.deproxy.default_handler
//            else:
//                # last resort
//                handler = simple_handler
//
//            logger.debug('calling handler')
//            resp = handler(incoming_request)
//            logger.debug('returned from handler')
//
//            add_default_headers = True
//            if type(resp) == tuple:
//                logger.debug('Handler gave back a tuple: %s',
//                             (type(resp[0]), resp[1:]))
//                if len(resp) > 1:
//                    add_default_headers = resp[1]
//                resp = resp[0]
//
//            if (resp.body is not None and
//                    'Content-Length' not in resp.headers):
//                resp.headers.add('Content-Length', len(resp.body))
//
//            if add_default_headers:
//                if 'Server' not in resp.headers:
//                    resp.headers['Server'] = version_string
//                if 'Date' not in resp.headers:
//                    resp.headers['Date'] = self.date_time_string()
//            else:
//                logger.debug('Don\'t add default response headers.')
//
//            found = resp.headers.get(request_id_header_name)
//            if not found and request_id is not None:
//                resp.headers[request_id_header_name] = request_id
//
//            outgoing_response = resp
//
//            h = Handling(self, incoming_request, outgoing_response)
//            if message_chain:
//                message_chain.add_handling(h)
//            else:
//                self.deproxy.add_orphaned_handling(h)
//
//            self.send_response(wfile, resp)
//
//            wfile.flush()
//
//            if persistent_connection and not close_connection:
//                conn_value = incoming_request.headers.get('connection')
//                if conn_value:
//                    if conn_value.lower() == 'close':
//                        close_connection = True
//
//        except socket.timeout, e:
//            close_connection = True
//
//        return close_connection
//
//    def parse_request(self, rfile, wfile):
//        logger.debug('reading request line')
//        request_line = rfile.readline(65537)
//        if len(request_line) > 65536:
//            self.send_error(wfile, 414, None, self.default_request_version)
//            return ()
//        if not request_line:
//            return ()
//
//        request_line = request_line.rstrip('\r\n')
//        logger.debug('request line is ok: "%s"' % request_line)
//
//        if request_line[-2:] == '\r\n':
//            request_line = request_line[:-2]
//        elif request_line[-1:] == '\n':
//            request_line = request_line[:-1]
//        words = request_line.split()
//        if len(words) == 3:
//            [method, path, version] = words
//            if version[:5] != 'HTTP/':
//                self.send_error(wfile, 400, method,
//                                self.default_request_version,
//                                "Bad request version (%r)" % version)
//                return ()
//            try:
//                base_version_number = version.split('/', 1)[1]
//                version_number = base_version_number.split(".")
//                # RFC 2145 section 3.1 says there can be only one "." and
//                # - major and minor numbers MUST be treated as
//                # separate integers;
//                # - HTTP/2.4 is a lower version than HTTP/2.13, which in
//                # turn is lower than HTTP/12.3;
//                # - Leading zeros MUST be ignored by recipients.
//                if len(version_number) != 2:
//                    raise ValueError
//                version_number = int(version_number[0]), int(version_number[1])
//            except (ValueError, IndexError):
//                self.send_error(wfile, 400, method,
//                                self.default_request_version,
//                                "Bad request version (%r)" % version)
//                return ()
//        elif len(words) == 2:
//            [method, path] = words
//            version = self.default_request_version
//            if method != 'GET':
//                self.send_error(wfile, 400, method,
//                                self.default_request_version,
//                                "Bad HTTP/0.9 request type (%r)" % method)
//                return ()
//        elif not words:
//            return ()
//        else:
//            self.send_error(wfile, 400, None,
//                            self.default_request_version,
//                            "Bad request syntax (%r)" % request_line)
//            return ()
//
//        logger.debug('checking HTTP protocol version')
//        if (version != 'HTTP/1.1' and
//                version != 'HTTP/1.0' and
//                version != 'HTTP/0.9'):
//            self.send_error(wfile, 505, method, self.default_request_version,
//                            "Invalid HTTP Version (%s)" % version)
//            return ()
//
//        logger.debug('parsing headers')
//        headers = HeaderCollection.from_stream(rfile)
//        for k, v in headers.iteritems():
//            logger.debug(' {0}: "{1}"'.format(k, v))
//
//        persistent_connection = False
//        if (version == 'HTTP/1.1' and
//                'Connection' in headers and
//                headers['Connection'] != 'close'):
//            persistent_connection = True
//
//        logger.debug('reading body')
//        body = read_body_from_stream(rfile, headers)
//
//        logger.debug('returning')
//        return (Request(method, path, headers, body), persistent_connection)
//
//    def send_error(self, wfile, code, method, request_version, message=None):
//        """Send and log an error reply.
//
//Arguments are the error code, and a detailed message.
//The detailed message defaults to the short entry matching the
//response code.
//
//This sends an error response (so it must be called before any
//output has been generated), logs the error, and finally sends
//a piece of HTML explaining the error to the user.
//
//"""
//
//        try:
//            short, long = messages_by_response_code[code]
//        except KeyError:
//            short, long = '???', '???'
//        if message is None:
//            message = short
//        explain = long
//        error_message_format = ("Error code %(code)d.\nMessage: %(message)s.\n"
//                                "Error code explanation: %(code)s = "
//                                "%(explain)s.")
//        content = (error_message_format %
//                   {'code': code, 'message': message,
//                    'explain': explain})
//
//        headers = {
//            'Content-Type': "text/html",
//            'Connection': 'close',
//        }
//
//        if method == 'HEAD' or code < 200 or code in (204, 304):
//            content = ''
//
//        response = Response(request_version, code, message, headers, content)
//
//        self.send_response(response)
//
//    def send_response(self, wfile, response):
//        """
//Send the given Response over the socket. Add Server and Date headers
//if not already present.
//"""
//
//        message = response.message
//        if message is None:
//            if response.code in messages_by_response_code:
//                message = messages_by_response_code[response.code][0]
//            else:
//                message = ''
//        wfile.write("HTTP/1.1 %s %s\r\n" %
//                    (response.code, message))
//
//        for name, value in response.headers.iteritems():
//            wfile.write("%s: %s\r\n" % (name, value))
//        wfile.write("\r\n")
//
//        if response.body is not None and len(response.body) > 0:
//            logger.debug('Send the response body, len: %s',
//                         len(response.body))
//            wfile.write(response.body)
//
//    def date_time_string(self, timestamp=None):
//        """Return the current date and time formatted for a message header."""
//        if timestamp is None:
//            timestamp = time.time()
//        year, month, day, hh, mm, ss, wd, y, z = time.gmtime(timestamp)
//
//        weekdayname = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
//        monthname = [None,
//                     'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
//                     'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
//
//        s = "%s, %02d %3s %4d %02d:%02d:%02d GMT" % (weekdayname[wd], day,
//                                                     monthname[month], year,
//                                                     hh, mm, ss)
//        return s
//
