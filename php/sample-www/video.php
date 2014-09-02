<?php

require(__DIR__ . '/istribute.php');

$video = NULL;
if (isset($_GET['id']))
	$video = $istribute->getVideo($_GET['id']);

if (isset($_POST['title'])) {
	$video->setTitle($_POST['title']);
	$video->save();
	echo "<!DOCTYPE html>";
	?>
	<title>The video was saved</title>
	<h1>The video was saved</h1>
	<p>It will take some time before the changes are visible.</p>
	<p><a href="">Continue</a></p>
	<?php
	die();
}

if (!$video) {
	header('HTTP/1.1 404 Not found');
	echo "<!DOCTYPE html>";
	?>
	<title>Video not found</title>
	<h1>Video not found</h1>
	<p>The video was not found. <a href="index.php">Please return to the video list</a></p>
	<?php
	die();
}

echo "<!DOCTYPE html>";
?>
<h1>Video data</h1>
<p><a href="index.php">Go back to front</a></p>
<form method="post">
	<table>
		<thead>
		<tfoot>
		<tbody>
			<tr>
				<th>Id
				<td><?php echo htmlspecialchars($video->getId()); ?>
			<tr>
				<th>Title
				<td><input name="title" type='text' value="<?php echo htmlspecialchars($video->getTitle()); ?>"/>
			<tr>
				<th>State
				<td><?php echo htmlspecialchars($video->getState()); ?>
			<tr>
				<th>PreviewImage
				<td><?php echo htmlspecialchars($video->getPreviewImage()); ?>
			<tr>
				<th>Aspect
				<td><?php echo htmlspecialchars($video->getAspect()); ?>
			<tr>
				<th>Download urls
				<td><?php echo htmlspecialchars($video->getDownloadUrls()); ?>
			<tr>
				<th>Thumbnails status
				<td><?php echo htmlspecialchars($video->getThumbnailStatus()); ?>
			<tr>
				<th>Interval thumbnail vtt
				<td><?php echo htmlspecialchars($video->getIntervalThumbnailVtt()); ?>
			<tr>
				<th>Timestamp
				<td><?php echo htmlspecialchars($video->getTimestamp()); ?>
	</table>


	<h2>Video sources</h2>
	<table>
		<thead>
			<tr>
				<th>Name
				<th>Label
				<th>Media
				<th>Method
				<th>Type
				<th>Width
				<th>Height
				<th>Bitrate
				<th>Video bitrate
				<th>Audio bitrate
				<th>Source
				<th>Codecs
		<tfoot>
		<tbody>
			<?php
			$sources = $video->getSources();
				foreach ($sources as $source) {
					?>
					<tr>
						<td><?php echo htmlspecialchars($source->getName()); ?>
						<td><?php echo htmlspecialchars($source->getLabel()); ?>
						<td><?php echo htmlspecialchars($source->getMedia()); ?>
						<td><?php echo htmlspecialchars($source->getMethod()); ?>
						<td><?php echo htmlspecialchars($source->getType()); ?>
						<td><?php echo htmlspecialchars($source->getWidth()); ?>
						<td><?php echo htmlspecialchars($source->getHeight()); ?>
						<td><?php echo htmlspecialchars($source->getBitrate()); ?>
						<td><?php echo htmlspecialchars($source->getVideoBitrate()); ?>
						<td><?php echo htmlspecialchars($source->getAudioBitrate()); ?>
						<td><a href="<?php echo htmlspecialchars($source->getSrc()); ?>">Link</a>
						<td><?php echo htmlspecialchars(implode(', ', $source->getCodecs())); ?>
					<?php
				}
				?>
	</table>
	<div>
		<input type="submit" value="<?php echo htmlspecialchars('Save data'); ?>"/>
	</div>
</form>