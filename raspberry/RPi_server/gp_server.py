import socketserver
import RequestHandler

PORT = 8000

#handler = http.server.SimpleHTTPRequestHandler
handler = RequestHandler.GPRequestHandler

httpd = socketserver.TCPServer(("", PORT), handler)

print("serving at port", PORT)
httpd.serve_forever()
