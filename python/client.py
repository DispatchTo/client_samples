#!/usr/bin/env python
import json
import urllib
import urllib2
import sys

dispatch_url = "http://v1.dispatch.to/"
api_key = "0123456789abcdef"

def main(argv = None):
	if argv is None:
		argv = sys.argv
	
	if len(argv) < 2:
		print >>sys.stderr, "usage: " + argv[0] + " <email> [email] ..."
		return 1
		
	emails = argv[1:]
	
	data = 'api_key=' + urllib.quote_plus(api_key)
	data += '&email=' + '&email='.join([urllib.quote_plus(email) for email in emails])
	
	handle = urllib2.urlopen(dispatch_url, data)
	
	results = json.load(handle)
	
	for result in results["results"]:
		print result['email'] + ": " + result['result']
	
if __name__ == "__main__":
	sys.exit(main())