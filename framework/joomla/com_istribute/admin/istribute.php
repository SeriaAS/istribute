<?php

defined('_JEXEC') or die;

$input  = JFactory::getApplication()->input;
$user   = JFactory::getUser();
$asset  = $input->get('asset');
$author = $input->get('author');

// Access check.
if (!$user->authorise('core.manage', 'com_media')
	&&	(!$asset or (
			!$user->authorise('core.edit', $asset)
			&&	!$user->authorise('core.create', $asset)
			&& 	count($user->getAuthorisedCategories($asset, 'core.create')) == 0)
		&&	!($user->id == $author && $user->authorise('core.edit.own', $asset))))
{
	return JError::raiseWarning(403, JText::_('JERROR_ALERTNOAUTHOR'));
}

$controller	= JControllerLegacy::getInstance('Istribute', array('base_path' => JPATH_COMPONENT_ADMINISTRATOR));
$controller->execute($input->get('task'));
$controller->redirect();
