<?php

class IstributeViewVideos extends JViewLegacy {
	public function display($tpl = null) {
		$app	= JFactory::getApplication();
		$config = JComponentHelper::getParams('com_istribute');

		if (!$app->isAdmin())
		{
			return $app->enqueueMessage(JText::_('JERROR_ALERTNOAUTHOR'), 'warning');
		}

		$lang	= JFactory::getLanguage();

		$style = $app->getUserStateFromRequest('media.list.layout', 'layout', 'thumbs', 'word');

		$document = JFactory::getDocument();

		$session	= JFactory::getSession();
		$state		= $this->get('state');
		$this->session = $session;
		$this->config = &$config;
		$this->state = &$state;

		$istributeUrl = $this->config->get('istribute.api.url', 'https://api.istribute.com');
		$istributeAppId = $this->config->get('istribute.api.appid', '');
		$istributeAppKey = $this->config->get('istribute.api.appkey', '');

		parent::display($tpl);
		echo JHtml::_('behavior.keepalive');
	}
}