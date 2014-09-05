<?php

defined('_JEXEC') or die;

class PlgButtonIstributebtn extends JPlugin
{
	/**
	 * Load the language file on instantiation.
	 *
	 * @var    boolean
	 * @since  3.1
	 */
	protected $autoloadLanguage = true;

	/**
	 * Display the button.
	 *
	 * @param   string   $name    The name of the button to display.
	 * @param   string   $asset   The name of the asset being edited.
	 * @param   integer  $author  The id of the author owning the asset being edited.
	 *
	 * @return  array    A two element array of (imageName, textToInsert) or false if not authorised.
	 */
	public function onDisplay($name, $asset, $author)
	{
		$app = JFactory::getApplication();
		$user = JFactory::getUser();
		$extension = $app->input->get('option');

		if ($asset == '')
		{
			$asset = $extension;
		}

		if (	$user->authorise('core.edit', $asset)
			||	$user->authorise('core.create', $asset)
			||	(count($user->getAuthorisedCategories($asset, 'core.create')) > 0)
			||	($user->authorise('core.edit.own', $asset) && $author == $user->id)
			||	(count($user->getAuthorisedCategories($extension, 'core.edit')) > 0)
			||	(count($user->getAuthorisedCategories($extension, 'core.edit.own')) > 0 && $author == $user->id))
		{
			$link = 'index.php?option=com_istribute&amp;view=videos&amp;tmpl=component&amp;e_name=' . $name . '&amp;asset=' . $asset . '&amp;author=' . $author;
			JHtml::_('behavior.modal');
			$button = new JObject;
			$button->modal = true;
			$button->class = 'btn';
			$button->link = $link;
			$button->text = JText::_('PLG_ISTRIBUTEBTN_BUTTON');
			$button->name = 'istributebtn';
			$button->options = "{handler: 'iframe', size: {x: 800, y: 500}}";

			return $button;
		}
		else
		{
			return false;
		}
	}
}
