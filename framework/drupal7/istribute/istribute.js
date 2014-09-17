;(function ($) {
  var fid = 0;
  Drupal.behaviors.istribute = {
    attach: function (context, settings) {
      var $fid = $('[name="istribute_video_upload[fid]"]');
      if ($fid.length && fid != +$fid.val()) {
        fid = +$fid.val();
        if (fid) {
          $.getJSON(Drupal.settings.istribute.ajaxpath + '/videoselect', function (data) {
            var options = [];
            for (var key in data) {
              options[options.length] = '<option value="' + key + '">' + data[key] + '</option>';
            }
            $('[name=istribute_video]').html(options.join(''));
          });
        }
      }
    }
  }
})(jQuery);