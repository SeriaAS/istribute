<?php

$configFilename = __DIR__ . '/config.php';
if (!file_exists($configFilename)) {
	die('Please create a config.php file (from the config.php.sample) to enable the sample webapp. App-id and app-key can be retrieved from your account on istribute.com');
}
$config = require($configFilename);

require_once(dirname(__DIR__).'/sdk/src/istribute.php');

$istribute = new \Seria\istributeSdk\Istribute($config['istribute']['appId'], $config['istribute']['appKey'], $config['istribute']['serverUrl']);

header('Cache-Control: no-cache');
