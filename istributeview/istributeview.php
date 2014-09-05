<?php

defined('_JEXEC') or die;

class PlgContentIstributeview extends JPlugin
{
	public function onContentPrepare($context, &$article, &$params, $page = 0) {
		$script = $this->_getScriptOnce();
		if ($script) {
			$article->text .= $script;
		}
		return true;
	}
	protected function _getScript() {
		ob_start();
		?>
		<!-- Istribute filter script for showing video players (istributeview) -->
		<script>
			(function () {
				jQuery(document).ready(function () {
					jQuery(".istribute-embed-link").each(function () {
						var link = this;
						var imgTags = jQuery(link).children('img');
						var img = imgTags.first();
						jQuery(img).load(function () {
							var w = jQuery(img).width();
							var h = jQuery(img).height();
							var href = link.getAttribute('href');
							var iframe = document.createElement('iframe');
							iframe.setAttribute('src', href);
							iframe.setAttribute('width', w);
							iframe.setAttribute('height', h);
							link.parentNode.replaceChild(iframe, link);
						});
					});
				});
			})();
		</script>
		<?php
		return ob_get_clean();
	}
	protected $script = TRUE;
	protected function _getScriptOnce() {
		if ($this->script) {
			$this->script = FALSE;
			return $this->_getScript();
		} else
			return '';
	}
}
