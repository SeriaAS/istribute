<?php

defined('_JEXEC') or die;

class IstributeController extends JControllerLegacy {
	public function display($cachable = false, $urlparams = false)
	{
		JPluginHelper::importPlugin('content');
		$vName = $this->input->get('view', 'videos');
		switch ($vName)
		{
			case 'videos':
			default:
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
