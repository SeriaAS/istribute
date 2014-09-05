<h1>Appkeys</h1>
<form method="post">
	<div>
		<label>Istribute url: <input type="text" name="istributeUrl" value="<?php echo htmlspecialchars($this->istributeUrl); ?>" disabled="disabled"></label>
		<label>Istribute application id: <input type="text" name="istributeAppId" value="<?php echo htmlspecialchars($this->istributeAppId); ?>"</label>
		<label>Istribute application key: <input type="text" name="istributeAppKey" value="<?php echo htmlspecialchars($this->istributeAppKey); ?>"</label>
	</div>
	<div>
		<input type="submit" value="Save parameters">
	</div>
</form>