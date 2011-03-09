#!/usr/bin/env php
<?php
$DISPATCH_URL = "http://v1.dispatch.to/";
$API_KEY = "0123456789abcdef";

if (sizeof($argv) < 2) {
  print "usage: " . $argv[0] . " <email> [email] ...\n";
  exit(1);
}

$emails = array_map("urlencode", array_slice($argv, 1));

$post_data = "api_key=" . urlencode($API_KEY) . "&email=" . join("&email=", $emails);

$ch = curl_init($DISPATCH_URL);
curl_setopt($ch, CURLOPT_POST, TRUE);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);

$results = json_decode(curl_exec($ch), TRUE);

foreach($results["results"] as $result) {
  print $result["email"] . ": " . $result["result"] . "\n";
}

?>
