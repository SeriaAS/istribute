<?php

require(__DIR__ . '/istribute.php');

if (isset($_FILES['videoFile'])) {
	?><!DOCTYPE html>
	<?php
	if ($_FILES['videoFile']['error'] == 0) {
		$filename = $_FILES['videoFile']['tmp_name'];
		$video = $istribute->uploadVideo($filename);
		if ($video !== NULL) {
			?>
			<title>The video was uploaded to istribute!</title>
			<h1>The video was uploaded to istribute!</h1>
			<p>The video was uploaded to istribute with id <?php echo $video->getId(); ?>! <a href="index.php">Continue...</a></p>
			<?php
		} else {
			?>
			<title>Upload to istribute failed</title>
			<h1>Upload to istribute failed</h1>
			<p>Upload to istribute failed. <a href="index.php">Please try again</a></p>
			<?php
		}
		unlink($filename);
	} else {
		?>
		<title>Upload failed</title>
		<h1>Upload failed</h1>
		<p>Sorry, the video upload failed. <a href="index.php">Please try again</a></p>
		<?php
	}
}