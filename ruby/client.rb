#!/usr/bin/env ruby
# Requires json gem
require 'net/http'
require 'json'

DISPATCH_URL = "http://v1.dispatch.to/"
API_KEY = "0123456789abcdef"

if ARGV.length < 1
  STDERR.puts "usage: #{$0} <email> [email] ..."
end

emails = ARGV[0..ARGV.length]

raw_result = Net::HTTP.post_form(URI.parse(DISPATCH_URL),
                                 {'email' => emails,
                                  'api_key' => API_KEY})

result = JSON.parse(raw_result.body)

result["results"].each do |r|
  puts "%-20s %-10s" % [r["email"], r["result"]]
end
