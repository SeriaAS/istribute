<?php
require(__DIR__ . '/istribute.php');
?><!DOCTYPE html>

<title>Istribute sample web application</title>

<h1>Istribute sample web application</h1>
<?php
	$videos = $istribute->getVideoList();
?>
<p>Below is a list of videos you have uploaded:</p>
<table>
	<thead>
		<tr>
			<th>Id
			<th>Title
			<th>Play
			<th>Details
	<tfoot>
	<tbody>
<?php
	foreach ($videos as $video) {
		?>
		<tr>
			<td><?php echo $video->getId(); ?>
			<td><?php echo $video->getTitle(); ?>
			<td><a href="<?php echo htmlspecialchars("play.php?id=".rawurlencode($video->getId())); ?>">Play</a>
			<td><a href="<?php echo htmlspecialchars("video.php?id=".rawurlencode($video->getId())); ?>">Details</a>
		<?php
	}
?>
</table>

<form enctype="multipart/form-data" action="upload.php" method="post">
	<div>
		<h2>Upload a new video file</h2>
		<input type="file" name="videoFile">
		<input type="submit" value="Upload">
	</div>
</form>