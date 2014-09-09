<?php

class PlgEditorsxtdIstributebtnInstallerScript {
	public function install($parent) {
		// Enable plugin
		$db  = JFactory::getDbo();
		$query = $db->getQuery(true);
		$query->update('#__extensions');
		$query->set($db->quoteName('enabled') . ' = 1');
		$query->where($db->quoteName('element') . ' = ' . $db->quote('istributebtn'));
		$query->where($db->quoteName('type') . ' = ' . $db->quote('plugin'));
		$db->setQuery($query);
		$db->execute();
		file_put_contents('/tmp/istributebtn', 'Hello');
	}
}