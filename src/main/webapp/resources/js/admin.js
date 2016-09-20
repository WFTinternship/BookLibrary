'use static';
/**
 * Created by Workfront on 9/13/2016.
 */

jQuery(document).ready(function(){
    jQuery('#addAuthor').on('click', function(/*event*/) {
        jQuery('#addAuthorContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addGenre').on('click', function(/*event*/) {
        jQuery('#addGenreContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addBook').on('click', function(/*event*/) {
        jQuery('#addBookContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addAuthorToBook').on('click', function(/*event*/) {
        jQuery('#addAuthorToBookContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#viewAuthorsOfBook').on('click', function(/*event*/) {
        jQuery('#viewBookAuthorsContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addMediaType').on('click', function(/*event*/) {
        jQuery('#addMediaTypeContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#addMediaToBook').on('click', function(/*event*/) {
        jQuery('#addMediaToBookContent').toggle('show');
    });
});

jQuery(document).ready(function(){
    jQuery('#showBooks').on('click', function(event) {
        jQuery('#showBooksContent').toggle('show');
    });
});