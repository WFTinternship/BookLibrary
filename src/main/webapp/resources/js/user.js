'use strict';
/**
 * Created by Sona on 9/6/2016.
 */
window.history.forward();
function noBack() {
    window.history.forward();
}

jQuery(document).ready(function(){
    jQuery('#showBooksButton').on('click', function(/*event*/) {
        jQuery('#showBooksContent').toggle('show');
    });
});