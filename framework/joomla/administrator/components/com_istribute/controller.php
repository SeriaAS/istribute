<?php

defined('_JEXEC') or die;

class IstributeController extends JControllerLegacy {
	public function display($cachable = false, $urlparams = false)
	{
		JPluginHelper::importPlugin('content');
		$app =& JFactory::getApplication();
		$config = JComponentHelper::getParams('com_istribute');

		if (isset($_POST['istributeUrl'])) {
			$istributeUrl = $_POST['istributeUrl'];
			$istributeAppId = $_POST['istributeAppId'];
			$istributeAppKey = $_POST['istributeAppKey'];
			$config->set('istribute.api.url', $istributeUrl);
			$config->set('istribute.api.appid', $istributeAppId);
			$config->set('istribute.api.appkey', $istributeAppKey);
			$db = JFactory::getDBO();
			$query = $db->getQuery(true);

// Build the query
			$query->update('#__extensions AS a');
			$query->set('a.params = ' . $db->quote((string)$config));
			$query->where('a.element = "com_istribute"');

// Execute the query
			$db->setQuery($query);
			$db->execute();
		} else {
			$istributeUrl = $config->get('istribute.api.url', 'https://api.istribute.com');
			$istributeAppId = $config->get('istribute.api.appid', '');
			$istributeAppKey = $config->get('istribute.api.appkey', '');
		}

		$vName = $this->input->get('view', 'videos');
		switch ($vName)
		{
			case 'appkeys':
				if ($istributeUrl && $istributeAppId && $istributeAppKey)
					$app->redirect('index.php?option=com_istribute&view=videos');
				$vLayout = $this->input->get('layout', 'default', 'string');
				$mName = 'manager';
				break;
			case 'videos':
			default:
				if (!$istributeUrl || !$istributeAppId || !$istributeAppKey)
					$app->redirect('index.php?option=com_istribute&view=appkeys');
				$vLayout = $this->input->get('layout', 'default', 'string');
				$mName = 'manager';

		}

		$document = JFactory::getDocument();
		$vType    = $document->getType();

		// Get/Create the view
		$view = $this->getView($vName, $vType);
		$view->addTemplatePath(JPATH_COMPONENT_ADMINISTRATOR.'/views/'.strtolower($vName).'/tmpl');

		// Get/Create the model
		if ($model = $this->getModel($mName))
		{
			// Push the model into the view (as default)
			$view->setModel($model, true);
		}

		// Set the layout
		$view->setLayout($vLayout);

		// Display the view
		$view->display();

		return $this;
	}
}
