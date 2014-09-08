<?php
defined('_JEXEC') or die;

$user  = JFactory::getUser();
$input = JFactory::getApplication()->input;

$e_name = $_GET['e_name'];

?>
<style scoped>
	.istributeVideos {
		list-style-type: none;
	}
	.istributeVideos li {
		float: left;
		border: 1px solid #dddddd;
		width: 95px;
		height: 105px;
		margin: 5px;
	}
	.istributeVideos li span.linkContainer {
		display: inline-block;
	}
	.istributeVideos li h2 {
		font-size: 12px;
		margin: 0px;
		padding: 0px;
		width: 95px;
		height: 20px;
		overflow: hidden;
		text-align: center;
		text-overflow: ellipsis;
	}
	.istributeVideos li img {
		display: block;
		width: 85px;
		height: 70px;
		padding: 5px;
	}
</style>
<h1>Istribute videos</h1>
<?php

$videoList = $this->istribute->getVideoList();

if ($this->message != NULL) {
	?>
	<p class="alert"><?php echo htmlspecialchars($this->message); ?></p>
	<?php
}

?>
<p><a href="<?php echo htmlspecialchars($this->updateAppkeysUrl); ?>">Change your appkeys</a></p>
<form enctype="multipart/form-data" method="post">
	<div>
		<label>Upload a video file: <input type="file" name="videoFile"></label>
	</div>
	<div>
		<label>Title: <input id='videoTitle' type="text" name="videoTitle" value="Untitled video"></label>
	</div>
	<div>
		<input type="submit" value="Upload">
	</div>
</form>
<script>
	(function () {
		var firstClick = true;
		jQuery('#videoTitle').focus(function () {
			if (firstClick) {
				firstClick = false;
				document.getElementById('videoTitle').setAttribute("value", "");
			}
		});
	})();
</script>
<ul class="istributeVideos">
	<?php
	$arr = array();
	foreach ($videoList as $video) {
		$arr[] = $video;
	}
	$videoList = $arr;
	while ($videoList && is_array($videoList)) {
		$video = array_pop($videoList);
		$playerUrl = $video->getPlayerUrl();
		$thumbnailUrl = $video->getPreviewImage();
		$onclick = 'setVideo('.json_encode($playerUrl).', '.json_encode($thumbnailUrl).'); return false;';
		?>
		<li><a onclick="<?php echo htmlspecialchars($onclick); ?>" href=""><span class="linkContainer"><h2><?php echo htmlspecialchars($video->getTitle()); ?></h2><img src="<?php echo htmlspecialchars($thumbnailUrl); ?>" alt="<?php echo htmlspecialchars($video->getTitle()); ?>"></span></a></li>
		<?php
	}
	?>
</ul>
<script>
	(function () {
		setVideo = function (playerUrl, thumbnailUrl) {
			window.parent.jInsertEditorText("<a class=\"istribute-embed-link\" href=\""+playerUrl+"\" target=\"_blank\"><img src=\""+thumbnailUrl+"\" alt=\"Video content\"></a>", <?php echo json_encode($e_name); ?>);
			window.parent.SqueezeBox.close();
		}
	})();
</script>
<?php

?>