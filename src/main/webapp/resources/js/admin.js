'use static';
/**
 * Created by Workfront on 9/13/2016.
 */

jQuery(document).ready(function(){
    jQuery('#addAuthor').on('click', function(event) {
        jQuery('#addAuthorContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addBook').on('click', function(event) {
        jQuery('#addBookContent').toggle('show');
    });
});