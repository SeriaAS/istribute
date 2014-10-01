;(function ($) {
  var fid = 0;
  Drupal.behaviors.istribute = {
    attach: function (context, settings) {
      var $fid = $('[name="istribute_video_upload[fid]"]');
      if ($fid.length && fid != +$fid.val()) {
        fid = +$fid.val();
        if (fid) {
          $('[name=istribute_video]').trigger('istribute-reload');
        }
      }
    }
  }
})(jQuery);