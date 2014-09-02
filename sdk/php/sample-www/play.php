<?php

require(__DIR__ . '/istribute.php');

$video = NULL;
if (isset($_GET['id']))
	$video = $istribute->getVideo($_GET['id']);

if (!$video) {
	header('HTTP/1.1 404 Not found');
	echo "<!DOCTYPE html>\n";
	?>
	<title>Video not found</title>
	<h1>The video was not found</h1>
	<p><a href="/index.php">Try going back to the list of videos</a></p>
	<?php
	die();
}

?><!DOCTYPE html>
<title>Video player</title>
<iframe src="<?php echo htmlspecialchars($video->getPlayerUrl()); ?>" width="800" height="600"></iframe>
